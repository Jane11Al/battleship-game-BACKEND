package com.example.battleshipbackendspring.controller;

import com.example.battleshipbackendspring.model.Game;
import com.example.battleshipbackendspring.service.GameService;
import com.example.battleshipbackendspring.service.PlayerMatchesService;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WebController {

    private final Gson gson;
    private final GameService gameService;
    private final PlayerMatchesService playerMatchesService;
    private final SimpMessageSendingOperations messagingTemplate;


    @Autowired
    public WebController(Gson gson, GameService gameService, PlayerMatchesService playerMatchesService, SimpMessageSendingOperations messagingTemplate) {
        this.gson = gson;
        this.gameService = gameService;
        this.playerMatchesService = playerMatchesService;
        this.messagingTemplate = messagingTemplate;
    }


    /**
     * id is socket id which is unique for game between 2 players
     * this method is "subscribed" to reply on messages which come to /message/socketId endpoint
     * and to reply to opponent (because of unique id)
     * Payload is body of message!
     * @param message
     * @param socketId
     * @return
     * @throws Exception
     */
    @SneakyThrows(NullPointerException.class)
    @MessageMapping("/message/{id}")
    @SendTo("/topic/reply/{id}")
    public String processMessageFromClient(@Payload String message, @DestinationVariable("id") String socketId) throws Exception{
        String messageBody = gson.fromJson(message, Map.class).get("name").toString();
        String textPart="",numberPart="";
        String playerId="";
        boolean isPlayerOne=false;

        if(!(messageBody.contains("start"))){
            textPart = messageBody.replaceAll("\\d","");
            numberPart = messageBody.replace(textPart, "");

            HashMap<String, String> tempHashMap = new HashMap<>();
            if (textPart.equals("potheir")) {   //it means is player 1 turn
                playerId = playerMatchesService.findBySockId(socketId).getPlayer2();
                tempHashMap.put("turnBy", "p1");
                isPlayerOne = true;
            }else if(textPart.equals("pttheir")){
                playerId = playerMatchesService.findBySockId(socketId).getPlayer1();
                tempHashMap.put("turnBy", "p2");
                isPlayerOne = false;
            }

            Game game=gameService.getGameByUserId(playerId);
            boolean isContainsShip = game.enemyTurn(Integer.parseInt(numberPart));
            boolean winningMove = game.getAttackedShips() >= 15;

            tempHashMap.put("attackedAt", numberPart);
            tempHashMap.put("isContainsShip", String.valueOf(isContainsShip));
            tempHashMap.put("winningMove", String.valueOf(winningMove));

            gameService.saveGame(game);
            String wonPlayerId, lostPlayerId;
            if(game.getAttackedShips()>=15){
                if(isPlayerOne){
                    wonPlayerId = playerMatchesService.findBySockId(socketId).getPlayer1();
                    lostPlayerId = playerMatchesService.findBySockId(socketId).getPlayer2();
                }else{
                    wonPlayerId = playerMatchesService.findBySockId(socketId).getPlayer2();
                    lostPlayerId = playerMatchesService.findBySockId(socketId).getPlayer1();
                }


                Game wonGame = gameService.getGameByUserId(wonPlayerId);
                Game lostGame = gameService.getGameByUserId(lostPlayerId);

                wonGame.setWonGames(wonGame.getWonGames()+1);
                lostGame.setLostGames(lostGame.getLostGames()+1);

                gameService.saveGame(wonGame);
                gameService.saveGame(lostGame);
            }

            return gson.toJson(tempHashMap);
        }

        return new Gson().fromJson(message,Map.class).get("name").toString();
    }

    @MessageExceptionHandler
    public String handleException(Throwable exception) {
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
        return exception.getMessage();
    }
}

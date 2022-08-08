package com.example.battleshipbackendspring.controller;


import com.example.battleshipbackendspring.model.Board;
import com.example.battleshipbackendspring.model.Game;
import com.example.battleshipbackendspring.repository.GameRepository;
import com.example.battleshipbackendspring.service.GameService;
import com.example.battleshipbackendspring.service.PlayerMatchesService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/battle")
public class BattleController {

    /**
     * Used Gson to parse JSON objects
     */

    private final Gson gson;
    private final GameService gameService;
    private final PlayerMatchesService playerMatchesService;


    @Autowired
    public BattleController(Gson gson, GameService gameService, PlayerMatchesService playerMatchesService) {
        this.gson = gson;
        this.gameService = gameService;
        this.playerMatchesService = playerMatchesService;
    }

    /**
     * This endpoint is used to show scoreboard
     * Score of users who won at least one game
     */

    @GetMapping(value = "/getAll")
    public String getScoreboard(){
        HashMap<String,String[]> statistic = gameService.getFullStatistic();
        return gson.toJson(statistic);
    }

//    @PostMapping(value="/test")
//    public void add(){
//        Game game=new Game();
//        game.setLostGames(1);
//        game.setWonGames(2);
//        game.setBoard(new Board());
//        game.setShips(null);
//        game.setAttackedShips(2);
//        game.setUserId(UUID.randomUUID().toString());
//        game.setUserName("Djole2");
//        Game g =gameRepository.save(game);
//        System.out.println(g);
//    }
}

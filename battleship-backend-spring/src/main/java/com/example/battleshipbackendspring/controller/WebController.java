package com.example.battleshipbackendspring.controller;

import com.example.battleshipbackendspring.service.GameService;
import com.example.battleshipbackendspring.service.PlayerMatchesService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

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


}

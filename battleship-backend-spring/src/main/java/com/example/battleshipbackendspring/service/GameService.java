package com.example.battleshipbackendspring.service;


import com.example.battleshipbackendspring.model.Game;

import java.util.HashMap;
import java.util.List;

public interface GameService {

    HashMap<String, String[]> getFullStatistic();


    Game getGameByUserName(String username);

    void saveGame(Game game);

    Game getGameByUserId(String userId);
}

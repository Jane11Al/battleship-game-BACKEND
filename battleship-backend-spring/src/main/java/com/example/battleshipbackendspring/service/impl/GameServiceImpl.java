package com.example.battleshipbackendspring.service.impl;

import com.example.battleshipbackendspring.model.Game;
import com.example.battleshipbackendspring.repository.GameRepository;
import com.example.battleshipbackendspring.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    @Override
    public HashMap<String, String[]> getFullStatistic() {
        List<Game> allGames = gameRepository.findAll();
        HashMap<String,String[]> statistic = new HashMap<>();

        for(Game game:allGames){
            if(game.getWonGames()>0 || game.getLostGames()>0){
                statistic.put(game.getUserName(),new String[]{Integer.toString(game.getWonGames()),Integer.toString(game.getLostGames())});
            }
        }

        return statistic;
    }

    @Override
    public Game getGameByUserName(String username) {
        Game game = null;

        if(gameRepository.existsByUserName(username)){
            game = gameRepository.findFirstByUserName(username);
            game = new Game(null, game.getUserName(),game.getWonGames(),game.getLostGames());
        }else{
            game = new Game(null,username);
        }

        return game;
    }

    @Override
    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public Game getGameByUserId(String userId) {
        return gameRepository.findByUserId(userId);
    }


}

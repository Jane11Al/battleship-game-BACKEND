package com.example.battleshipbackendspring.service;


import com.example.battleshipbackendspring.model.PlayerMatches;

public interface PlayerMatchesService {
    void savePlayerMatches(PlayerMatches playerMatches);

    PlayerMatches findOneByPlayer1(String userId);

    PlayerMatches findBySockId(String sockId);
}

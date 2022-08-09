package com.example.battleshipbackendspring.service.impl;

import com.example.battleshipbackendspring.model.PlayerMatches;
import com.example.battleshipbackendspring.repository.PlayerMatchesRepository;
import com.example.battleshipbackendspring.service.PlayerMatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerMatchesImpl implements PlayerMatchesService {

    private final PlayerMatchesRepository playerMatchesRepository;

    @Autowired
    public PlayerMatchesImpl(PlayerMatchesRepository playerMatchesRepository) {
        this.playerMatchesRepository = playerMatchesRepository;
    }

    @Override
    public void savePlayerMatches(PlayerMatches playerMatches) {
        playerMatchesRepository.save(playerMatches);
    }

    @Override
    public PlayerMatches findOneByPlayer1(String userId) {
        return playerMatchesRepository.findByPlayer1(userId);
    }

    @Override
    public PlayerMatches findBySockId(String sockId) {
        return playerMatchesRepository.findByWebSocketAddress(sockId);
    }
}

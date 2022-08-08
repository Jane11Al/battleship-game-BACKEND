package com.example.battleshipbackendspring.repository;

import com.example.battleshipbackendspring.model.PlayerMatches;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerMatchesRepository extends MongoRepository<PlayerMatches,String> {

    PlayerMatches findByPlayer1(String player1);

    PlayerMatches findByWebSocketAddress(String webSocketAddress);

}

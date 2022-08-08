package com.example.battleshipbackendspring.repository;

import com.example.battleshipbackendspring.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game,String> {

     Game findByUserId(String userId);

    /**
     * used for score board
     * @return
     */
     List<Game> findAll();

     boolean existsByUserName(String userName);

     Game findByUserName(String userName);

}

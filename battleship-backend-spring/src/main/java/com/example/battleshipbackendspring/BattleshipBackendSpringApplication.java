package com.example.battleshipbackendspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class BattleshipBackendSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BattleshipBackendSpringApplication.class, args);
    }

}

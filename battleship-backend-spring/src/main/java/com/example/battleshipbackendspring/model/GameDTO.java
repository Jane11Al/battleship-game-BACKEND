package com.example.battleshipbackendspring.model;


import lombok.Data;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * simple class which is Game class in simpler format, to be sent to clients
 * and is used to draw board on client side
 */

@Data
public class GameDTO {

    String userId;

    @Id
    String userName;


    /**
     * The unique id used to communicate with the server via web-sockets.
     * This id is common for both player one and
     * player two in a game. It is saved as a primary key in Player Matches model.
     */
    String socketUrl;


    List<Integer> attackedByEnemyCoordinates;

    HashMap<Integer,Boolean> attackedCoordinates;


    HashMap<ShipName,int[]> shipPositions;

    boolean won;


    /**
     * Simple constructor populating this class. The conversion happens in the logic package.
     * @param userId User id of the user.
     * @param userName User name of the user.
     */

    public GameDTO(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        attackedByEnemyCoordinates = new ArrayList<>();
        attackedCoordinates = new HashMap<>();
        shipPositions = new HashMap<>();
        this.won = false;
    }

}

package com.example.battleshipbackendspring.model;

import lombok.Data;

@Data
public class BoardCell {

    /**
     * Cell is attacked by enemy(left side)
     */
    private boolean attackedByOpponent;

    /**
     * Player attacked enemy's cell(right side)
     */
    private boolean attackedToOpponent;

    /**
     * If this cell contains a ship.
     */
    private boolean containsOpponentShip;

    /**
     * If this cell contains a ship in enemy's board. This value is updated only after player attacks a cell and
     * realizes that that cell contains an enemy ship.
     */
    private boolean containsShip;

    /**
     * Name of ship which is located in cell
     */
    private ShipName shipName;

    private boolean greyArea;

    /**
     * Constructor initializing everything with <i>false.</i>. This values update in game time.
     */
    public BoardCell() {
        this.attackedByOpponent = false;
        this.containsShip = false;
        this.attackedToOpponent = false;
        this.containsOpponentShip = false;
    }

}

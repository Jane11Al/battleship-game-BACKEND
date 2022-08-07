package com.example.battleshipbackendspring.model;

import lombok.Data;

/**
 * If attack count is equals to ship size, it means the ship is destroyed.
 * Also, it has ship coordinates where the ship is placed on board
 *
 */

@Data
public class Ship {

    private int size;
    private int[] coordinates;
    private int attackCount;
    private ShipName shipName;

    /**
     *
     * @param size - how many cells those ship use
     * @param shipName - type of ship
     * at the initialization coordinates are set to 0, they will be changed in runtime
     */
    public Ship(int size,ShipName shipName){
        this.size = size;
        this.shipName=shipName;
        coordinates=new int[size];
        for(int i=0;i<size;i++){
            coordinates[i]=0;
        }
        this.attackCount=0;
    }

}

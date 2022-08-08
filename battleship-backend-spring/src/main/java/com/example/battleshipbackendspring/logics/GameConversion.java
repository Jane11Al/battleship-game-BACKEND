package com.example.battleshipbackendspring.logics;

import com.example.battleshipbackendspring.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class converts Game to GameDTO
 */
public class GameConversion {

    public static GameDTO convertGameInstance(GameDTO gameDTO, Game game){
        int positiveAttacks = 0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                BoardCell boardCell = game.getBoard().getBoardCells()[i][j];
                if(boardCell.isAttackedByOpponent()) gameDTO.getAttackedByEnemyCoordinates().add(i*10+j);
                if(boardCell.isAttackedToOpponent()) gameDTO.getAttackedCoordinates().put(i*10+j,boardCell.isContainsOpponentShip());
            }

            ArrayList<Ship> ships = (ArrayList<Ship>) game.getShips();
            for (Ship ship:ships){
                ShipName shipName=ship.getShipName();
                int[] shipPosition=ship.getCoordinates();
                gameDTO.getShipPositions().put(shipName,shipPosition);
            }

            /**
             * Checking if someone won
             */

            Collection<Boolean> shipAttackedCells = gameDTO.getAttackedCoordinates().values();
            Iterator<Boolean> shipAttackedCellsIterator = shipAttackedCells.iterator();
            while(shipAttackedCellsIterator.hasNext()){
                if(shipAttackedCellsIterator.next()) positiveAttacks++;
            }
            if(positiveAttacks ==  15) gameDTO.setWon(true);
            else gameDTO.setWon(false);


        }
        return gameDTO;
    }
}

package com.example.battleshipbackendspring.model;

import lombok.Data;

@Data
public class Board {

    private BoardCell[][] boardCells;

    /**
     * initialization of battle board with 64 cells
     */

    public Board(){
        boardCells = new BoardCell[8][8];
        for (int i = 0;i<boardCells.length;i++){
            for(int j = 0;j<boardCells[i].length;j++){
                boardCells[i][j]=new BoardCell();
            }
        }
    }
}

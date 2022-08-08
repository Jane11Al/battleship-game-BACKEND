package com.example.battleshipbackendspring.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * This is a class which represents instance of game. It initializes board with random ship positions.
 * This instance is being updated every time a player or opponent make a move. It also tells a winner
 */

@Data
@NoArgsConstructor
public class Game {

    /**
     * This is not primary key. This is randomly generated for every game
     */
    private UUID userId;

    @Id
    private String userName;

    private Board board;
    private List<Ship> ships;

    /**
     * Contains value from 0 to 15. One for each cell. As all the five ships amount to 15 cells.
     */
    private int attackedShips;

    private int wonGames;
    private int lostGames;

    /**
     * Initialize board with random ships. It is called only when new user play game,
     * because constructor doesn't have params won and lost games
     * @param userId - it is given in controller
     * @param userName - this is caught from frontend
     */
    public Game(UUID userId,String userName){
        this.userId=userId;
        this.userName = userName;
        this.attackedShips=0;
        this.wonGames=0;
        this.lostGames=0;
        this.board = new Board();

        ships=new ArrayList<>(){
            {
                add(new Ship(5,ShipName.CARRIER));
                add(new Ship(4,ShipName.CRUISER));
                add(new Ship(3,ShipName.SUBMARINE));
                add(new Ship(2,ShipName.DESTROYER));
                add(new Ship(1,ShipName.ATTACKER));
            }
        };

        for(Ship ship:ships){
            setRandomPositions(board,ship);
        }
    }

    /**
     * This is constructor for already existing players
     * @param userId
     * @param userName
     * @param wonGames - it is accessed by controller
     * @param lostGames - it is accesses by controller
     */
    public Game(UUID userId,String userName,int wonGames,int lostGames){
        this.userId=userId;
        this.userName=userName;
        this.attackedShips=0;
        this.wonGames=wonGames;
        this.lostGames=lostGames;

        this.board = new Board();
        ships = new ArrayList<Ship>() {{
            add(new Ship(5, ShipName.CARRIER));
            add(new Ship(4, ShipName.CRUISER));
            add(new Ship(3, ShipName.SUBMARINE));
            add(new Ship(2, ShipName.DESTROYER));
            add(new Ship(1, ShipName.ATTACKER));
        }};
        for (Ship ship : ships) {
            setRandomPositions(board, ship);
        }
    }



    /**
     *
     * @param board
     * @param ship - the ship that needs to be allocated to random position
     */
    @SneakyThrows(NullPointerException.class)
    private void setRandomPositions(Board board, Ship ship) {
        boolean isHorizontal;
        BoardCell oneCell=null;
        Random random=new Random();
        int x=0;
        int y=0;

        do{
            isHorizontal=returnRandomBoolean();
            if(isHorizontal){
                x = random.nextInt(8 - ship.getSize() + 1);
                y = random.nextInt(8);
            }else{
                x = random.nextInt(8);
                y = random.nextInt(8 - ship.getSize() + 1);
            }
        }while(!areCellsEmpty(x,y,isHorizontal,board,ship.getSize()));

        if(isHorizontal){
            for(int i=0;i<ship.getSize();i++){
                ship.getCoordinates()[i] = (x+i) * 10 + y;
                oneCell = board.getBoardCells()[x+i][y];
                oneCell.setContainsShip(true);

                for (int j = 0; j<3; j++) {
                    for (int k = 0; k<3; k++) {
                        try {
                            BoardCell theCell = board.getBoardCells()[x+i-1+j][y-1+k];
                            theCell.setGreyArea(true);
                        } catch (Exception e) {
                            //
                        }
                    }
                }
            }
        }else{
            for(int i=0;i<ship.getSize();i++){
                ship.getCoordinates()[i]=x*10+(y+i);
                oneCell=board.getBoardCells()[x][y+i];
                oneCell.setContainsShip(true);

                for(int j=0;j<3;j++){
                    for(int k=0;k<3;k++){
                        try{
                            BoardCell theCell=board.getBoardCells()[x-1+j][y+i-1+k];
                            theCell.setGreyArea(true);
                        }catch (Exception e){
                            //
                        }
                    }
                }
            }
        }

        oneCell.setShipName(ship.getShipName());

    }



    /**
     * Used by the <b>setRandomPositions()</b> method. It checks if a sequence of cells in a
     * battle board are empty,
     * so that a shp with a certain size can fit there.
     * @param x - xCoordinate of topmost(if vertical)/leftmost(if horizontal) cell of ship
     * @param y - yCoordiante of topmost(if vertical)/leftmost(if horizontal) cell of ship
     * @param isHorizontal
     * @param board
     * @param size
     * @return - boolean value whether all the cells are empty or not, so that the ship can be placed there.
     */
    private boolean areCellsEmpty(int x, int y, boolean isHorizontal, Board board, int size) {
        boolean indicator = true;
        BoardCell oneCell = null;

        if(isHorizontal){
            for(int i=0;i<size;i++){
                oneCell = board.getBoardCells()[x+i][y];
                if(oneCell.isContainsShip() || oneCell.isGreyArea()){
                    indicator=false;
                    break;
                }
            }
        }else{
            for(int i=0;i<size;i++){
                oneCell = board.getBoardCells()[x][y+i];
                if(oneCell.isContainsShip() || oneCell.isGreyArea()){
                    indicator=false;
                    break;
                }
            }
        }

        return indicator;
    }

    private boolean returnRandomBoolean() {
        return  (Math.random() < 0.5);
    }

    /**
     * The variable attackedByOpponent is set to be true.
     * If the cell contained ship, the variable attackedShips is increased by one.
     * If the cell contained a ship, a true value is returned. Else false is returned
     * @param coordinate -The coordinate of that cell.
     *                  It is in int format which is later converted into x and y, as can
     *      *
     * @return boolean value whether the cell contained a ship or not.
     */

    public boolean enemyTurn(int coordinate){
        int x=coordinate/10;
        int y=coordinate%10;

        BoardCell boardCell = this.getBoard().getBoardCells()[x][y];
        boardCell.setAttackedByOpponent(true);
        if(boardCell.isContainsShip()){
            this.attackedShips++;

            ShipName shipName=boardCell.getShipName();
            for(Ship ship:ships){
                if(ship.getShipName() == shipName){
                    ship.setAttackCount(ship.getAttackCount()+1);
                }
            }

            return true;
        }

        return  false;
    }

}

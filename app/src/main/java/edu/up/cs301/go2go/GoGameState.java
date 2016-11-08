package edu.up.cs301.go2go;

import edu.up.cs301.game.Game;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by qi on 10/29/16.
 */

public class GoGameState extends GameState {
    /* STATIC CONSTANTS */
    //for stage of game
    public static final int MAKE_MOVE_STAGE = 0;
    public static final int SELECT_TERRITORY_STAGE = 1;
    public static final int AGREE_TERRITORY_STAGE = 2;

    //for pieces on the board
    public static final int WHITE = -1;
    public static final int BLACK = 1;
    public static final int EMPTY = 0;
    public static final int WHITE_CAPTURED = 2;
    public static final int BLACK_CPATURED = -2;

    //size of the board
    public static final int boardSize = 9;

    /* GAME STATE INFORMATION */
    private int stage;//stage of the game
    private int turnsPassed;//number of consecutive passes made
    private int whiteCaptures;//number of white pieces captured
    private int blackCaptures;//number of black pieces captured
    private int turn;//which player's turn it is
    private int board [][];//representation of the board
    private int territoryProposal[][];//representation of the most recent proposal

    /* METHODS FOR INTERACTING WITH GAME STATE */

    /**
     * given what player to move and where that player wants to put a piece,
     * return whether or not such move will be legal.
     * @param player index of player to move
     * @param moveX the move's x coordinate
     * @param moveY the move's y coordinate
     * @return true if move is legal, false otherwise
     */
    //None of this method is tested yet
    public boolean isLeagalMove(int player, int moveX, int moveY){
        //check if a piece is there
        if(board[moveX][moveY]!=0){return false;}
        
        //check if you're committing suicide
        //The way this is set up, it must be the last check in this method
        try{if(board[moveX+1][moveY]==0){return true;}}catch(Exception e){}
        try{if(board[moveX][moveY+1]==0){return true;}}catch(Exception e){}
        try{if(board[moveX-1][moveY]==0){return true;}}catch(Exception e){}
        try{if(board[moveX][moveY-1]==0){return true;}}catch(Exception e){}
        //checking more complicated suicide
        //basically does updateboard on a sample board and checks is the piece gets killed
        int newboard[][] = int[board.length][board[].length];
        for(int i=0;i<board.length;i++){
         for(int j=0;j<board[].length;j++){
          newboard[i][j] = board[i][j];   
         }
        }
        newboard[moveX][moveY]=player;
        boolean changing = true;
        
        for(int i=0;i<board.length;i++){
         outterloop:
         for(int j=0;j<board[].length;j++){
          try{if(newboard[i+1][j]==0){changing=false; break outterloop;}}catch(Exception e){}
          try{if(newboard[i][j+1]==0){changing=false; break outterloop;}}catch(Exception e){}
          try{if(newboard[i-1][j]==0){changing=false; break outterloop;}}catch(Exception e){}
          try{if(newboard[i][j-1]==0){changing=false; break outterloop;}}catch(Exception e){} 
         }
         if(changing){newboard[i][j]=4;}
         changing=true;
        }
        changing=true;
        
        while(changing){
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[].length;j++){
                    changing=false;
                     if(newboard[i][j]==4){
                           try{if(newboard[i+1][j]==player){changing=true; newboard[i][j]=player;}}catch(Exception e){}
                           try{if(newboard[i][j+1]==player){changing=true; newboard[i][j]=player;}}catch(Exception e){}
                           try{if(newboard[i-1][j]==player){changing=true; newboard[i][j]=player;}}catch(Exception e){}
                           try{if(newboard[i][j-1]==player){changing=true; newboard[i][j]=player;}}catch(Exception e){} 
                     }
                }
            }
        }
        
        if(newboard[moveX][moveY]!=player){
         return false;   
        }
        return true;
    }

    /**
     * alternate whose turn it is to move.
     */
    public void changeTurn(){

    }

    /**
     * update the state of the board given a player's move.
     * The piece is added on to the board, any captured pieces are removed from the board,
     * and the players' pieces captured count would be updated.
     * @param player index of player to move
     * @param moveX the move's x coordinate
     * @param moveY the move's y coordinate
     * @return true if updated successfully
     */
    public boolean updateBoard(int player, int moveX, int moveY){
        return false;
    }

    @Override
    public void setGame(Game g) {
        super.setGame(g);
    }

    /* CONSTRUCTORS */
    /**
     * constructor
     */
    public GoGameState(){
        super();
        stage = MAKE_MOVE_STAGE;
        turnsPassed = 0;
        whiteCaptures = 0;
        blackCaptures = 0;
        turn = 0;
        board = new int[boardSize][boardSize];
        territoryProposal = null;
    }

    /**
     * copy constructor
     */
    public GoGameState( GoGameState original){
        stage = original.stage;
        turnsPassed = original.turnsPassed;
        whiteCaptures = original.whiteCaptures;
        blackCaptures = original.blackCaptures;
        turn = original.turn;
        territoryProposal = original.territoryProposal;
    }

    /* GETTERS */

    public int getStage() {
        return stage;
    }

    public int getTurnsPassed() {
        return turnsPassed;
    }

    public int getWhiteCaptures() {
        return whiteCaptures;
    }

    public int getBlackCaptures() {
        return blackCaptures;
    }

    public int getTurn() {
        return turn;
    }

    public int[][] getBoard() {
        return boardDeepCopy(board);
    }

    public int[][] getTerritoryProposal() {
        return boardDeepCopy(territoryProposal);
    }

    /* SETTERS */

    public void setTerritoryProposal(int proposal[][]){
        this.territoryProposal = boardDeepCopy(proposal);
    }

    public void setStage(int stage){
        this.stage = stage;
    }

    /* helper methods */

    /**
     * @param original
     * @return a deep copy of a game board (or any 2d int array)
     */
    public static int[][] boardDeepCopy(int original[][]){
        if(original==null){
            return null;
        }

        int temp[][] = new int[original.length][];
        for(int i=0; i<original.length; i++){
            if(original[i]!=null){
                temp[i] = new int[original[i].length];
                for(int j=0; j<original[i].length; j++){
                    temp[i][j] = original[i][j];
                }
            }
        }
        return temp;
    }

    public void incrementPasses(){
        turnsPassed++;
    }
}

package edu.up.cs301.go2go;

import android.util.Log;
import android.view.SurfaceView;

import edu.up.cs301.game.Game;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by qi on 10/29/16.
 */

public class GoGameState extends GameState
{
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
        //add check for Kos
        //The idea is to create a hashtable with numbers for the move number, and a boolean area to save each board state history
        int piece[] = {BLACK, WHITE};
        //check if a piece is there
        try{if(board[moveX][moveY]!=EMPTY){return false;}}catch (Exception e){return false;}
        //if the move was not on the board, return false
        if(moveX<0 || moveX>=board.length || moveY<0 || moveY>=board[0].length){return false;}
        //check if you're committing suicide
        if( isPieceNextTo(EMPTY, board, moveX, moveY) ){
            return true;
        }
        //checking more complicated suicide
        //basically does updateboard on a sample board and checks if the piece gets killed
        int[][] newboard = new int[board.length][board[0].length];
        for(int i=0;i<board.length;i++){
         for(int j=0;j<board[0].length;j++){
          newboard[i][j] = board[i][j];   
         }
        }
        newboard[moveX][moveY]=piece[player];
        boolean changing = true;
        
        //decides if tiles are safe
        final int SAFE =4;
        for(int i=0;i<board.length;i++){
           for(int j=0;j<board[0].length;j++){
               if( isPieceNextTo(EMPTY, board, moveX, moveY) ){newboard[i][j]=SAFE;}
           }
        }
        changing=true;
        
        //checks if an entire group is in danger, or if they touch a safe tile
        while(changing){
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[0].length;j++){
                    changing=false;
                     if(newboard[i][j]==piece[player]){
                         if( isPieceNextTo(SAFE, board, moveX, moveY) ){
                             changing = true;
                             newboard[i][j]=SAFE;
                         }
                     }
                }
            }
        }
        //if the newboard spot didn't get changed to a 4, it's gonna get killed, thus, they were committing suicide
        if(newboard[moveX][moveY]==piece[player]){
         return false;   
        }
        
        return true;
    }

    /**
     * alternate whose turn it is to move.
     */
    public void changeTurn(){
        turn=1-turn;
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
        int piece[] = {BLACK, WHITE};
        if(isLeagalMove(player, moveX, moveY)){
            board[moveX][moveY]= piece[player];
            
            //this section deletes surrounded tiles
            final int INDANGER = 4;
            boolean danger=true;
            for(int i=0;i<board.length;i++){
                for(int j=0;j<board[0].length;j++){
                    danger=true;
                    if(board[i][j]==-piece[player]){
                        if(!isPieceNextTo(EMPTY, board, i, j)){board[i][j]=INDANGER;}
                    }
                }
            }
            boolean changing=true;
            while(changing){
                changing=false;
                for(int i=0;i<board.length;i++){
                   for(int j=0;j<board[0].length;j++){
                      if(board[i][j]==INDANGER){

                          if( isPieceNextTo(-piece[player], board, i, j) ){
                              changing=true;
                              board[i][j]=-piece[player];
                          }
                        }
                     }
                 }
            }
            for(int i=0;i<board.length;i++){ //For loop to remove surrounded tiles, and add points
                for(int j=0;j<board[0].length;j++){
                    if(board[i][j]==INDANGER){
                        if(-piece[player]==WHITE){
                            whiteCaptures++;
                        }
                        if(-piece[player]==BLACK){
                            blackCaptures++;
                        }
                        board[i][j]=EMPTY;
                    }
                }
            }
            //the 'in danger' section surrounded by these comments is not tested
            
            return true;
        }

//        GoSurfaceView.setBoard(board);
        return false;
    }

    /**
     * returns whether a coordinate is next to a certain piece
     * @param piece the piece to check for in the surrounding
     * @param board the game board
     * @param x the coordinate to check the surrounding of
     * @param y the coordinate to check the surrounding of
     * @return true if piece exist surrounding coordinate, false otherwise
     */
    private static boolean isPieceNextTo( int piece, final int board[][], int x, int y){
        if(x>0 && board[x-1][y]==piece){
            return true;
        }
        if(y>0 && board[x][y-1]==piece){
            return true;
        }
        if(x<board.length-1 && board[x+1][y]==piece){
            return true;
        }
        if(y<board[0].length-1 && board[x][y+1]==piece){
            return true;
        }

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
        territoryProposal = boardDeepCopy(original.territoryProposal);
        board = boardDeepCopy(original.board);
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

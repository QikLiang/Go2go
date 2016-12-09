package edu.up.cs301.go2go;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Sam Symmes
 * November 8th 2016
 */

//100% not tested, might not even compile
public class GoComputerPlayer1 extends GameComputerPlayer {
    public GoComputerPlayer1(String name) {
        super(name);
    }

    //obvious start to this method copied from Qi's simple computer player
    protected void receiveInfo(GameInfo info) {
        //returns if there's incorrect input
        if (!(info instanceof GoGameState)) {
            Log.i( "message", info.toString() );
            return;
        }
        GoGameState state = (GoGameState) info;
        //returns if it's not the computer's turn
        if (state.getTurn() != playerNum) {
            return;
        }
        //Determines the move if it's time in the game to place a stone
        if(state.getStage() == GoGameState.MAKE_MOVE_STAGE) {
            //pass if the other has passed
            if (state.getTurnsPassed() > 0) {
                game.sendAction(new PassAction(this));
                return;
            }

            //make a list of all possible board positions after current turn
            ArrayList<GoGameState> boards = new ArrayList<GoGameState>(GoGameState.boardSize*GoGameState.boardSize);
            ArrayList<PutPieceAction> moves = new ArrayList<PutPieceAction>(GoGameState.boardSize*GoGameState.boardSize);
            //for each position on the board
            for(int i=0; i<GoGameState.boardSize; i++){
                for(int j=0; j<GoGameState.boardSize; j++){
                    //make a copy of board
                    GoGameState temp = new GoGameState(state);
                    //if the move is possible
                    if(temp.updateBoard(state.getTurn(), i, j)){
                        temp.changeTurn();
                        boards.add(temp);
                        moves.add(new PutPieceAction(this, i, j));
                    }
                }
            }

            //if no move is legal, pass
            if(boards.size()==0){
                game.sendAction(new PassAction(this));
                return;
            }

            //calculate how many layers of recursion the tablet can handle

            //test how many legal moves the other player might have
            int opponentMoves = 1;
            for(int i=0; i<GoGameState.boardSize; i++) {
                for (int j = 0; j < GoGameState.boardSize; j++) {
                    if (state.isLeagalMove(1 - state.getTurn(), i, j)) {
                        opponentMoves++;
                    }
                }
            }

            int yourMoves = boards.size()+1;
            int numEvals = yourMoves;
            boolean yourMove = true;
            int maxDepth = -1;
            //computation limit 10000 board positions
            while ( 10000>numEvals && yourMoves>0 && opponentMoves>0 ){
                maxDepth++;
                if (yourMove){
                    numEvals*=yourMoves;
                    yourMoves-=2;
                }else{
                    numEvals*=opponentMoves;
                    opponentMoves-=2;
                }
                yourMove = !yourMove;
            }
            Log.i("maxDepth", ""+maxDepth);


            //select the best (least favorable to opponent) move and play it
            //the default move is pass
            //make it slightly less favorable than other moves so it is not played unless it has to
            GoGameState temp = new GoGameState(state);
            temp.changeTurn();//pass move as default
            double score = -evauateScore(temp, maxDepth)+0.1;
            ArrayList<GameAction> bestMoves = new ArrayList<GameAction>();
            bestMoves.add(new PassAction(this));
            double tempScore;//check every legal move
            for(int i=0; i<boards.size(); i++){
                tempScore =-evauateScore(boards.get(i), maxDepth);
                if(score>tempScore){
                    bestMoves.clear();
                    bestMoves.add(moves.get(i));
                    score = tempScore;
                }else if( score==tempScore ){
                    bestMoves.add(moves.get(i));
                    score = tempScore;
                }
            }

            Log.i("eval score", ""+score);
            game.sendAction(bestMoves.get( (int)(Math.random()*bestMoves.size()) ));
            return;
        }

        //always agree to other player's proposal
        else if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE){
            game.sendAction(new SelectTerritoryAction(this, state.getTerritorySuggestion()));
        }
        else {
            game.sendAction(new AgreeTerritoryAction(this, true));
        }
    }

    /**
     * evaluate the score of a board position recursively using a mini-max system
     * @param board the board/game state to evaluate
     * @param level how many levels of recursion to perform
     * @return how favorable the position is for black within the next $level turns
     */
    protected static double evauateScore(GoGameState board, int level){
        if(level<=0){
            return -evauateScore(board);
        }

        //make a list of all possible board positions after current turn
        ArrayList<GoGameState> boards = new ArrayList<GoGameState>(GoGameState.boardSize*GoGameState.boardSize);
        //for each position on the board
        for(int i=0; i<GoGameState.boardSize; i++){
            for(int j=0; j<GoGameState.boardSize; j++){
                //make a copy of board
                GoGameState temp = new GoGameState(board);
                //if the move is possible
                if(temp.updateBoard(board.getTurn(), i, j)){
                    temp.changeTurn();
                    boards.add(temp);
                }
            }
        }

        if(boards.size()==0){
            return evauateScore(board);
        }

        //evauate the score of each board in boards
        GoGameState temp = new GoGameState(board);
        temp.changeTurn();
        double score = -evauateScore(temp, level-1);
        double tempScore;
        for (int i=0; i<boards.size(); i++){
            //what's good for enimy is bad for you
            tempScore = -evauateScore(boards.get(i), level-1);
            if(tempScore<score){
                score=tempScore;
            }
        }

        //evauate the score of each board
        //return the value of the score least favorable to opponent
        return score;
    }

    /**
     * evauate the board to see how advantagious the position is for the player to move
     * @param board the board/game state to evauate
     * @return positive if favorable to player to move, negative otherwise
     */
    protected static double evauateScore(GoGameState board){
        int score = board.getWhiteCaptures() - board.getBlackCaptures();
        if(board.getTurn()!=0){
            score *= -1;
        }
        return score;
    }
}
package edu.up.cs301.go2go;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by qi on 10/29/16.
 */

public class GoLocalGame extends LocalGame {

    GoGameState officialState = new GoGameState();
    private boolean gameEnded;
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new GoGameState(officialState));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        if(gameEnded){
            return "game over";
        }else {
            return null;
        }
    }

    @Override
    protected boolean makeMove(GameAction action) {
        //reject if not that player's turn
        if(getPlayerIdx(action.getPlayer()) != officialState.getTurn()){
            return false;
        }

        if(action instanceof PutPieceAction){
            //reject if not in right state of game
            if(officialState.getStage() != GoGameState.MAKE_MOVE_STAGE){
                return false;
            }

            PutPieceAction move = (PutPieceAction) action;
            //if move is legal
            if(officialState.isLeagalMove(getPlayerIdx(action.getPlayer()),
                    move.getX(), move.getY())){
                //update board position
                officialState.updateBoard(getPlayerIdx(action.getPlayer()),
                    move.getX(), move.getY());
                //switch player
                officialState.changeTurn();
                return true;
            }else {
                return false;
            }
        }

        //if it gets to the end without matching any actions, something is wrong
        return false;
    }
}

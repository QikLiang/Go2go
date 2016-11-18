package edu.up.cs301.go2go;

import android.util.Log;

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
        return officialState.getTurn()==playerIdx;
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
                Log.i("Wrong stage", ""+officialState.getStage());
                return false;
            }

            PutPieceAction move = (PutPieceAction) action;
            Log.i("Put Piece", ""+getPlayerIdx(move.getPlayer())+","+move.getX()+","+move.getY());
            //if move is legal
            if(officialState.isLeagalMove(getPlayerIdx(action.getPlayer()),
                    move.getX(), move.getY())){
                //update board position
                boolean moveMade = officialState.updateBoard(getPlayerIdx(action.getPlayer()),
                    move.getX(), move.getY());
                //switch player
                officialState.changeTurn();
                Log.i(moveMade ? "Success":"Failed", ""+getPlayerIdx(move.getPlayer())+","+move.getX()+","+move.getY());
                return true;
            }else {
                Log.i("Illegal move", ""+getPlayerIdx(move.getPlayer())+","+move.getX()+","+move.getY());
                return false;
            }
        }

        if(action instanceof PassAction){
            //reject if not in right state of game
            if(officialState.getStage() != GoGameState.MAKE_MOVE_STAGE){
                return false;
            }
            Log.i("Pass", ""+getPlayerIdx(((PassAction) action).getPlayer()));
            officialState.changeTurn();
            officialState.incrementPasses();
            return true;
        }

        if(action instanceof SelectTerritoryAction){
            //both players must first pass before a proposal can be made
            if(officialState.getTurnsPassed() < 2){
                return false;
            }

            SelectTerritoryAction proposal = (SelectTerritoryAction) action;
            //reject if proposal is empty
            if(proposal.getProposal() == null){
                return false;
            }

            Log.i("Select Territory", ""+getPlayerIdx(proposal.getPlayer()));
            officialState.changeTurn();
            officialState.setTerritoryProposal(proposal.getProposal());
            return true;
        }

        if(action instanceof AgreeTerritoryAction){
            //reject if not in right state of game
            if(officialState.getStage() != GoGameState.SELECT_TERRITORY_STAGE){
                return false;
            }

            boolean agreement = ((AgreeTerritoryAction) action).getAgreement();

            Log.i("agreement", ""+((AgreeTerritoryAction)action).getPlayer()+","+agreement);
            gameEnded = agreement;
            if(!agreement){
                officialState.setStage(GoGameState.MAKE_MOVE_STAGE);
            }
            return true;
        }

        //if it gets to the end without matching any actions, something is wrong
        return false;
    }
}

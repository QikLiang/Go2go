package edu.up.cs301.go2go;

import java.util.ArrayList;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by qi on 10/29/16.
 */

public class GoComputerPlayer0 extends GameComputerPlayer {
    public GoComputerPlayer0(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        //not the AI's business if it's not a game state
        if(! (info instanceof GoGameState) ){
            return;
        }

        GoGameState state = (GoGameState) info;

        //not it's business if it's not its turn
        if(state.getTurn()!=playerNum){
            return;
        }

        if(state.getStage() == GoGameState.MAKE_MOVE_STAGE){
            //pass if the other has passed
            if(state.getTurnsPassed()>0){
                game.sendAction(new PassAction(this));
                return;
            }

            //generate random move
            ArrayList<PutPieceAction> moves = new ArrayList<PutPieceAction>();
            for(int i=0; i<GoGameState.boardSize; i++){
                for(int j=0; j<GoGameState.boardSize; j++){
                    moves.add(new PutPieceAction(this, i, j));
                }
            }
            PutPieceAction move;
            do {
                int random = (int) (Math.random()*moves.size());
                move = moves.get(random);
                moves.remove(random);
            }while (!state.isLeagalMove(playerNum, move.getX(), move.getY()));
            game.sendAction(move);
        }

        //always agree to other player's proposal
        if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE){
            game.sendAction(new AgreeTerritoryAction(this, true));
        }
    }
}

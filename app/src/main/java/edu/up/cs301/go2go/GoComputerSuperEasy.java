package edu.up.cs301.go2go;

import java.util.ArrayList;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Sam on 11/17/16.
 */

public class GoComputerSuperEasy extends GameComputerPlayer {
    public GoComputerSuperEasy(String name) {
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
                Log.i("AI:","Passed");
                game.sendAction(new PassAction(this));
                return;
            }

            //generate random move
            Random rand = new Random();
            for(int i=0;i<40;i++){
                int x = rand.nextInt(9);
                int y = rand.nextInt(9);
                if(state.isLegalMove(playerNum,x,y)){
                    Log.i("AI:","Placed Piece");
                    PutPieceAcion myMove = new PutPieceAction(this,x,y);
                    game.sendAction(myMove);
                    return;
                }
            }
            
            //if this didn't place a piece
            Log.i("AI:","Passed");
            game.sendAction(new PassAction(this));
            return;
        }

        //always agree to other player's proposal
        if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE){
            Log.i("AI:","Agreed to Territory");
            game.sendAction(new AgreeTerritoryAction(this, true));
        }
    }
}

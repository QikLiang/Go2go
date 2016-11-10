package edu.up.cs301.go2go;

import java.util.ArrayList;

import edu.up.cs301.game.GameComputerPlayer;
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
            return;
        }
        GoGameState state = (GoGameState) info;
        //returns if it's not the computer's turn
        if (state.getTurn() != playerNum) {
            return;
        }
        //Determines the move if it's time in the game to place a stone
        if (state.getStage() == GoGameState.MAKE_MOVE_STAGE) {
            for (int i = 0; i < GoGameState.boardSize; i++) {
                for (int j = 0; j < GoGameState.boardSize; j++) {

                }
            }
        }
    }
}

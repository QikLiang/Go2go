package edu.up.cs301.go2go;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by qi on 10/29/16.
 */

public class PutPieceAction extends GameAction {

    //coordinates to put the piece
    private int x;
    private int y;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PutPieceAction(GamePlayer player, int initX, int initY) {
        super(player);
        x = initX;
        y = initY;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}

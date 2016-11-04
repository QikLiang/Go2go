package edu.up.cs301.go2go;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by qi on 10/29/16.
 */

public class AgreeTerritoryAction extends GameAction {

    private boolean agreement;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public AgreeTerritoryAction(GamePlayer player, boolean initAgreement) {
        super(player);
        agreement = initAgreement;
    }

    public boolean getAgreement(){
        return agreement;
    }
}

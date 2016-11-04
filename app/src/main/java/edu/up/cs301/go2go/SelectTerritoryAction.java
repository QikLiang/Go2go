package edu.up.cs301.go2go;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by qi on 10/29/16.
 */

public class SelectTerritoryAction extends GameAction {
    private int proposal[][];
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public SelectTerritoryAction(GamePlayer player, int initProposal[][]) {
        super(player);
        proposal = new int[initProposal.length][];
        for(int i=0; i<initProposal.length; i++){
            proposal[i] = new int[initProposal[i].length];
            for(int j=0; j<initProposal[i].length; j++){
                //deep copy
                proposal[i][j] = initProposal[i][j];
            }
        }
    }

    /**
     * returns a deep copy of the proposal
     * @return
     */
    public int[][] getProposal(){
        int temp[][] = new int[proposal.length][];
        for(int i=0; i<proposal.length; i++){
            temp[i] = new int[proposal[i].length];
            for(int j=0; j<proposal[i].length; j++){
                //deep copy
                temp[i][j] = proposal[i][j];
            }
        }
        return temp;
    }
}

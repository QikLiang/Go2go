package edu.up.cs301.go2go;

import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by qi on 10/29/16.
 */

public class GoHumanPlayer extends GameHumanPlayer {
    public GoHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(R.layout.go_human_player_portrait);
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }
}

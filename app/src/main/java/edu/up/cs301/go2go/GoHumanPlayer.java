package edu.up.cs301.go2go;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.tictactoe.TTTState;

/**
 * Created by qi on 10/29/16.
 */

public class GoHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {

    GoSurfaceView surfaceView;
    Activity theActivity;
    Button[] buttons = new Button[4];
    public GoHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return theActivity.findViewById(R.id.top_gui_layout);
    }

    public void setAsGui(GameMainActivity activity) {

        theActivity = activity;
        activity.setContentView(R.layout.go_human_player);
        surfaceView = (GoSurfaceView)activity.findViewById(R.id.boardView);
        surfaceView.setOnTouchListener(this);
        theActivity.findViewById(R.id.top_gui_layout).setOnClickListener(this);

    }

    @Override
    public void receiveInfo(GameInfo info) {

        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
        }
        else if (!(info instanceof GoGameState))
            // if we do not have a TTTState, ignore
            return;
        else {
//            surfaceView.setState((GoGameState)info);
            surfaceView.invalidate();
            Log.i("human player", "receiving");
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() != MotionEvent.ACTION_UP)
            return true;

        int x = (int) event.getX();
        int y = (int) event.getY();
        return false;
    }

    public void onClick(View v) {

    }
}

package edu.up.cs301.go2go;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    GoGameState state;
    public Button pass,draw,resign,territory;
    public TextView playerScore,playerStonesPlaced,playerStonesCaptured,enemyScore,enemyStonesPlaced,enemyStonesCaptured;


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
        theActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        surfaceView = (GoSurfaceView)activity.findViewById(R.id.boardView);
        surfaceView.setOnTouchListener(this);

        pass = (Button)activity.findViewById(R.id.passButton);
        pass.setOnClickListener(this);
        draw = (Button)activity.findViewById(R.id.drawButton);
        draw.setOnClickListener(this);
        resign = (Button)activity.findViewById(R.id.resignButton);
        resign.setOnClickListener(this);
        territory = (Button)activity.findViewById(R.id.territorySelectButton);
        territory.setOnClickListener(this);

        playerScore = (TextView)activity.findViewById(R.id.playerScoreText);
        playerStonesPlaced = (TextView)activity.findViewById(R.id.stonesPlacedText);
        playerStonesCaptured = (TextView)activity.findViewById(R.id.stonesCapturedText);
        enemyScore = (TextView)activity.findViewById(R.id.enemyScoreText);
        enemyStonesPlaced = (TextView)activity.findViewById(R.id.enemyStonesPlacedText);
        enemyStonesCaptured = (TextView)activity.findViewById(R.id.enemyStonesCaptured);
        surfaceView.invalidate();

    }

    @Override
    public void receiveInfo(GameInfo info)
    {

        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
        }
        else if (!(info instanceof GoGameState))
            // if we do not have a TTTState, ignore
            return;
        else {
            state = (GoGameState) info;
            Log.i("human player", "receiving");
        }
    }

    public boolean onTouch(View v, MotionEvent event)
    {
        double width = GoSurfaceView.cWidth;
        double height = GoSurfaceView.cHeight;
        double x = (double)event.getX();
        double y = (double)event.getY();
        if(event.getAction() != MotionEvent.ACTION_UP)
        {
            v.invalidate();
            return true;
        }

        int xPos = (int)((x/width)*9);
        int yPos = (int)((y/height)*9);
        PutPieceAction action = new PutPieceAction(this,xPos,yPos);
        if(!state.isLeagalMove(playerNum,xPos,yPos)) {
            game.sendAction(action);
        }
        else{}

//        surfaceView.getBoard(state.getBoard());//gets the board for the
        surfaceView.invalidate();
        return true;
    }

	public void onClick(View v)
    {
		if(v == pass)
		{
            PassAction action = new PassAction(this);
            game.sendAction(action);
		}
		if(v == draw)
		{

		}
		if(v == resign)
		{

		}
		if(v == territory)
		{

		}
	}
}

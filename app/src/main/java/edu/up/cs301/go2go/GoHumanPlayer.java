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
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by qi on 10/29/16.
 */

public class GoHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {

    boolean firstTime = true;
    GoSurfaceView surfaceView;
    Activity theActivity;
    GoGameState state;
    public static GameMainActivity humanPlayerStatic;
    public Button pass,territory;
    public TextView playerScore,playerStonesCaptured,enemyScore,enemyStonesCaptured,stage;


    public GoHumanPlayer(String name) {//made this change
        super(name);
    }

    @Override
    public View getTopView() {
        return theActivity.findViewById(R.id.top_gui_layout);
    }

    public void setAsGui(GameMainActivity activity) {

        theActivity = activity;
        humanPlayerStatic = activity;
        activity.setContentView(R.layout.go_human_player);
        theActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        surfaceView = (GoSurfaceView)activity.findViewById(R.id.boardView);
        surfaceView.setOnTouchListener(this);

        pass = (Button)activity.findViewById(R.id.passButton);
        pass.setOnClickListener(this);
        territory = (Button)activity.findViewById(R.id.territorySelectButton);
        territory.setOnClickListener(this);

        playerScore = (TextView)activity.findViewById(R.id.playerScoreText);
        playerStonesCaptured = (TextView)activity.findViewById(R.id.stonesCapturedText);
        enemyScore = (TextView)activity.findViewById(R.id.enemyScoreText);
        enemyStonesCaptured = (TextView)activity.findViewById(R.id.enemyStonesCaptured);
        stage = (TextView)activity.findViewById(R.id.stageView);
        surfaceView.invalidate();

    }

    @Override
    public void receiveInfo(GameInfo info)
    {

        if(firstTime){
            firstTime = false;
            //Set name displays
            ((TextView)theActivity.findViewById(R.id.playerName)).setText(name);
            if(allPlayerNames[0] == name){
                ((TextView)theActivity.findViewById(R.id.enemyName)).setText(allPlayerNames[1]);
            } else {
                ((TextView)theActivity.findViewById(R.id.enemyName)).setText(allPlayerNames[0]);
            }
        }

        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            flash(Color.rgb(255,0,0), 300);
            return;
        }
        else if (!(info instanceof GoGameState)) {
            // if we do not have a GoGameState, ignore
            return;
        }

        state = (GoGameState) info;

        surfaceView.setMyTurn(state.getTurn() == playerNum);

        //update gui for stage
        if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE) {
            stage.setText("select territory");
            pass.setText("submit proposal");
            territory.setText("");
        } else if(state.getStage() == GoGameState.AGREE_TERRITORY_STAGE){
            stage.setText("counterproposal");
            pass.setText("submit proposal");
            territory.setText("refuse proposal");
        } else {
            stage.setText("make move stage");
            pass.setText("pass");
            territory.setText("forfeit");
        }


        if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE){
            if(state.getTerritoryProposal() == null){
                state.setTerritoryProposal(state.getTerritorySuggestion());
            }
            GoSurfaceView.setProposal(state.getTerritoryProposal());
        }
        GoSurfaceView.setBoard(state.getBoard());
        if(playerNum==0){
            setCapturedText(state.getBlackCaptures(), enemyStonesCaptured);
            setCapturedText(state.getWhiteCaptures(), playerStonesCaptured);
        }else{
            setCapturedText(state.getWhiteCaptures(), enemyStonesCaptured);
            setCapturedText(state.getBlackCaptures(), playerStonesCaptured);
        }

        stage.invalidate();
        pass.invalidate();
        territory.invalidate();
        enemyStonesCaptured.invalidate();
        playerStonesCaptured.invalidate();
        surfaceView.invalidate();

    }

    private void setCapturedText(int num, TextView text){
        if(num==0){
            text.setText("captured no stones");
            return;
        }
        if(num==1){
            text.setText("captured 1 stone");
            return;
        }
        text.setText("captured "+num+" stones");
    }

    public boolean onTouch(View v, MotionEvent event)
    {
        if(event.getAction() != MotionEvent.ACTION_UP)
        {
            v.invalidate();
            return true;
        }

        double width = GoSurfaceView.cWidth;
        double height = GoSurfaceView.cHeight;
        double x = (double)event.getX();
        double y = (double)event.getY();
        int xPos = (int)((x/width)*9);
        int yPos = (int)((y/height)*9);

        if(state==null){
            return false;
        }
        //make move stage
        if(state.getStage() == GoGameState.MAKE_MOVE_STAGE)
        {
            PutPieceAction action = new PutPieceAction(this,xPos,yPos);
            game.sendAction(action);
        }//select/accept territory stage
        else
        {
            if(state.getTurn() == playerNum) {
                state.updateProposal(xPos, yPos);
                GoSurfaceView.setProposal(GoGameState.boardDeepCopy(state.getTerritoryProposal()));
            }
        }

        surfaceView.invalidate();
        return true;
    }

	public void onClick(View v)
    {
		if(v == pass)
		{
            if(state.getStage() == GoGameState.MAKE_MOVE_STAGE)
            {
                Log.i("onclick","sending pass action");
                PassAction action = new PassAction(this);
                game.sendAction(action);
                return;
            }
            if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE) //Acts as submit proposal
            {
                game.sendAction(new SelectTerritoryAction(this,state.getTerritoryProposal()));
                pass.setText("submit proposal");
                territory.setText("return to play");
                pass.invalidate();
                territory.invalidate();
            }
		}
		if(v == territory)
		{
            if(state.getStage() == GoGameState.MAKE_MOVE_STAGE) {
                game.sendAction(new ForfeitAction(this));
                return;
            }
            if(state.getStage() == GoGameState.AGREE_TERRITORY_STAGE) {
                game.sendAction(new AgreeTerritoryAction(this, false));
                return;
            }
		}
	}

    public static GameMainActivity getActivity(){return humanPlayerStatic;}
}

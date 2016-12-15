package edu.up.cs301.go2go;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.SoundPool;
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
    public Button leftButton,rightButton;
    public TextView playerScore,playerStonesCaptured,enemyScore,enemyStonesCaptured,stage;

    private int[][] originalTerritoryProposal;
    private int[][] lastBoard;


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

        leftButton = (Button)activity.findViewById(R.id.passButton);
        leftButton.setOnClickListener(this);
        rightButton = (Button)activity.findViewById(R.id.territorySelectButton);
        rightButton.setOnClickListener(this);

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

            GoSurfaceView.setEasterEgg(allPlayerNames[0], allPlayerNames[1]);

            //Set name displays
            ((TextView)theActivity.findViewById(R.id.playerName)).setText(name);
            if(allPlayerNames[0] == name){
                ((TextView)theActivity.findViewById(R.id.enemyName)).setText(allPlayerNames[1]);
            } else {
                ((TextView)theActivity.findViewById(R.id.enemyName)).setText(allPlayerNames[0]);
            }

            ((GoMainActivity)theActivity).initSounds();

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

        surfaceView.setMyTurn(state.getTurn() == playerNum,playerNum);

        originalTerritoryProposal = GoGameState.boardDeepCopy(state.getTerritoryProposal());

        //update gui for stage
        if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE) {
            stage.setText("select territory");
            leftButton.setText("submit proposal");
            rightButton.setText("");
        } else if(state.getStage() == GoGameState.AGREE_TERRITORY_STAGE){
            stage.setText("counter-proposal");
            leftButton.setText("agree w/ proposal");
            rightButton.setText("refuse proposal");
        } else if(state.getStage() == GoGameState.MAKE_MOVE_STAGE) {
            stage.setText("make move stage");
            leftButton.setText("pass");
            rightButton.setText("forfeit");
        }
        else{
            stage.setText("play again?");
            leftButton.setText("yes");
            rightButton.setText("no");
        }

        //disable buttons when not player's turn
        leftButton.setEnabled( state.getTurn() == playerNum );
        rightButton.setEnabled( state.getTurn() == playerNum );

        //Play sound if needed
        boolean didChange = false;
        if(lastBoard != null) {
            for (int i = 0; i < lastBoard.length; i++) {
                for (int j = 0; j < lastBoard[0].length; j++){
                    if(lastBoard[i][j] != state.getBoard()[i][j]){
                        didChange = true;
                    }
                }
            }
        }
        if(didChange){
            ((GoMainActivity)theActivity).pieceNoise();
        }
        lastBoard = state.getBoard();



        if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE || state.getStage() == GoGameState.AGREE_TERRITORY_STAGE){
            if(state.getTerritoryProposal() == null){
                state.setTerritoryProposal(state.getTerritorySuggestion());
            }
            surfaceView.setProposal(state.getTerritoryProposal());
        }
        surfaceView.setBoard(state.getBoard());
        surfaceView.setPrevMove( state.getPrevX(), state.getPrevY() );
        if(playerNum==0){
            setCapturedText(state.getBlackCaptures(), enemyStonesCaptured);
            setCapturedText(state.getWhiteCaptures(), playerStonesCaptured);
        }else{
            setCapturedText(state.getWhiteCaptures(), enemyStonesCaptured);
            setCapturedText(state.getBlackCaptures(), playerStonesCaptured);
        }

        stage.invalidate();
        leftButton.invalidate();
        rightButton.invalidate();
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

        double boundarySize = 0.05 * width;

        //check for non-move positions
        if(x < boundarySize || y < boundarySize || x > width - boundarySize || y > width - boundarySize){
            return true;
        }

        x -= boundarySize;
        y -= boundarySize;
        width -= (2*boundarySize);
        height -= (2*boundarySize);


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
        }//select/accept rightButton stage
        else
        {
            if(state.getTurn() == playerNum) {
                state.updateProposal(xPos, yPos);
                if( state.getStage() == GoGameState.AGREE_TERRITORY_STAGE ){
                    boolean diff = false;
                    for(int i = 0; i < GoGameState.boardSize; i++){
                        for(int j = 0; j < GoGameState.boardSize; j++){
                            if(originalTerritoryProposal[i][j] != state.getTerritoryProposal()[i][j]){
                                diff = true;
                            }
                        }
                    }
                    if(diff){
                        leftButton.setText("counter proposal");
                    } else {
                        leftButton.setText("agree w/ proposal");
                    }
                }
                leftButton.invalidate();
                surfaceView.setProposal(GoGameState.boardDeepCopy(state.getTerritoryProposal()));
            }
        }

        surfaceView.invalidate();
        return true;
    }

	public void onClick(View v)
    {
		if(v == leftButton)
		{
            if(state.getStage() == GoGameState.MAKE_MOVE_STAGE)
            {
                Log.i("onclick","sending leftButton action");
                PassAction action = new PassAction(this);
                game.sendAction(action);
                return;
            }
            if(state.getStage() == GoGameState.SELECT_TERRITORY_STAGE) //Acts as submit proposal
            {
                game.sendAction(new SelectTerritoryAction(this,state.getTerritoryProposal()));
            }
            if(state.getStage() == GoGameState.AGREE_TERRITORY_STAGE) //Acts as submit proposal
            {
                boolean diff = false;
                for(int i = 0; i < GoGameState.boardSize; i++){
                    for(int j = 0; j < GoGameState.boardSize; j++){
                        if(originalTerritoryProposal[i][j] != state.getTerritoryProposal()[i][j]){
                            diff = true;
                        }
                    }
                }
                if(diff){
                    game.sendAction(new SelectTerritoryAction(this,state.getTerritoryProposal()));
                } else {
                    game.sendAction(new AgreeTerritoryAction(this, true));
                }
            }
		}
		if(v == rightButton)
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

package edu.up.cs301.go2go;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

public class GoMainActivity extends GameMainActivity implements View.OnClickListener,View.OnTouchListener {


	Button pass,draw,resign,territory;
	Button[] buttons = {pass,draw,resign,territory};
	public GoSurfaceView goSurfaceView;

	public static final int PORT_NUMBER = 5213;

	/**
	 * a tic-tac-toe game is for two players. The default is human vs. computer
	 */
	@Override
	public GameConfig createDefaultConfig() {

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

		// yellow-on-blue GUI
		playerTypes.add(new GamePlayerType("Local Human Player") {
			public GamePlayer createPlayer(String name) {
				return new GoHumanPlayer(name);
			}
		});

		// dumb computer player
		playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
			public GamePlayer createPlayer(String name) {
				return new GoComputerPlayer0(name);
			}
		});

		// Create a game configuration class for Tic-tac-toe
		GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Go2go", PORT_NUMBER);

		// Add the default players
		defaultConfig.addPlayer("Human", 0); // yellow-on-blue GUI
		defaultConfig.addPlayer("Computer", 1); // dumb computer player

		// Set the initial information for the remote player
		defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI

		//done!
		return defaultConfig;
    }

	/**
	 * games onCreate Method
	 */
	public void onCreate() {


		goSurfaceView = (GoSurfaceView)findViewById(R.id.boardView);
		goSurfaceView.setOnTouchListener(this);
		setContentView(R.layout.go_human_player);

		pass = (Button) findViewById(R.id.passButton);
		draw = (Button) findViewById(R.id.drawButton);
		territory = (Button) findViewById(R.id.territorySelectButton);
		resign = (Button) findViewById(R.id.resignButton);

		TextView playerScore = (TextView) findViewById(R.id.playerScoreText);
		TextView playerStonesPlaced = (TextView) findViewById(R.id.stonesPlacedText);
		TextView playerStonesCaptured = (TextView) findViewById(R.id.stonesCapturedText);
		TextView enemyScore = (TextView) findViewById(R.id.enemyScoreText);
		TextView enemyStonesPlaced = (TextView) findViewById(R.id.enemyStonesPlacedText);
		TextView enemyStonesCaptured = (TextView) findViewById(R.id.enemyStonesCaptured);

		for(Button b: buttons)
		{
			b.setOnClickListener(this);
		}
	}


	public void onClick(View v)
	{
		super.onClick(v);
		if(v == pass)
		{

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
	public boolean onTouch(View v, MotionEvent mo)
	{
		int x = (int)mo.getX();
		int y = (int)mo.getY();
		int action = mo.getAction();
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE)
		{

		}
		goSurfaceView.invalidate();
		return true;
	}

	@Override
    public LocalGame createLocalGame() {
        return new GoLocalGame();
    }
}

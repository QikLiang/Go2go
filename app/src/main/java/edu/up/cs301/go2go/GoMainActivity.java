package edu.up.cs301.go2go;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import edu.up.cs301.animation.AnimationSurface;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

public class GoMainActivity extends GameMainActivity implements View.OnClickListener {


	Button pass,draw,resign,territory;
	TextView playerScore,playerStonesPlaced,playerStonesCaptured,enemyScore,enemyStonesPlaced,nemyStonesCaptured;
	private SurfaceView GoSurfaceView;

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
		setContentView(R.layout.go_human_player_portrait);

		GoSurfaceView = (SurfaceView) findViewById(R.id.gameBoardView);
		GoSurfaceView.setOnClickListener(this);

		pass = (Button) findViewById(R.id.passButton);
		pass.setOnClickListener(this);
		draw = (Button) findViewById(R.id.drawButton);
		draw.setOnClickListener(this);
		territory = (Button) findViewById(R.id.territorySelectButton);
		territory.setOnClickListener(this);
		resign = (Button) findViewById(R.id.resignButton);
		resign.setOnClickListener(this);

		TextView playerScore = (TextView) findViewById(R.id.playerScoreText);
		TextView playerStonesPlaced = (TextView) findViewById(R.id.stonesPlacedText);
		TextView playerStonesCaptured = (TextView) findViewById(R.id.stonesCapturedText);
		TextView enemyScore = (TextView) findViewById(R.id.enemyScoreText);
		TextView enemyStonesPlaced = (TextView) findViewById(R.id.enemyStonesPlacedText);
		TextView enemyStonesCaptured = (TextView) findViewById(R.id.enemyStonesCaptured);

	}


	public void onClick(View v)
	{
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
		if(v == GoSurfaceView)
		{

		}
	}

	@Override
    public LocalGame createLocalGame() {
        return new GoLocalGame();
    }
}

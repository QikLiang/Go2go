package edu.up.cs301.go2go;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

public class GoMainActivity extends GameMainActivity implements View.OnClickListener {


	private static int[] buttonID = {R.id.passButton,R.id.drawButton,R.id.territorySelectButton,R.id.resignButton};
	private static int[] textViewID = {R.id.playerScoreText,R.id.stonesCapturedText,R.id.stonesPlacedText,R.id.enemyScoreText
	,R.id.enemyStonesCaptured,R.id.enemyStonesPlacedText};
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

	public void onCreate() {
			setContentView(R.layout.go_human_player_portrait);
		GoSurfaceView = (SurfaceView) findViewById(R.id.gameBoardView);

	}

	@Override
    public LocalGame createLocalGame() {
        return new GoLocalGame();
    }
}

package edu.up.cs301.go2go;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.media.AudioManager;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

public class GoMainActivity extends GameMainActivity{

	public static final int PORT_NUMBER = 5213;

	SoundPool mySoundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    int placeNoise;
	MediaPlayer musicPlayer;

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

		playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new GoComputerPlayer1(name);
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


	}

	public void initSounds() {
		placeNoise = mySoundPool.load(this, R.raw.place_noise, 1);
		musicPlayer = MediaPlayer.create(this, R.raw.music);
        musicPlayer.setLooping(true);
        musicPlayer.start();

	}

    public void pieceNoise(){
        mySoundPool.play(placeNoise, 1f, 1f, 1, 0, 4f);
    }

	@Override
	public LocalGame createLocalGame()
	{
		return new GoLocalGame();
	}
}

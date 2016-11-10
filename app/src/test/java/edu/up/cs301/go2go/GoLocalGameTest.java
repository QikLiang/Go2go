package edu.up.cs301.go2go;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by symmes19 on 11/9/2016.
 */
public class GoLocalGameTest {

    @Test
    public void testSendUpdatedStateTo() throws Exception {

    }

    @Test
    public void testCanMove() throws Exception {
        GoLocalGame game = new GoLocalGame();
        GoGameState state = game.officialState;
        assertTrue(game.canMove(0));
        assertTrue(!game.canMove(1));
    }

    @Test
    public void testCheckIfGameOver() throws Exception {
        GoLocalGame game = new GoLocalGame();
        assertEquals(null,game.checkIfGameOver());
    }

    @Test
    public void testMakeMove() throws Exception {

    }
}
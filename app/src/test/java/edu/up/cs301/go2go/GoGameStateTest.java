package edu.up.cs301.go2go;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by symmes19 on 11/9/2016.
 */
public class GoGameStateTest {

    @Test
    public void testIsLeagalMove() throws Exception {
        GoGameState state = new GoGameState();
        state.board[0][0] = 1;
        assertTrue(!state.isLeagalMove(1,0,0));
    }

    @Test
    public void testChangeTurn() throws Exception {
        GoGameState state = new GoGameState();
        state.changeTurn();
        assertEquals(1,state.getTurn());
        state.changeTurn();
        assertEquals(0,state.getTurn());
    }

    @Test
    public void testUpdateBoard() throws Exception {

    }

    @Test
    public void testSetGame() throws Exception {

    }

    @Test
    public void testGetStage() throws Exception {

    }

    @Test
    public void testGetTurnsPassed() throws Exception {

    }

    @Test
    public void testGetWhiteCaptures() throws Exception {

    }

    @Test
    public void testGetBlackCaptures() throws Exception {

    }

    @Test
    public void testGetTurn() throws Exception {

    }

    @Test
    public void testGetBoard() throws Exception {

    }

    @Test
    public void testGetTerritoryProposal() throws Exception {

    }

    @Test
    public void testSetTerritoryProposal() throws Exception {

    }

    @Test
    public void testSetStage() throws Exception {

    }

    @Test
    public void testBoardDeepCopy() throws Exception {
        GoGameState state = new GoGameState();
        int[][] myBoard = state.getBoard();
        myBoard[0][0] = 5;
        int[][] newBoard = new int[state.getBoard().length][state.getBoard()[0].length];
        newBoard = state.boardDeepCopy(myBoard);
        assertEquals(newBoard[0][0],5);
    }

    @Test
    public void testIncrementPasses() throws Exception {
        GoGameState state = new GoGameState();
        assertEquals(state.getTurnsPassed(),0);
        state.incrementPasses();
        assertEquals(state.getTurnsPassed(),1);
    }
}
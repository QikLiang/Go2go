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
    public void testSetGetStage() throws Exception {
        GoGameState state = new GoGameState();
        state.setStage(GoGameState.MAKE_MOVE_STAGE);
        assertTrue("get stage", state.getStage() == GoGameState.MAKE_MOVE_STAGE);
    }

    @Test
    public void testGetTurnsPassed() throws Exception {
        GoGameState state = new GoGameState();
        state.incrementPasses();
        assertTrue("get turns passed", state.getTurnsPassed() == 1);
    }

    @Test
    public void testGetWhiteCaptures() throws Exception {

    }

    @Test
    public void testGetBlackCaptures() throws Exception {

    }

    @Test
    public void testGetTurn() throws Exception {
        GoGameState state = new GoGameState();
        assertTrue("get turn", state.getTurn()==0);
        state.changeTurn();
        assertTrue("get turn", state.getTurn()==1);
    }

    @Test
    public void testGetBoard() throws Exception {
        GoGameState state = new GoGameState();
        int board[][] = state.getBoard();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                assertTrue("empty board", board[i][j]==0);
            }
        }
    }

    @Test
    public void testSetGetTerritoryProposal() throws Exception {
        GoGameState state = new GoGameState();
        int board[][] = state.getTerritoryProposal();
        assertTrue("initial proposal", state.getTerritoryProposal()==null);
        int array[][] ={{1,0,1,1,0,1,1,1,0},{-1,0,-1,-1,0,-1,-1,0,-1},
        {-1,0,-1,-1,0,-1,-1,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},
        {1,0,1,0,1,0,1,0,1},{1,0,1,0,0,1,0,1,1},{0,1,0,1,1,0,1,1,1},{0,1,1,1,0,0,0,0,0}};
        state.setTerritoryProposal(array);
        board = state.getTerritoryProposal();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                assertTrue("empty board", board[i][j]==array[i][j]);
            }
        }
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
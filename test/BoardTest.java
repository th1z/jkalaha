/**
 * 
 */
package kalaha.test;

import java.util.Arrays;

import junit.framework.TestCase;

import kalaha.model.gameModel.Board;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class BoardTest extends TestCase {

	/**
	 * Test method for {@link kalaha.game.Board#isValidMove(int)}.
	 */
	public final void testIsValidMove() {
		int[][] pits = { { 6, 6, 6, 6, 6, 6, 0 }, { 6, 6, 6, 6, 6, 6, 0 } };
		int startPlayer = 0;
		Board board = new Board(pits, startPlayer);

		assertTrue(board.isValidMove(0));
		assertTrue(board.isValidMove(1));
		assertFalse(board.isValidMove(6));
	}

	/**
	 * Test method for {@link kalaha.game.Board#getValidMoves()}.
	 */
	public final void testGetValidMoves() {
		int[][] pits = { { 6, 6, 6, 6, 6, 6, 0 }, { 6, 6, 6, 6, 6, 6, 0 } };
		int startPlayer = 0;
		Board board = new Board(pits, startPlayer);
		boolean[][] supposedValidMoves = {
				{ true, true, true, true, true, true, false },
				{ false, false, false, false, false, false, false } };
		boolean[][] moves = new boolean[board.getPits().length][board.getPits()[0].length];

		for (int i = 0; i < board.getPits()[0].length - 1; i++) {
			moves[startPlayer][i] = board.getValidMoves()[i];
		}

		for (int i = 0; i < supposedValidMoves.length; i++)
			if (!(Arrays.equals(supposedValidMoves[i], moves[i]))) {
				fail("Test");
			}

		// for (int i = 0; i < moves.length; i++)
		// assertTrue(board.isValidMove(moves[i]));
	}

	/**
	 * Test method for {@link kalaha.game.Board#doMove(int)}.
	 */
	public final void testDoMove() {
		// Board zum Testen erstellen
		int[][] pits = { { 6, 6, 6, 6, 6, 6, 0 }, { 6, 6, 6, 6, 6, 6, 0 } };
		int startPlayer = 0;
		Board board = new Board(pits, startPlayer);

		// Testfall 1: Spieler darf nochmal
		int[][] supposedPits = { { 0, 7, 7, 7, 7, 7, 1 },
				{ 6, 6, 6, 6, 6, 6, 0 } };
		int supposedPlayer = 0;
		int supposedLastMove = 0;

		Board newBoard = board.doMove(0);
		assertTrue(newBoard.equals(new Board(supposedPits, supposedPlayer,
				supposedLastMove)));

		// Testfall 2: normaler Zug
		supposedPits = new int[][] { { 6, 6, 6, 6, 6, 0, 1 },
				{ 7, 7, 7, 7, 7, 6, 0 } };
		supposedPlayer = 1;
		supposedLastMove = 5;

		newBoard = board.doMove(5);
		assertTrue(newBoard.equals(new Board(supposedPits, supposedPlayer,
				supposedLastMove)));

		// neues Testboard
		pits = new int[][] { { 6, 1, 0, 6, 6, 6, 0 }, { 6, 6, 6, 6, 6, 6, 0 } };
		startPlayer = 0;
		board = new Board(pits, startPlayer);

		// Testfall 3: Token stehlen 1
		supposedPits = new int[][] { { 6, 0, 0, 6, 6, 6, 7 },
				{ 6, 6, 6, 0, 6, 6, 0 } };
		supposedPlayer = 1;
		supposedLastMove = 1;

		newBoard = board.doMove(1);
		assertTrue(newBoard.equals(new Board(supposedPits, supposedPlayer,
				supposedLastMove)));

		// neues Testboard
		pits = new int[][] { { 1, 0, 8, 8, 8, 1, 3 }, { 8, 0, 10, 8, 8, 8, 1 } };
		startPlayer = 0;
		board = new Board(pits, startPlayer);

		// Testfall 4: Token stehlen 2
		supposedPits = new int[][] { { 0, 0, 8, 8, 8, 1, 12 },
				{ 8, 0, 10, 8, 0, 8, 1 } };
		supposedPlayer = 1;
		supposedLastMove = 0;

		newBoard = board.doMove(0);
		assertTrue(newBoard.equals(new Board(supposedPits, supposedPlayer,
				supposedLastMove)));

	}

	/**
	 * Test method for {@link kalaha.game.Board#isGameOver()}.
	 */
	public final void testIsGameOver() {
		// Board zum Testen erstellen
		int[][] pits = { { 6, 6, 6, 6, 6, 6, 0 }, { 6, 6, 6, 6, 6, 6, 0 } };
		int startPlayer = 0;
		Board board = new Board(pits, startPlayer);
		
		assertFalse(board.isGameOver());
		
		pits = new int[][] { { 0, 0, 0, 0, 0, 0, 0 }, { 6, 6, 6, 6, 6, 6, 0 } };
		startPlayer = 0;
		board = new Board(pits, startPlayer);
		
		assertTrue(board.isGameOver());
		
		startPlayer = 1;
		board = new Board(pits, startPlayer);
		
		assertFalse(board.isGameOver());
	}

	/**
	 * Test method for {@link kalaha.game.Board#equals()}.
	 */
	public final void testequals() {
		int[][] pits = { { 6, 6, 6, 6, 6, 6, 0 }, { 6, 6, 6, 6, 6, 6, 0 } };
		int startPlayer = 0;
		Board board = new Board(pits, startPlayer);

		int[][] supposedPits = { { 6, 6, 6, 6, 6, 6, 0 },
				{ 6, 6, 6, 6, 6, 6, 0 } };
		int supposedPlayer = 0;

		assertTrue(board.equals(new Board(supposedPits, supposedPlayer)));
	}
}

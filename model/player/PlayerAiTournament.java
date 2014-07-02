package kalaha.model.player;

import java.util.Random;

import kalaha.model.gameModel.Board;

public class PlayerAiTournament extends Player {

	private static final int TIMELIMIT_DELTA = -1000;

	private static int max_depth = 12;

	private int playerSide;

	private int timeLimit;

	/**
	 * Create a new computer-player. This player uses a minimax-algorithm.
	 * 
	 * @param timeLimit
	 *            The timelimit for this player.
	 */
	public PlayerAiTournament(int timeLimit) {
		this.timeLimit = (timeLimit * 1000) + TIMELIMIT_DELTA;
	}

	@Override
	public Board setActive(Board board) {
		long waitUntil = System.currentTimeMillis() + timeLimit;
		int bestMove = getBestMove(board, waitUntil);
		if (bestMove == -1) {
			bestMove = getRandomMove(board);
		}
		return board.doMove(bestMove);
	}

	/**
	 * Calculates the best possible move to make.
	 * 
	 * @param board
	 *            The board from which calculations should be started.
	 * @param waitUntil
	 *            The time (in milliseconds) after which the calculation stops.
	 * @return The move to make.
	 */
	private int getBestMove(Board board, long waitUntil) {
		playerSide = board.getCurrentPlayer();
		int bestMove = -1;
		int value;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		boolean b[] = board.getValidMoves();
		for (int i = 0; i < b.length; i++) {

			if (System.currentTimeMillis() > waitUntil) {
				break;
			}

			if (b[i] == true) {
				Board newBoard = board.doMove(i);
				if (newBoard.getCurrentPlayer() != board.getCurrentPlayer()) {
					value = minMove(newBoard, max_depth - 1, alpha, beta,
							waitUntil);
				} else {
					value = maxMove(newBoard, max_depth - 1, alpha, beta,
							waitUntil);
				}
				if (value > alpha) {
					alpha = value;
					bestMove = i;
				}
			}
		}
		return bestMove;
	}

	/**
	 * Max-method of the minimax-algorithm to calculate own moves.
	 * 
	 * @param board
	 *            The board to use.
	 * @param depth
	 *            Remaining tree-depth to calculate.
	 * @param alpha
	 *            Alpha-Cut.
	 * @param beta
	 *            Beta-Cut.
	 * @param waitUntil
	 *            The time (in milliseconds) after which the calculation stops.
	 * @return Returns the best possible move to make.
	 */
	private int maxMove(Board board, int depth, int alpha, int beta,
			long waitUntil) {

		int value;
		if (depth <= 0 || board.isGameOver()) {
			return rateBoard(board);
		}
		boolean b[] = board.getValidMoves();
		for (int i = 0; i < b.length; i++) {
			if (System.currentTimeMillis() > waitUntil) {
				return alpha;
			}

			if (b[i] == true) {
				Board newBoard = board.doMove(i);
				if (newBoard.getCurrentPlayer() != board.getCurrentPlayer()) {
					value = minMove(newBoard, depth - 1, alpha, beta, waitUntil);
				} else {
					value = maxMove(newBoard, depth - 1, alpha, beta, waitUntil);
				}
				if (value >= beta) {
					return beta;
				}
				if (value > alpha) {
					alpha = value;
				}
			}
		}
		return alpha;
	}

	/**
	 * Min-method of minimax-algorithm to calculate the moves of the opponent.
	 * 
	 * @param board
	 *            The board to use.
	 * @param depth
	 *            Remaining tree-depth to calculate.
	 * @param alpha
	 *            Alpha-Cut.
	 * @param beta
	 *            Beta-Cut.
	 * @param waitUntil
	 *            The time (in milliseconds) after which the calculation stops.
	 * @return Returns the best possible move to make.
	 */
	private int minMove(Board board, int depth, int alpha, int beta,
			long waitUntil) {
		int value;
		if (depth <= 0 || board.isGameOver() == true) {
			return rateBoard(board);
		}
		boolean b[] = board.getValidMoves();
		for (int i = 0; i < b.length; i++) {
			if (System.currentTimeMillis() > waitUntil) {
				return beta;
			}

			if (b[i] == true) {
				Board newBoard = board.doMove(i);
				if (newBoard.getCurrentPlayer() != board.getCurrentPlayer()) {
					value = maxMove(newBoard, depth - 1, alpha, beta, waitUntil);
				} else {
					value = minMove(newBoard, depth - 1, alpha, beta, waitUntil);
				}
				if (value <= alpha) {
					return alpha;
				}
				if (value < beta) {
					beta = value;
				}
			}
		}
		return beta;
	}

	/**
	 * Rates the given board.
	 * 
	 * @param board
	 *            The board to rate.
	 * @return The rating.
	 */
	private int rateBoard(Board board) {
		return board.getPoints(playerSide);
	}

	@Override
	public void setPassive(Board board) {

	}

	/**
	 * Generates a random move for a given board.
	 * 
	 * @return A random move for the given board.
	 */
	public int getRandomMove(Board board) {
		Random generator = new Random();
		int max = board.getPits()[0].length - 1;
		int move = generator.nextInt(max);

		while (board.getValidMoves()[move] == false) {
			move = (move + 1) % max;
		}
		return move;
	}

	/**
	 * Sets the depth up to which the minimax-tree should be calculated.
	 */
	public static void setMax_Depth(int depth) {
		max_depth = depth;
	}
}

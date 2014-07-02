package kalaha.model.gameModel;

import kalaha.model.player.Player;

/**
 * 
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class GameThread extends Thread {

	private GameModel model;
	private Player player1;
	private Player player2;
	private Board board;
	private boolean bRun;

	/**
	 * Creates a new Game Thread.
	 * 
	 * @param model
	 *            The game model that should be used.
	 * @param p1
	 *            The first player playing the game.
	 * @param p2
	 *            The second player playing the game.
	 * @param startingPlayer
	 *            The number of the player starting the game.
	 */
	public GameThread(GameModel model, Player p1, Player p2, int startingPlayer) {

		this.model = model;
		this.player1 = p1;
		this.player2 = p2;
		this.board = new Board(startingPlayer);
		bRun = true;
	}

	/**
	 * Starts and runs the game.
	 */
	public void run() {

		while (bRun) {
			model.notifyObservers(board);
			Player op = getOpponent();
			board = getCurrentPlayer().setActive(board);
			op.setPassive(board);
			if (isGameOver() == true) {
				bRun = false;
			}
		}
	}

	/**
	 * Checks if the current Game is finished and notifies the observers.
	 * 
	 * @return <tt>false</tt> if the game is still running, <tt>true</tt>
	 *         otherwise.
	 */
	private boolean isGameOver() {

		if (board == null) {
			model.notifyObservers("TIMEOUT");
			return true;
		} else if (board.isGameOver()) {
			if (board.getPoints() > 0) {
				model.notifyObservers("P1WINS."+Math.abs(board.getPoints()));
			} else if (board.getPoints() < 0) {
				model.notifyObservers("P2WINS."+Math.abs(board.getPoints()));
			} else {
				model.notifyObservers("TIE");
			}
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Stops the currently running game.
	 */
	public void end() {
		bRun = false;
	}

	/**
	 * 
	 * @return The first player.
	 */
	public Player getPlayer1() {

		return player1;

	}

	/**
	 * 
	 * @return The second player.
	 */
	public Player getPlayer2() {

		return player2;

	}

	/**
	 * 
	 * @return The current player.
	 */
	public Player getCurrentPlayer() {

		if (board.getCurrentPlayer() == 0) {
			return player1;
		} else {
			return player2;
		}

	}

	/**
	 * 
	 * @return The player currently waiting for the opponent.
	 */
	public Player getOpponent() {

		if (board.getCurrentPlayer() == 0) {
			return player2;
		} else {
			return player1;
		}

	}

}

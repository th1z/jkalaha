/**
 * 
 */
package kalaha.model;

import java.util.Observer;

import kalaha.model.player.Player;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public interface ModelInterface {

	// public static enum Player { Player1 }

	// ----- Modify Model -----

	/**
	 * Creates a new game and starts it.
	 * 
	 * @param p1
	 *            The first player.
	 * @param p2
	 *            The second player.
	 * @param startingPlayer
	 *            Determines which player makes the first move.
	 */
	public void newGame(Player p1, Player p2, int startingPlayer);

	/**
	 * Makes a move for the given player.
	 * @param player The player (<tt>0</tt> or <tt>1</tt>). 
	 * @param pit The move to make.
	 */
	public void doMove(int player, int pit);

	/**
	 * Stops the current game.
	 */
	public void endGame();

	// ----- Observable -----

	public void addObserver(Observer o);

	public void deleteObserver(Observer o);

	public void notifyObservers(Object arg);

}

/**
 * 
 */
package kalaha.controller;

import kalaha.model.ModelInterface;
import kalaha.model.player.Player;
import kalaha.view.ViewInterface;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public interface ControllerInterface {

	// ----- Initialize -----

	/**
	 * Initializes the controller.
	 * 
	 * @param model
	 *            The model the controller should use.
	 * @param view
	 *            The view the controller should use.
	 */
	public void init(ModelInterface model, ViewInterface view);

	// ----- Modify Model -----

	/**
	 * Creates a new Game.
	 * 
	 * @param pType1
	 *            The type of the first player.
	 * @param p1Name
	 *            The name of the first player.
	 * @param p1TimeLimit
	 *            The timelimit for the first player.
	 * @param pType2
	 *            The type of the second player.
	 * @param p2Name
	 *            The name of the second player.
	 * @param p2TimeLimit
	 *            The timelimit for the second player.
	 * @param startingPlayer
	 *            The Player, who is allowed to make the first move.
	 * @param ip
	 *            The address of the server, if this is a network-game.
	 */
	public void newGame(Player.Type pType1, String p1Name, int p1TimeLimit, Player.Type pType2, String p2Name, int p2TimeLimit, int startingPlayer, String ip);

	/**
	 * Stops the current game.
	 */
	public void endGame();

}

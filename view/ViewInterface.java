/**
 * 
 */
package kalaha.view;

import java.util.Observer;

import kalaha.controller.Controller;
import kalaha.model.ModelInterface;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public interface ViewInterface extends Observer {

	// ----- Interface Creation -----

	/**
	 * Creates a new main window and shows it.
	 */
	public void createAndShowGui();

	// ----- Interface Updates -----

	/**
	 * Enables or disables the "new Game"-Item in the menu.
	 * 
	 * @param enable
	 *            <tt>true</tt> enables the item, <tt>false</tt> disables it.
	 */
	public void enableNewGameButton(boolean enable);

	/**
	 * Enables or disables the "end Game"-Item in the menu.
	 * 
	 * @param enable
	 *            <tt>true</tt> enables the item, <tt>false</tt> disables it.
	 */
	public void enableEndGameButton(boolean enable);

	/**
	 * Enables or disables the buttons for the first player.
	 * 
	 * @param enable
	 *            <tt>true</tt> enables the buttons, <tt>false</tt> disables
	 *            them.
	 */
	public void enablePlayer1Input(boolean enable);

	/**
	 * Enables or disables the buttons for the second player.
	 * 
	 * @param enable
	 *            <tt>true</tt> enables the buttons, <tt>false</tt> disables
	 *            them.
	 */
	public void enablePlayer2Input(boolean enable);

	// ----- Getter & Setter -----

	/**
	 * Sets the name to be displayed for the players.
	 * 
	 * @param player
	 *            The player for which name should be set (<tt>0</tt> or
	 *            <tt>1</tt>).
	 * @param name
	 *            The name to set.
	 */
	public void setName(int player, String name);

	/**
	 * Sets the timelimit to displayed for each player.
	 * 
	 * @param player
	 *            The player for which the timelimit should be set (<tt>0</tt>
	 *            or <tt>1</tt>).
	 * @param timelimit
	 *            The timelimit in seconds.
	 */
	public void setTimelimit(int player, int timelimit);

	/**
	 * Sets the model to be used by the UI.
	 * 
	 * @param model
	 *            The model to be used.
	 */
	public void setModel(ModelInterface model);

	/**
	 * Sets the controller to be used by the UI.
	 * 
	 * @param controller
	 *            The controller to be used.
	 */
	public void setController(Controller controller);

}

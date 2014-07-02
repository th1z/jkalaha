/**
 * 
 */
package kalaha.model.gameModel;

import java.util.Observable;

import kalaha.model.ModelInterface;
import kalaha.model.player.Player;
import kalaha.model.player.PlayerHuman;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class GameModel extends Observable implements ModelInterface {
	
	private GameThread game;
	
	// ----- Modify Model -----

	@Override
	public void newGame(Player p1, Player p2, int startingPlayer) {

		game = new GameThread(this, p1, p2, startingPlayer);
		game.start();

	}
	
	@Override
	public void doMove(int player, int pit) {
		if(player == 0) {
			if(game.getPlayer1().getClass() == PlayerHuman.class) {
				((PlayerHuman)game.getPlayer1()).doMove(pit);
			}
		} else if(player == 1) {
			if(game.getPlayer2().getClass() == PlayerHuman.class) {
				((PlayerHuman)game.getPlayer2()).doMove(pit);
			}			
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void endGame() {
		
		game.stop();
		game.end();
		game = null;

	}

	// ----- Observable -----

	@Override
	public void notifyObservers(Object arg) {

		super.setChanged();
		super.notifyObservers(arg);

	}



}

/**
 * 
 */
package kalaha.model.player;

import java.util.EventListener;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import kalaha.model.gameModel.Board;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class PlayerHuman extends Player implements EventListener {

	private final Semaphore available = new Semaphore(-1, true);
	private int pit = -1;
	private int timeLimit;

	/**
	 * Create a new human player.
	 * 
	 * @param timeLimit
	 *            The timelimit for this player.
	 */
	public PlayerHuman(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kalaha.model.player.Player#setActive(kalaha.model.gameModel.Board)
	 */
	@Override
	public Board setActive(Board board) {
		pit = -1;
		long waitUntil = System.currentTimeMillis() + timeLimit * 1000;
		while (System.currentTimeMillis() < waitUntil) {

			try {
				available.tryAcquire(10, TimeUnit.MILLISECONDS);
				if (pit != -1 && board.isValidMove(pit)) {
					available.release();
					return board.doMove(pit);
				}
				available.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * Makes a move with the given pit.
	 * 
	 * @param pit
	 *            The pit to use.
	 */
	public void doMove(int pit) {

		try {
			available.acquire();
			this.pit = pit;
			available.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setPassive(Board board) {

	}
}

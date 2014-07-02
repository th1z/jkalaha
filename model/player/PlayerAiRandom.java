/**
 * 
 */
package kalaha.model.player;

import java.util.Random;

import kalaha.model.gameModel.Board;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 *
 */
public class PlayerAiRandom extends Player {
	
	private Random rand;
	private int timeLimit;
	
	/**
	 * Create a new computer-player. This player uses a random-algorithm.
	 * @param timeLimit The timelimit for this player.
	 */
	public PlayerAiRandom(int timeLimit) {
		rand = new Random();
		this.timeLimit = timeLimit;
	}

	/* (non-Javadoc)
	 * @see kalaha.model.player.Player#setActive(kalaha.model.gameModel.Board)
	 */
	@Override
	public Board setActive(Board board) {
		long waitUntil = System.currentTimeMillis() + timeLimit * 1000;
		boolean b[] = board.getValidMoves();
		int pit = rand.nextInt(b.length);
		boolean repeat = b[pit];
		while(repeat == false){
			pit++;
			pit = pit % b.length;
			repeat = b[pit];
		}
		while(System.currentTimeMillis() < waitUntil) {
		}
		
		return board.doMove(pit);
	}

	@Override
	public void setPassive(Board board) {
		
	}


}

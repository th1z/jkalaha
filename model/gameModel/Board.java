/**
 * 
 */
package kalaha.model.gameModel;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class Board {

	// ----- Statics -----

	private static final int PLAYERCOUNT = 2;

	private static final int PITCOUNT = 7;

	private static final int BEANCOUNT = 6;

	private static final int PLAY_PITS = PITCOUNT - 2;

	private static final int KALAHA_PIT = PITCOUNT - 1;

	// ----- Privates -----

	/**
	 * The player who currently has to do his move (<tt>0</tt> or <tt>1</tt>).
	 */
	private int currentPlayer;

	/**
	 * The representation of the playing field. Each row represents a player
	 * with the Kalaha at the end of the row.
	 */
	private int pits[][];

	/**
	 * The last move made on this board.
	 */
	private int lastMove;

	// ----- Constructors -----

	/**
	 * Creates a new Board.
	 * 
	 * @param startPlayer
	 *            The number of the player, who starts the game (<tt>0</tt>
	 *            or <tt>1</tt>).
	 */
	public Board(int startPlayer) {

		this.currentPlayer = startPlayer;
		this.pits = new int[PLAYERCOUNT][PITCOUNT];
		this.lastMove = -1;
		for (int i = 0; i < PLAYERCOUNT; i++) {
			for (int j = 0; j <= PLAY_PITS; j++) {
				pits[i][j] = BEANCOUNT;
			}
		}

	}

	/**
	 * Creates a new Board.
	 * 
	 * @param pits
	 * @param startPlayer
	 *            The number of the player, who starts the game (<tt>0</tt>
	 *            or <tt>1</tt>).
	 */
	public Board(int[][] pits, int startPlayer) {

		this.currentPlayer = startPlayer;
		this.pits = new int[PLAYERCOUNT][PITCOUNT];
		for (int i = 0; i < PLAYERCOUNT; i++) {
			for (int j = 0; j <= KALAHA_PIT; j++) {
				this.pits[i][j] = pits[i][j];
			}
		}

	}

	/**
	 * Creates a new Board.
	 * 
	 * @param pits
	 * @param startPlayer
	 *            The number of the player, who starts the game (<tt>0</tt>
	 *            or <tt>1</tt>)
	 * @param lastMove
	 */
	public Board(int[][] pits, int startPlayer, int lastMove) {

		this(pits, startPlayer);
		this.lastMove = lastMove;

	}

	// ----- Public Methods -----

	/**
	 * @return A matrix containing all pits with their bean-counts.
	 */
	public int[][] getPits() {
		return pits;
	}

	/**
	 * 
	 * @param pit
	 *            The move to be checked.
	 * @return <tt>true</tt> if the move is valid, <tt>false</tt> otherwise.
	 */
	public boolean isValidMove(int pit) {

		if (pits[currentPlayer][pit] == 0) {
			return false;
		}
		return true;

	}

	/**
	 * Checks which moves the current player is able to do.
	 * 
	 * @return An array containing <tt>true</tt> or <tt>false</tt> for a
	 *         given move.
	 */
	public boolean[] getValidMoves() {

		boolean moves[] = new boolean[PITCOUNT - 1];
		for (int j = 0; j <= PLAY_PITS; j++) {
			moves[j] = isValidMove(j);
		}
		return moves;
	}

	/**
	 * Plays a given move on the board, distributing all tokens.
	 * 
	 * @param pit
	 *            The move to be made.
	 * @return The new Board, with the move done.
	 */
	public Board doMove(int pit) {

		lastMove = pit;
		Board newBoard = this.clone();
		int playerSide = currentPlayer;
		int currentPit = pit + 1;
		int tokens = newBoard.pits[currentPlayer][pit];

		newBoard.pits[currentPlayer][pit] = 0;

		while (tokens > 1) {

			if (currentPit == KALAHA_PIT) {

				if (playerSide == newBoard.currentPlayer) {
					newBoard.pits[playerSide][currentPit]++;
					tokens--;
				}

				playerSide = (playerSide + 1) % 2;
				currentPit = 0;

			}

			else {
				newBoard.pits[playerSide][currentPit]++;
				tokens--;
				currentPit++;
			}

		}

		if (currentPit == KALAHA_PIT) {

			if (playerSide == newBoard.currentPlayer) {
				newBoard.pits[playerSide][currentPit]++;
				return newBoard;
			} else {
				currentPit = 0;
				playerSide = (playerSide + 1) % 2;
			}

		}

		if (playerSide == newBoard.currentPlayer
				&& newBoard.pits[newBoard.currentPlayer][currentPit] == 0) {
			newBoard.pits[playerSide][KALAHA_PIT] += newBoard.pits[(newBoard.currentPlayer + 1) % 2][PITCOUNT
					- 2 - currentPit];
			newBoard.pits[(newBoard.currentPlayer + 1) % 2][PITCOUNT - 2
					- currentPit] = 0;
			newBoard.pits[playerSide][KALAHA_PIT]++;
		} else {
			newBoard.pits[playerSide][currentPit]++;
		}

		newBoard.currentPlayer = (newBoard.currentPlayer + 1) % 2;
		return newBoard;

	}

	/**
	 * Checks if the current player has moves left to make.
	 * 
	 * @return <tt>true</tt>, if there is no move left to be done for the
	 *         current Player, <tt>false</tt> otherwise.
	 */
	public boolean isGameOver() {

		for (int j = 0; j <= PLAY_PITS; j++) {
			if (pits[currentPlayer][j] != 0) {
				return false;
			}
		}
		return true;

	}

	public int getPoints() {

		if (isGameOver()) {
			for (int i = 0; i <= PLAY_PITS; i++) {
				pits[(currentPlayer + 1) % 2][KALAHA_PIT] += pits[(currentPlayer + 1) % 2][i];
				pits[(currentPlayer + 1) % 2][i] = 0;
			}
		}
		return pits[0][KALAHA_PIT] - pits[1][KALAHA_PIT];

	}

	public int getPoints(int player) {

		if (isGameOver()) {
			for (int i = 0; i <= PLAY_PITS; i++) {
				pits[(currentPlayer + 1) % 2][KALAHA_PIT] += pits[(currentPlayer + 1) % 2][i];
				pits[(currentPlayer + 1) % 2][i] = 0;
			}
		}
		return pits[player][KALAHA_PIT] - pits[(player + 1) % 2][KALAHA_PIT];
	}

	/**
	 * 
	 * @return the player doing his move currently.
	 */
	public int getCurrentPlayer() {

		return currentPlayer;

	}

	/**
	 * 
	 * @return The move last made on this board.
	 */
	public int getLastMove() {
		return lastMove;
	}

	// ------ Overridden Methods -----

	/**
	 * @return An exact copy of this board.
	 */
	@Override
	protected Board clone() {
		return new Board(pits, currentPlayer, lastMove);
	}

	/**
	 * @return <tt>true</tt> if the given object is equal to this board,
	 *         <tt>false</tt> otherwise.
	 */
	@Override
	public boolean equals(Object arg0) {

		if (!(arg0 instanceof Board)) {
			return false;
		}

		Board tmp = (Board) arg0;

		if (currentPlayer != tmp.currentPlayer) {
			return false;
		}

		if (lastMove != tmp.lastMove) {
			return false;
		}

		for (int i = 0; i < PLAYERCOUNT; i++) {
			for (int j = 0; j < PITCOUNT; j++) {
				if (pits[i][j] != tmp.pits[i][j]) {
					return false;
				}
			}
		}
		return true;

	}

	/**
	 * @return A String representation of this board.
	 */
	@Override
	public String toString() {

		String a1 = "";
		String a2 = "";
		for (int i = 0; i <= PLAY_PITS; i++) {
			a1 = a1 + (i) + ":[" + pits[0][i] + "] ";
		}
		for (int i = 0; i <= PLAY_PITS; i++) {
			a2 = a2 + (i) + ":[" + pits[1][i] + "] ";
		}

		return "CurrentPlayer: " + currentPlayer + "\n" + "Player1: " + a1
				+ " {{" + pits[0][6] + "}}" + "\n" + "Player2: " + a2 + " {{"
				+ pits[1][6] + "}}";
	}

}

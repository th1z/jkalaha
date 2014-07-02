package kalaha.model.player;

import java.util.ArrayList;

import kalaha.model.gameModel.Board;

public abstract class Player {

	public static final int PLAYER_1 = 0;

	public static final int PLAYER_2 = 1;

	public static final int PLAYER_RANDOM = 2;

	/**
	 * This method is invoked, when the player has a move to make.
	 * 
	 * @param board
	 *            The board on which the move should be made.
	 * @return The board with the move made.
	 */
	public abstract Board setActive(Board board);

	/**
	 * This method is invoked for the waiting player, after the current player had his move done.
	 * 
	 * @param board
	 *            The board on which the current player has to make his move.
	 */
	public abstract void setPassive(Board board);

	/**
	 * Defines the different player types.
	 */
	public static enum Type {

		Human(true, "Player", true, false, true, 10), Ai_Random(true, "AI Random", true, false, true, 3), Ai_Tournament(true, "AI Tournament", true, false, true, 10), Net_Player(false, "Net Player", false, true, true, 10);

		private boolean nameChoosable;

		private String defaultName;

		private boolean startChoosable;

		private boolean isNetPlayer;

		private boolean timeChoosable;

		private int defaultTime;

		Type(boolean nameChoosable, String defaultName, boolean startChoosable, boolean isNetPlayer, boolean timeChoosable, int defaultTime) {

			this.nameChoosable = nameChoosable;
			this.defaultName = defaultName;
			this.startChoosable = startChoosable;
			this.isNetPlayer = isNetPlayer;
			this.timeChoosable = timeChoosable;
			this.defaultTime = defaultTime;

		}

		// ----- Public Methods -----

		/**
		 * 
		 * Returns whether the players name is chooseable or not.
		 * 
		 * @return <code>true</code> if the players name is chooseable;
		 *         <code>false</code> otherwise.
		 */
		public boolean getNameChoosable() {

			return nameChoosable;

		}

		/**
		 * 
		 * Returns the default player name for this player type.
		 * 
		 * @return the default name.
		 */
		public String getDefaultName() {

			return defaultName;

		}

		/**
		 * 
		 * Returns whether the starting player is chooseable or not.
		 * 
		 * @return <code>true</code> if the starting player is choosable;
		 *         <code>false</code> otherwise.
		 */
		public boolean getStartChoosable() {

			return startChoosable;

		}

		/**
		 * 
		 * Returns whether the player type is a net player or a local one.
		 * 
		 * @return <code>true</code> if the starting player is a net player;
		 *         <code>false</code> if it is a local player.
		 */
		public boolean getIsNetPlayer() {

			return isNetPlayer;

		}

		/**
		 * 
		 * Returns whether the player type has a fixed time limit, or not.
		 * 
		 * @return <code>true</code> if the player type has a fixed time limit;
		 *         <code>false</code> otherwise.
		 */
		public boolean getTimeChoosable() {

			return timeChoosable;

		}

		/**
		 * 
		 * Returns the player types default time limit.
		 * 
		 * @return the default time limit.
		 */
		public int getDefaultTime() {

			return defaultTime;

		}

	}

	/**
	 * 
	 * Returns all different player types.
	 * 
	 * @return all different player types.
	 */
	public static Type[] getPlayerTypes() {

		return Type.values();

	}

	/**
	 * 
	 * Returns all local player types.
	 * 
	 * @return all local player types.
	 */
	public static Type[] getLocalPlayerTypes() {

		ArrayList<Type> local = new ArrayList<Type>();
		for (Type t : Type.values()) {
			if (t.getIsNetPlayer() == false) {
				local.add(t);
			}
		}
		return (Type[]) local.toArray((new Type[local.size()]));

	}

	/**
	 * 
	 * Returns all network player types.
	 * 
	 * @return all network player types.
	 */
	public static Type[] getNetPlayerTypes() {

		ArrayList<Type> net = new ArrayList<Type>();
		for (Type t : Type.values()) {
			if (t.getIsNetPlayer() == true) {
				net.add(t);
			}
		}
		return (Type[]) net.toArray((new Type[net.size()]));

	}
}

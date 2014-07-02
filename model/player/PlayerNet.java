/**
 * 
 */
package kalaha.model.player;

import java.rmi.RemoteException;

import ai1.mancala.server.MancalaServerRemote;
import kalaha.model.gameModel.Board;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class PlayerNet extends Player {

	private static final int TIMELIMIT_DELTA = 1000;

	private static final String RMI_LOOKUP = "MancalaServer";
	private static final int PORT = 22333;
	private static final int NET_TIMEOUT = 3600 * 1000;

	private MancalaServerRemote server;
	private ai1.mancala.server.Player player;
	private boolean isFirstMove;
	private boolean isSecondPlayer;
	private long timelimit;

	/**
	 * Create a new network-player and connect it to the server.
	 * @param ip The ip-address of the server.
	 * @param localPlayerName The name of this player.
	 * @param timeLimit The timelimit for this player.
	 */
	public PlayerNet(String ip, String localPlayerName, int timeLimit) {

		try {
			this.server = (MancalaServerRemote) java.rmi.registry.LocateRegistry
					.getRegistry(ip, PORT).lookup(RMI_LOOKUP);
			this.player = (ai1.mancala.server.Player) server
					.registerPlayer(localPlayerName);
			this.isSecondPlayer = !player.isFirstPlayer();
			this.isFirstMove = true;
			this.timelimit = (timeLimit * 1000) + TIMELIMIT_DELTA;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kalaha.model.player.Player#setActive(kalaha.model.gameModel.Board)
	 */
	@Override
	public Board setActive(Board board) {

		int move = 0;
		long waitUntil;
		try {
			if (isFirstMove) {
				isFirstMove = false;
				if (isSecondPlayer == false) {
					return new Board((board.getCurrentPlayer() + 1) % 2);
				} else {
					waitUntil = System.currentTimeMillis() + NET_TIMEOUT;
				}
			} else {
				if (isSecondPlayer == false) {
					waitUntil = System.currentTimeMillis() + NET_TIMEOUT;
					isSecondPlayer = true;
				} else {
					waitUntil = System.currentTimeMillis() + timelimit;
				}
			}
			while (move == 0) {
				move = server.consumeMove(player);
				if (System.currentTimeMillis() > waitUntil) {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return board.doMove(move - 1);
	}

	@Override
	public void setPassive(Board board) {

		try {
			if (board == null) {
				return;
			}
			int move = board.getLastMove();
			if (move >= 0) {
				server.sendMove(player, move + 1);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}

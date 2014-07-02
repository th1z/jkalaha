/**
 * 
 */
package kalaha.controller;

import kalaha.model.ModelInterface;
import kalaha.model.player.Player;
import kalaha.model.player.PlayerAiTournament;
import kalaha.model.player.PlayerAiRandom;
import kalaha.model.player.PlayerHuman;
import kalaha.model.player.PlayerNet;
import kalaha.view.ViewInterface;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class Controller implements ControllerInterface {

	private ModelInterface model;

	private ViewInterface view;

	// ----- Initialize -----

	public void init(ModelInterface model, ViewInterface view) {

		this.model = model;
		this.view = view;
		this.view.setModel(model);
		this.view.setController(this);
		this.model.addObserver(this.view);
		view.createAndShowGui();
		view.enableEndGameButton(false);

	}

	// ----- Modify Model -----

	public void newGame(Player.Type pType1, String p1Name, int p1TimeLimit, Player.Type pType2, String p2Name, int p2TimeLimit, int startingPlayer, String ip) {
		Player p1 = null;
		Player p2 = null;
		switch (pType1) {
		case Human:
			p1 = new PlayerHuman(p1TimeLimit);
			view.enablePlayer1Input(true);
			break;
		case Ai_Random:
			p1 = new PlayerAiRandom(p1TimeLimit);
			break;
		case Ai_Tournament:
			p1 = new PlayerAiTournament(p1TimeLimit);
			break;
		case Net_Player:
			p1 = new PlayerNet(ip, p2Name, p1TimeLimit);
			startingPlayer = 0;
			break;
		}
		switch (pType2) {
		case Human:
			p2 = new PlayerHuman(p2TimeLimit);
			view.enablePlayer2Input(true);
			break;
		case Ai_Random:
			p2 = new PlayerAiRandom(p2TimeLimit);
			break;
		case Ai_Tournament:
			p2 = new PlayerAiTournament(p2TimeLimit);
			break;
		case Net_Player:
			p2 = new PlayerNet(ip, p1Name, p2TimeLimit);
			startingPlayer = 1;

			break;
		}
		view.enableNewGameButton(false);
		view.enableEndGameButton(true);
		view.setTimelimit(0, p1TimeLimit);
		view.setTimelimit(1, p2TimeLimit);
		view.setName(0, p1Name);
		view.setName(1, p2Name);
		model.newGame(p1, p2, startingPlayer);

	}

	public void endGame() {
		view.enablePlayer1Input(false);
		view.enablePlayer2Input(false);
		view.enableNewGameButton(true);
		view.enableEndGameButton(false);
		model.endGame();
	}

}

/**
 * 
 */
package kalaha;

import kalaha.controller.Controller;
import kalaha.controller.ControllerInterface;
import kalaha.model.ModelInterface;
import kalaha.model.gameModel.GameModel;
import kalaha.model.player.PlayerAiTournament;
import kalaha.view.ViewInterface;
import kalaha.view.windowGui.WindowGui;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class Kalaha {

	private static ModelInterface model;
	private static ViewInterface view;
	private static ControllerInterface controller;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length > 0) {
			PlayerAiTournament.setMax_Depth(Integer.parseInt(args[0]));
		}
		
		model = new GameModel();
		view = new WindowGui();
		controller = new Controller();
		controller.init(model, view);

	}
}

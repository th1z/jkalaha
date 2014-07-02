/**
 * 
 */
package kalaha.view.windowGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

import kalaha.controller.Controller;
import kalaha.model.ModelInterface;
import kalaha.model.gameModel.Board;
import kalaha.model.player.Player;
import kalaha.view.ViewInterface;
import kalaha.view.windowGui.NewGameDialog;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
public class WindowGui implements ViewInterface, ActionListener {

	private static final String WIN_TITLE = "Kalaha";

	private static final Dimension WIN_DEFAULT_SIZE = new Dimension(800, 600);

	private static final String MENU_GAME_TITLE = "Game";

	private static final String MENU_GAME_NEW = "New Game";

	private static final String MENU_GAME_END = "End Game";

	private static final String MENU_GAME_EXIT = "Close";

	private static final String PLAYER_TURN = " turn";

	private static final String PLAYER_TIMEOUT = " had a timeout";

	private static final String PLAYER_TIE = "Tie";

	private static final String PLAYER_WINS = " wins by ";

	public static final int TIMER_DELAY = 1000;

	public static final ImageIcon[] imgE = new ImageIcon[5];

	public static final ImageIcon[] imgD = new ImageIcon[5];

	public static final ImageIcon[] imgK = new ImageIcon[5];

	private ModelInterface model;

	private Controller controller;

	private JFrame frame;

	private JPanel contentPane;

	private JMenuBar menuBar;

	private JMenuItem itemNewGame, itemEndGame;

	boolean p1InputEnabled;

	boolean p2InputEnabled;

	int p1Timelimit, p2Timelimit;

	String p1Name, p2Name;

	private JButton[][] playerpits;

	private JProgressBar timeBar;

	private Timer barTimer;

	// ----- Interface Creation -----

	/**
	 * Creates and shows the main window of the application.
	 */
	@Override
	public void createAndShowGui() {
		frame = new JFrame(WIN_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setStyle();
		contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);
		frame.setContentPane(contentPane);

		frame.setJMenuBar(createMenu());
		frame.getContentPane().add(createPanel(7));

		frame.setSize(WIN_DEFAULT_SIZE);
		frame.setLocationRelativeTo(null);
		frame.validate();
		frame.setVisible(true);
	}

	/**
	 * Sets the look-and-feel to native windows, if the application runs on
	 * windows.
	 */
	private void setStyle() {
		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				JFrame.setDefaultLookAndFeelDecorated(true);
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the main menu.
	 * 
	 * @return The created menu.
	 */
	private JMenuBar createMenu() {
		JMenu menu;
		JMenuItem menuItem;

		menuBar = new JMenuBar();

		menu = new JMenu(MENU_GAME_TITLE);
		menuBar.add(menu);

		itemNewGame = new JMenuItem(MENU_GAME_NEW);
		itemNewGame.setActionCommand("newGame");
		itemNewGame.addActionListener(this);
		menu.add(itemNewGame);

		itemEndGame = new JMenuItem(MENU_GAME_END);
		itemEndGame.setActionCommand("endGame");
		itemEndGame.addActionListener(this);
		menu.add(itemEndGame);

		menu.addSeparator();

		menuItem = new JMenuItem(MENU_GAME_EXIT);
		menuItem.setActionCommand("quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		return menuBar;
	}

	/**
	 * Creates a new Panel, where the controls for the players are placed.
	 * 
	 * @param numPits
	 *            The number of the pits for each player, including the Kalaha.
	 * @return The created panel.
	 */
	private JPanel createPanel(int numPits) {
		// imgE[0] = new ImageIcon("media" + File.separator + "pitE0.png");
		imgE[1] = new ImageIcon(getClass().getResource("/kalaha/media/pitE1.png"));
		imgE[2] = new ImageIcon(getClass().getResource("/kalaha/media/pitE2.png"));
		imgE[3] = new ImageIcon(getClass().getResource("/kalaha/media/pitE3.png"));
		imgE[4] = new ImageIcon(getClass().getResource("/kalaha/media/pitEM.png"));
		imgD[0] = new ImageIcon(getClass().getResource("/kalaha/media/pitD0.png"));
		imgD[1] = new ImageIcon(getClass().getResource("/kalaha/media/pitD1.png"));
		imgD[2] = new ImageIcon(getClass().getResource("/kalaha/media/pitD2.png"));
		imgD[3] = new ImageIcon(getClass().getResource("/kalaha/media/pitD3.png"));
		imgD[4] = new ImageIcon(getClass().getResource("/kalaha/media/pitDM.png"));
		imgK[0] = new ImageIcon(getClass().getResource("/kalaha/media/pitK0.png"));
		imgK[1] = new ImageIcon(getClass().getResource("/kalaha/media/pitK1.png"));
		imgK[2] = new ImageIcon(getClass().getResource("/kalaha/media/pitK2.png"));
		imgK[3] = new ImageIcon(getClass().getResource("/kalaha/media/pitK3.png"));
		imgK[4] = new ImageIcon(getClass().getResource("/kalaha/media/pitKM.png"));

		JPicturePanel panel = new JPicturePanel();

		playerpits = new JButton[2][numPits];
		timeBar = new JProgressBar();
		timeBar.setStringPainted(true);
		timeBar.setString("");

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c;

		for (int i = 0; i < playerpits.length; i++) {

			for (int j = 0; j < playerpits[0].length; j++) {
				playerpits[i][j] = new JButton(" ");
				c = new GridBagConstraints();
				if (i == 0) {
					c.gridx = j + 1;
				} else {
					c.gridx = playerpits[0].length - j - 1;
				}

				// c.fill = GridBagConstraints.BOTH;
				// c.insets=new Insets(30, 30, 30, 30);

				if (j == playerpits[0].length - 1) {
					c.gridy = 0;

					c.weightx = 1.0;
					c.weighty = 1.0;

					c.gridheight = 2;

					playerpits[i][j].setIcon(imgK[0]);
					playerpits[i][j].setDisabledIcon(imgK[0]);

				} else {
					c.gridy = 1 - i;

					c.weightx = 0.5;
					c.weighty = 0.5;

					playerpits[i][j].setIcon(imgD[0]);
					playerpits[i][j].setDisabledIcon(imgD[0]);

					if (i == 0) {
						playerpits[i][j].setActionCommand("P1." + Integer.toString(j));
					} else {
						playerpits[i][j].setActionCommand("P2." + Integer.toString(j));
					}
					playerpits[i][j].addActionListener(this);
				}
				panel.add(playerpits[i][j], c);
				playerpits[i][j].setEnabled(false);

				playerpits[i][j].setVerticalTextPosition(JButton.CENTER);
				playerpits[i][j].setHorizontalTextPosition(JButton.CENTER);
				playerpits[i][j].setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
				playerpits[i][j].setForeground(new Color(0, 0, 0));
				playerpits[i][j].setBorder(BorderFactory.createEmptyBorder());
			}
		}

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = playerpits[0].length + 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		panel.add(timeBar, c);

		barTimer = new Timer(TIMER_DELAY, this);
		barTimer.setActionCommand("BarTimer");

		return panel;
	}

	// ----- Interface Updates -----

	@Override
	public void enableNewGameButton(boolean enable) {
		itemNewGame.setEnabled(enable);
	}

	@Override
	public void enableEndGameButton(boolean enable) {
		itemEndGame.setEnabled(enable);
	}

	@Override
	public void enablePlayer1Input(boolean enable) {
		p1InputEnabled = enable;
	}

	@Override
	public void enablePlayer2Input(boolean enable) {
		p2InputEnabled = enable;
	}

	public void setTimelimit(int player, int timelimit) {
		if (player == 0) {
			p1Timelimit = timelimit * 1000;
		}
		if (player == 1) {
			p2Timelimit = timelimit * 1000;
		}
	}

	public void setName(int player, String name) {
		if (player == 0) {
			p1Name = name;
		}
		if (player == 1) {
			p2Name = name;
		}
	}

	// ----- ActionListener Methods -----

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		if ("BarTimer".equals(command)) {
			timeBar.setValue(timeBar.getValue() + TIMER_DELAY);
			return;
		}

		if ("newGame".equals(command)) {
			NewGameDialog dialog = new NewGameDialog(frame);
			if (dialog.getSuccess() == true) {
				controller.newGame(dialog.getPlayer1Type(), dialog.getPlayer1Name(), dialog.getPlayer1TimeLimit(), dialog.getPlayer2Type(), dialog.getPlayer2Name(), dialog.getPlayer2TimeLimit(), dialog.getStartPlayer(), dialog.getIp());
			}
			return;
		}
		if ("endGame".equals(command)) {

			barTimer.stop();
			timeBar.setValue(0);
			resetButtons();
			timeBar.setString("");

			controller.endGame();
			return;
		}
		if ("quit".equals(command)) {
			System.exit(0);
		}

		if (command.matches("P1\\.[0-9]+")) {
			model.doMove(Player.PLAYER_1, Integer.parseInt(command.substring(3)));
			return;
		}
		if (command.matches("P2\\.[0-9]+")) {
			model.doMove(Player.PLAYER_2, Integer.parseInt(command.substring(3)));
			return;
		}

	}

	// ----- Observer Methods -----

	@Override
	public void update(Observable o, Object arg) {
		if (arg.getClass() == String.class) {
			barTimer.stop();
			timeBar.setValue(0);
			disableButtons();

			if (arg.equals("TIE")) {
				timeBar.setString(PLAYER_TIE);
				JOptionPane.showMessageDialog(frame, PLAYER_TIE);
				controller.endGame();
			}
			if (arg.equals("TIMEOUT")) {
				String tmp = timeBar.getString().substring(0, timeBar.getString().length() - PLAYER_TURN.length()) + PLAYER_TIMEOUT;
				timeBar.setString(tmp);
				JOptionPane.showMessageDialog(frame, tmp + ".");
				controller.endGame();
			}
			if (arg.toString().matches("P1WINS\\.[0-9]+")) {
				timeBar.setString(p1Name + PLAYER_WINS + arg.toString().substring(7));
				JOptionPane.showMessageDialog(frame, p1Name + PLAYER_WINS + arg.toString().substring(7) + ".");
				controller.endGame();
			}
			if (arg.toString().matches("P2WINS\\.[0-9]+")) {
				timeBar.setString(p2Name + PLAYER_WINS + arg.toString().substring(7));
				JOptionPane.showMessageDialog(frame, p2Name + PLAYER_WINS + arg.toString().substring(7) + ".");
				controller.endGame();
			}
			if (arg.equals("GAMEOVER")) {
				controller.endGame();
			}

		} else {
			Board board = (Board) arg;
			int[][] numPits = board.getPits();
			boolean[][] enabledPits = new boolean[numPits.length][numPits[0].length];

			if ((p1InputEnabled && (board.getCurrentPlayer() == 0)) || (p2InputEnabled && (board.getCurrentPlayer() == 1))) {
				for (int i = 0; i < board.getValidMoves().length; i++) {
					enabledPits[board.getCurrentPlayer()][i] = board.getValidMoves()[i];
				}
			}

			for (int i = 0; i < playerpits.length; i++) {
				for (int j = 0; j < playerpits[0].length; j++) {
					playerpits[i][j].setText(Integer.toString(numPits[i][j]));
					playerpits[i][j].setEnabled(enabledPits[i][j]);

					if (playerpits[i][j].getText() != "") {
						int tmp = Integer.parseInt(playerpits[i][j].getText());

						if (j == playerpits[0].length - 1) {
							if (tmp < 4) {
								playerpits[i][j].setDisabledIcon(imgK[tmp]);
							} else {
								playerpits[i][j].setDisabledIcon(imgK[4]);
							}
						} else {
							if (tmp < 4) {
								if (playerpits[i][j].isEnabled()) {
									playerpits[i][j].setIcon(imgE[tmp]);
								} else {
									playerpits[i][j].setDisabledIcon(imgD[tmp]);
								}
							} else {
								if (playerpits[i][j].isEnabled()) {
									playerpits[i][j].setIcon(imgE[4]);
								} else {
									playerpits[i][j].setDisabledIcon(imgD[4]);
								}
							}
						}
					}
				}
			}

			barTimer.stop();
			timeBar.setValue(0);

			if (!board.isGameOver()) {
				if (board.getCurrentPlayer() == 0) {
					timeBar.setMaximum(p1Timelimit);
				} else {
					timeBar.setMaximum(p2Timelimit);
				}

				barTimer.start();

				if (board.getCurrentPlayer() == 0) {
					timeBar.setString(p1Name + PLAYER_TURN);
				} else {
					timeBar.setString(p2Name + PLAYER_TURN);
				}
			}
		}

	}

	/**
	 * Resets all input buttons.
	 */
	private void resetButtons() {
		for (int i = 0; i < playerpits.length; i++) {
			for (int j = 0; j < playerpits[0].length; j++) {
				playerpits[i][j].setText("");
				playerpits[i][j].setEnabled(false);

				if (j == playerpits[0].length - 1) {
					playerpits[i][j].setIcon(imgK[0]);
					playerpits[i][j].setDisabledIcon(imgK[0]);
				} else {
					playerpits[i][j].setIcon(imgD[0]);
					playerpits[i][j].setDisabledIcon(imgD[0]);
				}
			}
		}
	}

	/**
	 * Disables all input buttons for players.
	 */
	private void disableButtons() {
		for (int i = 0; i < playerpits.length; i++) {
			for (int j = 0; j < playerpits[0].length; j++) {
				playerpits[i][j].setEnabled(false);
			}
		}
	}

	// ----- Getter & Setter -----

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void setModel(ModelInterface model) {
		this.model = model;
	}

}

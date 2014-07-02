package kalaha.view.windowGui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import kalaha.model.player.Player;

@SuppressWarnings("serial")
public class NewGameDialog extends JDialog implements ActionListener {

	private static final String title = "New Game";

	private static final Dimension DEFAULT_DISTANCE = new Dimension(10, 10);

	private static final int DEFAULT_TIMELIMIT = 10;

	private static final String DEFAULT_IP = "127.0.0.1";

	private static final int MAX_TIMELIMIT = 1000;

	private static final int STEP_TIMELIMIT = 1;

	private static final String DIALOG_OK = "ok";

	private static final String DIALOG_CANCEL = "chancel";

	private static final String DIALOG_UPDATE = "update";

	private static final String START_PLAYER1 = "Player1";

	private static final String START_PLAYER2 = "Player2";

	private static final String START_RANDOM = "Random";

	private boolean success = false;

	private Container contentPane;

	private JTextField player1NameField;

	private JComboBox player1TypeField;

	private DefaultComboBoxModel player1TypeModel;

	private DefaultComboBoxModel player1TypeLocalModel;

	private JTextField player2NameField;

	private JComboBox player2TypeField;

	private DefaultComboBoxModel player2TypeModel;

	private DefaultComboBoxModel player2TypeLocalModel;

	private JSpinner player1TimeField;

	private SpinnerNumberModel player1TimeFrame;

	private SpinnerNumberModel player2TimeFrame;

	private JSpinner player2TimeField;

	private JComboBox startPlayerField;

	private JTextField ipField;

	/**
	 * Creates and shows a new game window.
	 * @param parentComp The parent window.
	 */
	public NewGameDialog(Component parentComp) {

		super(JOptionPane.getFrameForComponent(parentComp), title, true);

		contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		createPlayerBoxes(contentPane);
		createOptionBox(contentPane);
		createButtonBox(contentPane);
		updateContent();

		pack();
		setLocationRelativeTo(parentComp);
		setResizable(false);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (DIALOG_OK.equals(e.getActionCommand())) {

			success = true;
			setVisible(false);

		} else if (DIALOG_CANCEL.equals(e.getActionCommand())) {

			success = false;
			setVisible(false);

		} else if (DIALOG_UPDATE.equals(e.getActionCommand())) {
			updateContent();

		}

	}
	
	/**
	 * 
	 * @return The type set for the first player.
	 */
	public Player.Type getPlayer1Type() {
		return (Player.Type) player1TypeField.getSelectedItem();
	}
	
	/**
	 * 
	 * @return The type set for the second player.
	 */
	public Player.Type getPlayer2Type() {
		return (Player.Type) player2TypeField.getSelectedItem();
	}
	
	/**
	 * 
	 * @return The name set for the first player.
	 */
	public String getPlayer1Name() {
		return player1NameField.getText();
	}
	
	/**
	 * 
	 * @return The name set for the second player.
	 */
	public String getPlayer2Name() {
		return player2NameField.getText();
	}
	
	/**
	 * 
	 * @return The timelimit set for the first player.
	 */
	public int getPlayer1TimeLimit() {
		return player1TimeFrame.getNumber().intValue();
	}

	/**
	 * 
	 * @return The timelimit set for the second player.
	 */
	public int getPlayer2TimeLimit() {
		return player2TimeFrame.getNumber().intValue();
	}

	/**
	 * 
	 * @return The adress for the server, if available.
	 */
	public String getIp() {
		if (ipField.isEnabled() == false) {
			return "";
		} else {
			return ipField.getText();
		}
	}

	/**
	 * 
	 * @return The player choosen to start the game.
	 */
	public int getStartPlayer() {
		if (startPlayerField.isEnabled() == false) {
			return 0;
		} else {
			if (startPlayerField.getSelectedItem() == START_PLAYER1) {
				return Player.PLAYER_1;
			} else if (startPlayerField.getSelectedItem() == START_PLAYER2) {
				return Player.PLAYER_2;
			} else {
				Random generator = new Random();
				return generator.nextInt(2);
			}
		}
	}

	public boolean getSuccess() {

		return success;

	}

	/**
	 * Creates the Box containing the options for both players.
	 * 
	 * @param cont
	 *            The container the box should be added to.
	 */
	private void createPlayerBoxes(Container cont) {

		Box topBox = Box.createHorizontalBox();
		topBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		{
			createPlayer1Box(topBox);
			cont.add(Box.createRigidArea(DEFAULT_DISTANCE));
			createPlayer2Box(topBox);
		}
		cont.add(topBox);
	}

	/**
	 * Creates the options to be set for the first player.
	 * 
	 * @param cont
	 *            The container the options should be added to.
	 */
	private void createPlayer1Box(Container cont) {

		Box tempBox;
		JLabel tempLabel;

		Box player1Box = Box.createVerticalBox();
		player1Box.setBorder(BorderFactory.createTitledBorder(" Player 1 "));
		{
			player1Box.add(Box.createRigidArea(DEFAULT_DISTANCE));
			tempBox = Box.createHorizontalBox();
			{
				tempLabel = new JLabel("Spielername: ");
				tempBox.add(tempLabel);
				tempBox.add(Box.createRigidArea(DEFAULT_DISTANCE));
				player1NameField = new JTextField("Spieler 1");
				player1NameField.setMaximumSize(new Dimension(100, 20));
				tempBox.add(player1NameField);
				player1Box.add(tempBox);
			}
			tempBox = Box.createHorizontalBox();
			tempBox.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
			{

				tempLabel = new JLabel("Player Type: ");
				tempBox.add(tempLabel);
				tempBox.add(Box.createHorizontalGlue());
				player1TypeModel = new DefaultComboBoxModel(Player.getPlayerTypes());
				player1TypeLocalModel = new DefaultComboBoxModel(Player.getLocalPlayerTypes());
				player1TypeField = new JComboBox(player1TypeModel);
				// player1TypeField.setMaximumSize(new Dimension(120,30));
				player1TypeField.setPreferredSize(new Dimension(120, 30));
				player1TypeField.setActionCommand(DIALOG_UPDATE);
				player1TypeField.addActionListener(this);
				tempBox.add(player1TypeField);
				player1Box.add(tempBox);

			}

			tempBox = Box.createHorizontalBox();
			tempBox.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 25));
			{

				tempLabel = new JLabel("Time limit (sec): ");
				tempBox.add(tempLabel);
				tempBox.add(Box.createHorizontalGlue());
				player1TimeFrame = new SpinnerNumberModel(DEFAULT_TIMELIMIT, 0, MAX_TIMELIMIT, STEP_TIMELIMIT);
				player1TimeField = new JSpinner(player1TimeFrame);
				player1TimeField.setMaximumSize(new Dimension(50, 30));
				tempBox.add(player1TimeField);
				player1Box.add(tempBox);

			}
		}

		cont.add(player1Box);

	}

	/**
	 * Creates the options to be set for the second player.
	 * 
	 * @param cont
	 *            The container the options should be added to.
	 */
	private void createPlayer2Box(Container cont) {

		Box tempBox;
		JLabel tempLabel;

		Box player2Box = Box.createVerticalBox();
		player2Box.setBorder(BorderFactory.createTitledBorder(" Player 2 "));
		{
			player2Box.add(Box.createRigidArea(DEFAULT_DISTANCE));
			tempBox = Box.createHorizontalBox();
			{
				tempLabel = new JLabel("Spielername: ");
				tempBox.add(tempLabel);
				tempBox.add(Box.createRigidArea(DEFAULT_DISTANCE));
				player2NameField = new JTextField("Spieler 2");
				player2NameField.setMaximumSize(new Dimension(100, 20));
				tempBox.add(player2NameField);
				player2Box.add(tempBox);
			}
			tempBox = Box.createHorizontalBox();
			tempBox.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
			{

				tempLabel = new JLabel("Player Type: ");
				tempBox.add(tempLabel);
				tempBox.add(Box.createHorizontalGlue());
				player2TypeModel = new DefaultComboBoxModel(Player.getPlayerTypes());
				player2TypeLocalModel = new DefaultComboBoxModel(Player.getLocalPlayerTypes());
				player2TypeField = new JComboBox(player2TypeModel);
				// player2TypeField.setMaximumSize(new Dimension(120,30));
				player2TypeField.setPreferredSize(new Dimension(120, 30));
				player2TypeField.setActionCommand(DIALOG_UPDATE);
				player2TypeField.addActionListener(this);
				tempBox.add(player2TypeField);
				player2Box.add(tempBox);

			}

			tempBox = Box.createHorizontalBox();
			tempBox.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 25));
			{

				tempLabel = new JLabel("Time limit (sec): ");
				tempBox.add(tempLabel);
				tempBox.add(Box.createHorizontalGlue());
				player2TimeFrame = new SpinnerNumberModel(DEFAULT_TIMELIMIT, 0, MAX_TIMELIMIT, STEP_TIMELIMIT);
				player2TimeField = new JSpinner(player2TimeFrame);
				player2TimeField.setMaximumSize(new Dimension(50, 30));
				tempBox.add(player2TimeField);
				player2Box.add(tempBox);

			}
		}

		cont.add(player2Box);

	}

	/**
	 * Creates the Box containing the options for the game.
	 * 
	 * @param cont
	 *            The container the box should be added to.
	 */
	private void createOptionBox(Container cont) {

		Box tempBox, innerTempBox;
		JLabel tempLabel;

		Box optionBox = Box.createVerticalBox();
		optionBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		tempBox = Box.createHorizontalBox();
		tempBox.setBorder(BorderFactory.createTitledBorder(" Options "));

		{

			innerTempBox = Box.createHorizontalBox();
			innerTempBox.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

			tempLabel = new JLabel("Starting Player: ");
			innerTempBox.add(tempLabel);
			innerTempBox.add(Box.createRigidArea(DEFAULT_DISTANCE));
			String selection[] = { START_PLAYER1, START_PLAYER2, START_RANDOM };
			startPlayerField = new JComboBox(selection);
			startPlayerField.setMaximumSize(new Dimension(120, 30));
			innerTempBox.add(startPlayerField);

			innerTempBox.add(Box.createRigidArea(new Dimension(40, 0)));

			tempLabel = new JLabel("Ip / Adresse: ");
			innerTempBox.add(tempLabel);
			innerTempBox.add(Box.createRigidArea(DEFAULT_DISTANCE));
			ipField = new JTextField(DEFAULT_IP);
			ipField.setMaximumSize(new Dimension(160, 20));
			innerTempBox.add(ipField);

			tempBox.add(innerTempBox);
			optionBox.add(tempBox);
		}

		cont.add(optionBox);
	}

	/**
	 * Creates the Play and Cancel buttons.
	 * 
	 * @param cont
	 *            The container the Buttons should be added to.
	 */
	private void createButtonBox(Container cont) {

		Box buttonBox = Box.createHorizontalBox();
		buttonBox.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
		{
			JButton okButton = new JButton(" Play! ");
			okButton.setSize(200, 30);
			okButton.setActionCommand(DIALOG_OK);
			okButton.addActionListener(this);
			getRootPane().setDefaultButton(okButton);
			buttonBox.add(okButton);

			buttonBox.add(Box.createHorizontalGlue());

			JButton cancelButton = new JButton("Cancel");
			okButton.setSize(200, 30);
			cancelButton.setActionCommand(DIALOG_CANCEL);
			cancelButton.addActionListener(this);
			buttonBox.add(cancelButton);
		}

		cont.add(buttonBox);

	}

	/**
	 * Updates the window.
	 */
	private void updateContent() {

		Player.Type p1Type = (Player.Type) player1TypeField.getSelectedItem();
		Player.Type p2Type = (Player.Type) player2TypeField.getSelectedItem();

		if (p1Type.getNameChoosable() == false) {
			this.player1NameField.setEnabled(false);
			this.player1NameField.setText(p1Type.getDefaultName());

		} else {
			this.player1NameField.setEnabled(true);
			this.player1NameField.setText(p1Type.getDefaultName());
		}

		if (p2Type.getNameChoosable() == false) {
			this.player2NameField.setEnabled(false);
			this.player2NameField.setText(p2Type.getDefaultName());

		} else {
			this.player2NameField.setEnabled(true);
			this.player2NameField.setText(p2Type.getDefaultName());
		}

		if (p1Type.getTimeChoosable() == false) {
			player1TimeFrame.setValue(p1Type.getDefaultTime());
			player1TimeField.setEnabled(false);

		} else {
			player1TimeFrame.setValue(p1Type.getDefaultTime());
			player1TimeField.setEnabled(true);
		}

		if (p2Type.getTimeChoosable() == false) {
			player2TimeFrame.setValue(p2Type.getDefaultTime());
			player2TimeField.setEnabled(false);

		} else {
			player2TimeFrame.setValue(p2Type.getDefaultTime());
			player2TimeField.setEnabled(true);
		}

		ipField.setEnabled(false);
		if (p1Type.getIsNetPlayer() == true) {
			ipField.setEnabled(true);
			player2TypeField.setModel(player2TypeLocalModel);
		} else {
			player2TypeField.setModel(player2TypeModel);
		}

		if (p2Type.getIsNetPlayer() == true) {
			ipField.setEnabled(true);
			player1TypeField.setModel(player1TypeLocalModel);
		} else {
			player1TypeField.setModel(player1TypeModel);
		}

		if (p1Type.getStartChoosable() == false || p2Type.getStartChoosable() == false) {
			startPlayerField.setEnabled(false);
		} else {
			startPlayerField.setEnabled(true);
		}

		// contentPane.validate();
		contentPane.repaint();
	}
}

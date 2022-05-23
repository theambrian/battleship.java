import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import  GameElements.Ships.*;

public class GameWindow extends JFrame implements ActionListener {

	private JPanel homePanel, transPanel, setupPanel, playPanel, gameWonPanel; //scene panels

	private int currentPlayer = 1; //-1 for player 2, 1 for player 1
	private boolean gameWon = false;
	private boolean shotFired = false;
	private Game player1Game, player2Game;

	public GameWindow(){
		player1Game = new Game();
		player2Game = new Game();

		//leave for testing purposes
		player1Game.addShip(new Cruiser(1, 3, "right"));
		player1Game.addShip(new Cruiser(1, 1, "down"));
		player1Game.addShip(new Destroyer(4, 8, "left"));
		player1Game.addShip(new Submarine(0, 0, "right"));
		player1Game.addShip(new Carrier(5, 2, "right"));

		player2Game.addShip(new Carrier(0, 0, "right"));
		player2Game.setHealth(1);
		//delete once setupPanel is implemented


		guiSetup();
	}


	public void guiSetup() {
		//basic initializations
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(150, 100);
		this.setPreferredSize(new Dimension(400, 525));
		this.setResizable(false);
		this.setLayout(new GridLayout(1,1));
		this.setTitle("Battleship");
		this.setIconImage(new ImageIcon("images/icon.png").getImage());

		playPanel = makePlayPanel();
		transPanel = makeTransPanel();
		homePanel = makeHomePanel();

		this.setContentPane(homePanel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event){
		String e = event.getActionCommand();

		switch(e) {
			case "fire" -> {
				Game currentGame = (currentPlayer == 1) ? player1Game : player2Game;
				Game enemyGame = (currentPlayer == 1) ? player2Game : player1Game;
				int row = Integer.parseInt(coordinateTextField.getText().split(", ", 2)[1]);
				int col = Integer.parseInt(coordinateTextField.getText().split(", ", 2)[0]);

				currentGame.fireUpon(row, col, enemyGame);
				enemyGame.takeFire(row, col);

				if(enemyGame.getHealth() == 0) gameWon = true;

				if(gameWon){
					gameWonPanel = makeGameWonPanel();
					this.setContentPane(gameWonPanel);
					this.pack();
					break;
				};

				shotFired = true;
				playPanel = makePlayPanel();
				this.setContentPane(playPanel);
				this.pack();
			}
			case "end turn" -> {
				this.setContentPane(transPanel);
				this.pack();
			}
			case "end transition" -> {
				currentPlayer *= -1;
				shotFired = false;

				playPanel = makePlayPanel();
				this.setContentPane(playPanel);
				this.pack();
			}
			case "reset" -> {
				this.reset();
			}
			case "play" -> {
				this.setContentPane(playPanel);
				this.pack();
			}
		}
	}

	JTextField coordinateTextField;
	JButton coordinateButton, endTurnButton;
	public JPanel makePlayPanel(){

		JPanel playPanel = new JPanel(new GridBagLayout());
		playPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagConstraints cons = new GridBagConstraints();
		cons.anchor = GridBagConstraints.LINE_START;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weighty = 0.5;
		cons.weightx = 0.5;
		cons.insets = new Insets(5,5,5,5);
		cons.ipady = 5;

		Game currentGame = (currentPlayer == 1) ? player1Game : player2Game;


		//make map
		{
			JPanel myMapPanel = new JPanel(new GridLayout(10, 10));
			JLabel[][] myMap = new JLabel[10][10];
			char[][] myMapGame = currentGame.getMyMap();
			JLabel myMapLabel = new JLabel("Home:");

			JPanel enemyMapPanel = new JPanel(new GridLayout(10, 10));
			JLabel[][] enemyMap = new JLabel[10][10];
			char[][] enemyMapGame = currentGame.getEnemyMap();
			JLabel enemyMapLabel = new JLabel("Enemy:");

			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					myMap[row][col] = new JLabel(String.valueOf(myMapGame[row][col]));
					myMap[row][col] = GUITools.setMapColors(myMap[row][col]);
					myMapPanel.add(myMap[row][col]);

					enemyMap[row][col] = new JLabel(String.valueOf(enemyMapGame[row][col]));
					enemyMap[row][col] = GUITools.setMapColors(enemyMap[row][col]);
					enemyMapPanel.add(enemyMap[row][col]);

				}
			}
			cons.gridx = 0; cons.gridy = 0;
			playPanel.add(enemyMapLabel, cons);
			cons.gridx = 0; cons.gridy = 1;
			playPanel.add(enemyMapPanel, cons);

			cons.gridx = 0; cons.gridy = 2;
			playPanel.add(myMapLabel, cons);
			cons.gridx = 0; cons.gridy = 3;
			playPanel.add(myMapPanel, cons);
		}

		//make buttons and text input
		{
			JPanel inputPanel = new JPanel();
			if(!shotFired) {
				coordinateTextField = new JTextField();
				coordinateTextField.setPreferredSize(new Dimension(125, 20));
				coordinateTextField.requestFocusInWindow();
				coordinateTextField.setActionCommand("fire");
				coordinateTextField.addActionListener(this);
				inputPanel.add(coordinateTextField);

				coordinateButton = new JButton("Fire");
				coordinateButton.setPreferredSize(new Dimension(80, 20));
				coordinateButton.setActionCommand("fire");
				coordinateButton.addActionListener(this);
				inputPanel.add(coordinateButton);
			}

			endTurnButton = new JButton("End Turn");
			endTurnButton.setPreferredSize(new Dimension(100, 20));
			endTurnButton.setActionCommand("end turn");
			endTurnButton.addActionListener(this);
			inputPanel.add(endTurnButton);

			cons.gridx = 0; cons.gridy = 5;
			playPanel.add(inputPanel, cons);
		}


		return playPanel;
	}

	JButton transNextButton;
	public JPanel makeTransPanel(){
		JPanel transPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;

		JLabel transLabel = new JLabel("Transition");
		transLabel.setFont(new Font("Sans-Serif", Font.BOLD, 28));
		cons.gridx = 0; cons.gridy = 0;
		transPanel.add(transLabel, cons);

		transNextButton = new JButton("Next");
		transNextButton.setPreferredSize(new Dimension(125, 20));
		transNextButton.setActionCommand("end transition");
		transNextButton.addActionListener(this);
		cons.gridx = 0; cons.gridy = 1;
		transPanel.add(transNextButton, cons);

		return transPanel;
	}

	JButton gameWonHomeButton;
	public JPanel makeGameWonPanel(){
		JPanel gameWonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;

		JLabel gameWonLabel = new JLabel("Congratulations Player " + ((currentPlayer == 1) ? 1 : 2) + "!");
		gameWonLabel.setFont(new Font("Sans-Serif", Font.BOLD, 28));
		cons.gridx = 0; cons.gridy = 0;
		gameWonPanel.add(gameWonLabel, cons);

		gameWonHomeButton = new JButton("Home");
		gameWonHomeButton.setPreferredSize(new Dimension(125, 20));
		gameWonHomeButton.setActionCommand("reset");
		gameWonHomeButton.addActionListener(this);
		cons.gridx = 0; cons.gridy = 1;
		gameWonPanel.add(gameWonHomeButton, cons);

		return gameWonPanel;
	}

	public void reset(){
		player1Game = new Game();
		player2Game = new Game();
		this.setContentPane(homePanel);
		this.pack();
	}

	JButton homePlayButton;
	public JPanel makeHomePanel(){
		JPanel homePanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;

		JLabel homeLabel = new JLabel("Battleship");
		homeLabel.setFont(new Font("Sans-Serif", Font.BOLD, 28));
		cons.gridx = 0; cons.gridy = 0;
		homePanel.add(homeLabel, cons);

		homePlayButton = new JButton("Play");
		homePlayButton.setPreferredSize(new Dimension(125, 20));
		homePlayButton.setActionCommand("play");
		homePlayButton.addActionListener(this);
		cons.insets = new Insets(5, 2, 2, 2);
		cons.gridx = 0; cons.gridy = 1;
		homePanel.add(homePlayButton, cons);

		return homePanel;
	}

	public static void main(String[] args) {
		new GameWindow();
	}
}

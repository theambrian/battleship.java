import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Game.java;
import GameElements.*;
import GameElements.Ships.*;


public class GameWindow extends JFrame implements ActionListener {

	private JPanel homePanel, transPanel, setupPanel, playPanel, gameWonPanel; //scene panels

	//game fields
	private int currentPlayer = 1; //-1 for player 2, 1 for player 1
	private boolean gameWon = false;
	private boolean shotFired = false;
	private Game player1Game, player2Game;

	public GameWindow(){
		player1Game = new Game();
		player2Game = new Game();

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
		this.setBackground(Color.WHITE);

		playPanel = makePlayPanel();
		transPanel = makeTransPanel();
		homePanel = makeHomePanel();
		setupPanel = makeSetupPanel();
	}

	@Override
	public void actionPerformed(ActionEvent event){
		String e = event.getActionCommand();

		//play panel
		switch(e) {
			case "fire" -> {
				Game currentGame = (currentPlayer == 1) ? player1Game : player2Game;
				Game enemyGame = (currentPlayer == 1) ? player2Game : player1Game;

				String[] userIn = coordinateTextField.getText().split(", ", 2);

				int row = Integer.parseInt(userIn[1]);
				int col = Integer.parseInt(userIn[0]);

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
			case "end battle turn" -> {
				this.setContentPane(transPanel);
				this.pack();
			}
			case "reset" -> {
				this.reset();
			}

		}

		//transition screen
		if ("end transition".equals(e)) {
			currentPlayer *= -1;
			shotFired = false;

			playPanel = makePlayPanel();
			this.setContentPane(playPanel);
			this.pack();
		}

		//home panel
		if ("play".equals(e)) {
			this.setContentPane(setupPanel);
			this.pack();
		}

		//setup panel
		switch(e){
			case "add ship" -> {
				Game currentGame = (currentPlayer == 1) ? player1Game : player2Game;

				String[] userIn = setupCoordinateField.getText().split(", ", 4);
				
				String orientation = userIn[3];
				int row = Integer.parseInt(userIn[2]);
				int col = Integer.parseInt(userIn[1]);
				String name = userIn[0];


				currentGame.addShip(name, row, col, orientation);

				setupPanel = makeSetupPanel();
				this.setContentPane(setupPanel);
				this.pack();
			}
			case "end setup turn" -> {
				Game enemyGame = (currentPlayer == 1) ? player2Game : player1Game;

				if(enemyGame.getInventory().size() > 0){
					currentPlayer *= -1;
					setupPanel = makeSetupPanel();
					this.setContentPane(setupPanel);
					this.pack();
				} else {
					playPanel = makePlayPanel();
					this.setContentPane(playPanel);
					this.pack();
				}
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
			JLabel myMapLabel = new JLabel("Player " + ((currentPlayer == 1) ? 1 : 2) + " Home:");
			JPanel enemyMapPanel = new JPanel(new GridLayout(10, 10));
			JLabel[][] enemyMap = new JLabel[10][10];
			char[][] enemyMapGame = currentGame.getEnemyMap();
			JLabel enemyMapLabel = new JLabel("Enemy:");

			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					myMap[row][col] = new JLabel(String.valueOf(myMapGame[row][col]));
					myMap[row][col] = setMapColors(myMap[row][col]);
					myMapPanel.add(myMap[row][col]);

					enemyMap[row][col] = new JLabel(String.valueOf(enemyMapGame[row][col]));
					enemyMap[row][col] = setMapColors(enemyMap[row][col]);
					enemyMapPanel.add(enemyMap[row][col]);

				}
			}

			enemyMapLabel.setFont(new Font("Sans-Serif",Font.BOLD, 14));
			cons.gridx = 0; cons.gridy = 0;
			playPanel.add(enemyMapLabel, cons);
			enemyMapPanel.setBackground(Color.DARK_GRAY);
			cons.gridx = 0; cons.gridy = 1;
			playPanel.add(enemyMapPanel, cons);

			myMapLabel.setFont(new Font("Sans-Serif",Font.BOLD, 14));
			cons.gridx = 0; cons.gridy = 2;
			playPanel.add(myMapLabel, cons);
			myMapPanel.setBackground(Color.DARK_GRAY);
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
			endTurnButton.setActionCommand("end battle turn");
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

	JTextField setupCoordinateField;
	JButton endSetupButton;
	public JPanel makeSetupPanel(){
		JPanel setupPanel = new JPanel(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.anchor = GridBagConstraints.LINE_START;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weighty = 0.5;
		cons.weightx = 0.5;
		cons.insets = new Insets(5,5,5,5);
		cons.ipady = 5;

		Game currentGame = (currentPlayer == 1) ? player1Game : player2Game;

		JPanel myMapPanel = new JPanel(new GridLayout(10, 10));
		JLabel[][] myMap = new JLabel[10][10];
		char[][] myMapGame = currentGame.getMyMap();
		JLabel myMapLabel = new JLabel("Player " + ((currentPlayer == 1) ? 1 : 2) + " Home:");
		for(int row = 0; row < 10; row++){
			for(int col = 0; col < 10; col++){
				myMap[row][col] = new JLabel(String.valueOf(myMapGame[row][col]));
				myMap[row][col] = setMapColors(myMap[row][col]);
				myMapPanel.add(myMap[row][col]);
			}
		}

		myMapLabel.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		cons.gridx = 0; cons.gridy = 0; cons.anchor = GridBagConstraints.BELOW_BASELINE;
		setupPanel.add(myMapLabel, cons);
		myMapPanel.setBackground(Color.DARK_GRAY);
		cons.gridx = 0; cons.gridy = 1;
		setupPanel.add(myMapPanel, cons);


		ArrayList<String> currentPlayerInv = currentGame.getInventory();
		JPanel inventoryPanel = new JPanel(new GridLayout(0, 1));
		for(String ship : currentPlayerInv){
			inventoryPanel.add(new JLabel("* " + ship));
		}
		cons.gridx = 1; cons.gridy = 1;
		setupPanel.add(inventoryPanel, cons);

		JPanel inputPanel = new JPanel();
		if(currentPlayerInv.size() > 0){
			setupCoordinateField = new JTextField();
			setupCoordinateField.requestFocusInWindow();
			setupCoordinateField.setPreferredSize(new Dimension(150, 28));
			setupCoordinateField.setActionCommand("add ship");
			setupCoordinateField.addActionListener(this);
			inputPanel.add(setupCoordinateField);
		}

		endSetupButton = new JButton("end setup");
		endSetupButton.setActionCommand("end setup turn");
		endSetupButton.addActionListener(this);
		inputPanel.add(endSetupButton);

		cons.gridx = 0; cons.gridy = 2;
		setupPanel.add(inputPanel, cons);

		return setupPanel;
	}
	
	public void reset(){
		player1Game = new Game();
		player2Game = new Game();
		this.setContentPane(homePanel);
		this.pack();
	}
	
	public JLabel setMapColors(JLabel original){
		final char water = '~';
		final char hit = 'x';
		final char miss = 'o';
		final char ship = '^';

		original.setFont(new Font("Sans-Serif", Font.BOLD, 14));

		char text = original.getText().charAt(0);
		switch (text) {
			case water -> {
				original.setForeground(new Color(137, 207, 240));
				return original;
			}
			case hit -> {
				original.setForeground(Color.RED);
				return original;
			}
			case ship -> {
				original.setForeground(Color.LIGHT_GRAY);
				return original;
			}
			case miss -> {
				original.setForeground(Color.WHITE);
			}
		}
		return original;
	}

	public static void main(String[] args) { new GameWindow(); }
	
}

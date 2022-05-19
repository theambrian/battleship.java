import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import GameElements.*;
import  GameElements.Ships.*;

public class GameWindow extends JFrame implements ActionListener {

	private JPanel homePanel, transPanel, setupPanel, playPanel;

	private int currentPlayer = 1;
	private Game player1Game, player2Game;

	public GameWindow(){
		//basic initializations
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(150, 100);
		this.setPreferredSize(new Dimension(400, 500));
		this.setMinimumSize(new Dimension(100, 125));
		this.setResizable(false);

		player1Game = new Game();
		player1Game.addShip(new Cruiser(1, 3, "right"));
		player1Game.addShip(new Cruiser(1, 1, "down"));
		player1Game.addShip(new Destroyer(4, 8, "left"));
		player1Game.addShip(new Submarine(0, 0, "right"));
		player1Game.addShip(new Carrier(5, 2, "right"));
		player2Game = new Game();

		playPanel = makePlayPanel();
		this.add(playPanel);
		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event){
		String e = event.getActionCommand();

		switch(e) {
			case "shot coordinate" -> {
				Game currentGame = (currentPlayer == 1) ? player1Game : player2Game;
				Game enemyGame = (currentPlayer == 1) ? player2Game : player1Game;
				String[] locations = coordinateTextField.getText().split(", ", 2);
				currentGame.fireUpon(Integer.getInteger(locations[0]), Integer.getInteger(locations[1]),enemyGame);
				enemyGame.takeFire(Integer.getInteger(locations[0]), Integer.getInteger(locations[1]));
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

		Game currentGame = (currentPlayer == 1) ? player1Game : player2Game;


		//make map
		{
			JPanel myMapPanel = new JPanel(new GridLayout(10, 10));
			JLabel[][] myMap = new JLabel[10][10];
			char[][] myMapGame = currentGame.getMyMap();

			JPanel enemyMapPanel = new JPanel(new GridLayout(10, 10));
			JLabel[][] enemyMap = new JLabel[10][10];
			char[][] enemyMapGame = currentGame.getEnemyMap();

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

			cons.gridx = 0;
			cons.gridy = 0;
			playPanel.add(enemyMapPanel, cons);
			cons.gridx = 0;
			cons.gridy = 1;
			playPanel.add(myMapPanel, cons);
		}

		//make buttons and text input
		{
			JPanel inputPanel = new JPanel();

			coordinateTextField = new JTextField("Enter target:");
			coordinateTextField.setSize(new Dimension(60, 10));
			coordinateTextField.setActionCommand("shot coordinate");
			inputPanel.add(coordinateTextField);
			
			coordinateButton = new JButton("Shoot");
			coordinateButton.setSize(new Dimension(25,10));
			coordinateButton.setActionCommand("shot coordinate");
			inputPanel.add(coordinateButton);

			endTurnButton = new JButton("End Turn");
			endTurnButton.setSize(new Dimension(10, 10));
			endTurnButton.setActionCommand("end battle turn");
			inputPanel.add(endTurnButton);

			cons.gridx = 0; cons.gridy = 2;
			playPanel.add(inputPanel, cons);
		}


		return playPanel;
	}


	public static void main(String[] args) {
		new GameWindow();
	}
}

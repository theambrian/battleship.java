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
	private JLabel[][] map;

	private int currentPlayer = 1;
	private Game player1Game, player2Game;

	public GameWindow(){
		//basic initializations
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(150, 100);
		this.setPreferredSize(new Dimension(400, 500);
		this.setMinimumSize(new Dimension(100, 125));

		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event){

	}

	public void makePlayPanel(){
		JPanel playPanel = new JPanel();
		playPanel.setLayout(new GridBagLayout());
		playPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints cons = new GridBagConstraints();
		cons.anchor = GridBagConstraints.LINE_START;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weighty = 0.5;
		cons.weightx = 0.5;
		cons.insets = new Insets(2, 3, 2, 3);


		//make map
		{
			JPanel mapPanel = new JPanel();
			mapPanel.setLayout(new GridLayout(10, 10));
			JLabel[][] map = new JLabel[10][10];
			char[][] gameMap = (currentPlayer == 1) ? player1Game.getMap() : player2Game.getMap();
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					map[row][col] = new JLabel(String.valueOf(gameMap[row][col]));
					mapPanel.add(map[row][col]);
				}
			}

			cons.gridx = 0; cons.gridy = 0;
			playPanel.add(mapPanel, cons);
		}
	}


	public static void main(String[] args) {
		new GameWindow();
	}
}

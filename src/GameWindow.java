import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameWindow extends JFrame implements ActionListener {

	private JPanel homePanel, transPanel, setupPanel, playPanel;
	private int currentPlayer = 1;

	public GameWindow(){
		//basic initializations
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(150, 100);
		this.setPreferredSize(new Dimension(750, 450));
		this.setMinimumSize(new Dimension(100, 100));


	}

	public void actionPerformed(ActionEvent event){

	}

	public static JPanel makePlayPanel(){
		JPanel output = new JPanel();
		JLabel[] map =

		return output;
	}


	public static void main(String[] args) {
		new GameWindow();
	}
}

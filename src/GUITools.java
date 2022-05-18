import javax.swing.*;
import java.awt.*;

public class GUITools {
	public static JLabel setMapColors(JLabel original){
		final char water = '~';
		final char hit = 'x';
		final char miss = 'o';
		final char ship = '^';


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
				original.setForeground(Color.BLACK);
				original.setFont(new Font("Sans-Serif", Font.BOLD, 14));
				return original;
			}
			default -> {
				return original;
			}
		}

	}
}

import GameElements.*;
import GameElements.Ships.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
	char[][] map;
	char water = '~';
	char hit = 'x';
	char miss = 'o';
	char ship = '^';

	private ArrayList<String> inventory;


	public Game() {
		this.map = new char[10][10];
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				this.map[row][col] = water;
			}
		}

		this.inventory = new ArrayList<>(Arrays.asList("Destroyer", "Cruiser", "Submarine", "Battleship", "Carrier"));
	}

	public char[][] getMap(){return this.map;};

	public boolean addShip(Ship newShip){
		String orientation = newShip.getOrientation();
		int[] start = newShip.getStart();
		int length = newShip.length();
		String name = newShip.getName();

		switch (orientation) {
			case "up" -> {
				if (length - start[1] < 0) return false;
				for (int i = 0; i < length; i++) {
					this.map[start[0] - i][start[1]] = '^';
				}
				this.inventory.remove(name);
			}
			case "down" -> {
				if (length + start[1] > 9) return false;
				for (int i = 0; i < length; i++) {
					this.map[start[0] + i][start[1]] = '^';
				}
				this.inventory.remove(name);
			}
			case "left" -> {
				if (length - start[0] > 0) return false;
				for (int i = 0; i < length; i++) {
					this.map[start[0]][start[1] - i] = '^';
				}
				this.inventory.remove(name);
			}
			case "right" -> {
				if (length + start[0] > 9) return false;
				for (int i = 0; i < length; i++) {
					this.map[start[0]][start[1] + i] = '^';
				}
				this.inventory.remove(name);
			}
			default -> {
			}
		}

		return true;
	}
}

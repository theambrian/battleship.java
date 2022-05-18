import GameElements.*;
import GameElements.Ships.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
	char[][] myMap; //player's ships. bottom of battleship board
	char[][] enemyMap; //what the player knows about enemy's ship. top of battleship board.
	char water = '~';
	char hit = 'x';
	char miss = 'o';
	char ship = '^';
	int health; //total health of player's ships.

	public Game() {
		this.myMap = new char[10][10];
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				this.myMap[row][col] = water;
			}
		}

		this.enemyMap = new char[10][10];
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				this.enemyMap[row][col] = water;
			}
		}

		this.health = 17;
	}

	public char[][] getMyMap(){return this.myMap;}
	public char[][] getEnemyMap(){return this.enemyMap;}
	public int getHealth(){return this.health;}
	public void setMyMap(int row, int col, char newStatus){
		this.myMap[row][col] = newStatus;
	}
	public void setEnemyMap(int row, int col, char newStatus){this.enemyMap[row][col] = newStatus;}
	public void takeHealth(){this.health--;}

	public boolean addShip(Ship newShip){
		String orientation = newShip.getOrientation();
		int[] start = newShip.getStart();
		int length = newShip.length();
		String name = newShip.getName();

		switch (orientation) {
			case "up" -> {
				if (length - start[0] < 0) return false;
				for (int i = 0; i < length; i++) {
					this.myMap[start[0] - i][start[1]] = ship;
				}
			}
			case "down" -> {
				if (length + start[0] > 9) return false;
				for (int i = 0; i < length; i++) {
					this.myMap[start[0] + i][start[1]] = ship;
				}
			}
			case "left" -> {
				if (length - start[1] > 0) return false;
				for (int i = 0; i < length; i++) {
					this.myMap[start[0]][start[1] - i] = ship;
				}
			}
			case "right" -> {
				if (length + start[1] > 9) return false;
				for (int i = 0; i < length; i++) {
					this.myMap[start[0]][start[1] + i] = ship;
				}
			}
			default -> {
			}
		}
		return true;
	}

	//fire on another player. both player and enemy will change their maps in response
	public void fire(int row, int col, Game enemyGame){
		
		if(enemyGame.getMyMap()[row][col] == ship){
			enemyGame.setMyMap(row, col, hit);
			enemyGame.takeHealth();
			this.setEnemyMap(row, col, hit);
		} else {
			enemyGame.setMyMap(row, col, miss);
			this.setEnemyMap(row, col, miss);
		}
	}



}

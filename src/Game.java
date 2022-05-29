import GameElements.*; import GameElements.Ships.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Game {
	private char[][] myMap; //player's ships. bottom of battleship board
	private char[][] enemyMap; //what the player knows about enemy's ship. top of battleship board.
	private char water = '~';
	private char hit = 'x';
	private char miss = 'o';
	private char ship = '^';
	private int health; //total health of player's ships.
	private ArrayList<String> inventory = new ArrayList<>(List.of("destroyer", "cruiser", "submarine", "battleship", "carrier"));

	public Game() {
		myMap = new char[10][10];
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				myMap[row][col] = water;
			}
		}

		enemyMap = new char[10][10];
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				enemyMap[row][col] = water;
			}
		}

		health = 17;
	}

	public char[][] getMyMap(){return myMap;}
	public char[][] getEnemyMap(){return enemyMap;}
	public int getHealth(){return health;}
	public ArrayList<String> getInventory(){return inventory;}
	public void setHealth(int n){health = n;}

	public boolean addShip(String name, int row, int col, String orientation){

		Ship newShip = null;
		name = name.toLowerCase(Locale.ROOT);

		switch(name){
			case "destroyer" -> {
				newShip = new Destroyer(row, col, orientation);
			}
			case "cruiser" ->{
				newShip = new Cruiser(row, col, orientation);
			}
			case "submarine" ->{
				newShip = new Submarine(row, col, orientation);
			}
			case "battleship" ->{
				newShip = new Battleship(row, col, orientation);
			}
			case "carrier" ->{
				newShip = new Carrier(row, col, orientation);
			}
		}

		int length = (newShip != null) ? newShip.length() : 0;

		if(newShip != null && newShip.isPlaceable()){
			switch (orientation) {
				case "up" -> {
					for (int i = 0; i < length; i++) {
						myMap[row - i][col] = ship;
					}
				}
				case "down" -> {
					for (int i = 0; i < length; i++) {
						myMap[row + i][col] = ship;
					}
				}
				case "left" -> {
					for (int i = 0; i < length; i++) {
						myMap[row][col - i] = ship;
					}
				}
				case "right" -> {
					for (int i = 0; i < length; i++) {
						myMap[row][col + i] = ship;
					}
				}
			}
			inventory.remove(name);
			return true;
		}

		return false;
	}

	//fire on another player. player will change their view of enemy map in response
	public void fireUpon(int row, int col, Game enemyGame){
		enemyMap[row][col] = (enemyGame.getMyMap()[row][col] == ship) ? hit : miss;
	}
	
	public void takeFire(int row, int col){
		if(getMyMap()[row][col] == ship){
			myMap[row][col] = hit;
			health--;
		} else {
			myMap[row][col] = miss;
		}
	}



}

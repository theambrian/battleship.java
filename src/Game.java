import GameElements.*; 
import GameElements.Ships.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Game {

	//map info
	private final char water = '~';
	private final char hit = 'x';
	private final char miss = 'o';
	private final char ship = '^';
	private final char[][] myMap;
	private final char[][] enemyMap;

	//game info
	private int health;
	private ArrayList<String> inventory = new ArrayList<>
			(List.of("destroyer", "cruiser", "submarine", "battleship", "carrier"));
	private ArrayList


	/**
	 * Default and only constructor makes the game.
	 * Instanstiates a 10ux10u game board for enemies and players.
	 * Instantiates health.
	 * 
	 * @return new Game object
	 */
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

	/**
	 * Adds a ship to the player's own game board based on given
	 * coordinates, name, and orientation.
	 * 
	 * @param name - Name of the ship
	 * @param row - Location of the ship vertically.
	 * @param col - Location of the ship horizontally.
	 * @param orientation - Directoin from which the ship is build out from the coordinate. "up", "down", "left", or "right"
	 * @return - true: if ship was successfully created. false: if ship could not be created for some reason.
	 */
	public boolean addShip(String name, int row, int col, String orientation){

		Ship newShip = null;
		name = name.toLowerCase(Locale.ROOT);

		switch(name){
			case "destroyer" -> {
				newShip = new Destroyer(row, col, orientation);
			}
			case "cruiser" -> {
				newShip = new Cruiser(row, col, orientation);
			}
			case "submarine" -> {
				newShip = new Submarine(row, col, orientation);
			}
			case "battleship" -> {
				newShip = new Battleship(row, col, orientation);
			}
			case "carrier" -> {
				newShip = new Carrier(row, col, orientation);
			}
			default -> {
				return false;
			}
		}

		int length = newShip.length();

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

	/**
	 * fire on another player. 
	 * player will change their view of enemy map in response
	 * 
	 * @param row - vertical target
	 * @param col - horizontal target
	 * @param enemyGame - the enemyGame, so hit/miss can be confirmed
	 */
	public void fireUpon(int row, int col, Game enemyGame){
		enemyMap[row][col] = (enemyGame.getMyMap()[row][col] == ship) ? hit : miss;
	}
	
	public void takeFire(int row, int col){
		if(myMap[row][col] == ship){
			myMap[row][col] = hit;
			health--;
		} else {
			myMap[row][col] = miss;
		}
	}
	
}

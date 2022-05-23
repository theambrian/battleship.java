import GameElements.*;

public class Game {
	private char[][] myMap; //player's ships. bottom of battleship board
	private char[][] enemyMap; //what the player knows about enemy's ship. top of battleship board.
	private char water = '~';
	private char hit = 'x';
	private char miss = 'o';
	private char ship = '^';
	private int health; //total health of player's ships.

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
	public void setHealth(int n){this.health = n;}

	public boolean addShip(Ship newShip){
		String orientation = newShip.getOrientation();
		int rowNum = newShip.getRow();
		int colNum = newShip.getCol();
		int length = newShip.length();

		switch (orientation) {
			case "up" -> {
				if (length - rowNum < 0) return false;
				for (int i = 0; i < length; i++) {
					myMap[rowNum - i][colNum] = ship;
				}
			}
			case "down" -> {
				if (length + rowNum > 9) return false;
				for (int i = 0; i < length; i++) {
					myMap[rowNum + i][colNum] = ship;
				}
			}
			case "left" -> {
				if (length - colNum > 0) return false;
				for (int i = 0; i < length; i++) {
					myMap[rowNum][colNum - i] = ship;
				}
			}
			case "right" -> {
				if (length + colNum > 9) return false;
				for (int i = 0; i < length; i++) {
					myMap[rowNum][colNum + i] = ship;
				}
			}
			default -> {
			}
		}
		return true;
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

import GameElements.*;

public class Game {
	char[][] myMap; //player's ships. bottom of battleship board
	char[][] enemyMap; //what the player knows about enemy's ship. top of battleship board.
	char water = '~';
	char hit = 'x';
	char miss = 'o';
	char ship = '^';
	int health; //total health of player's ships.

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
	public void setMyMap(int row, int col, char newStatus){
		myMap[row][col] = newStatus;
	}
	public void setEnemyMap(int row, int col, char newStatus){enemyMap[row][col] = newStatus;}
	public void takeHealth(){health--;}

	public boolean addShip(Ship newShip){
		String orientation = newShip.getOrientation();
		int[] start = newShip.getStart();
		int length = newShip.length();
		String name = newShip.getName();

		switch (orientation) {
			case "up" -> {
				if (length - start[0] < 0) return false;
				for (int i = 0; i < length; i++) {
					myMap[start[0] - i][start[1]] = ship;
				}
			}
			case "down" -> {
				if (length + start[0] > 9) return false;
				for (int i = 0; i < length; i++) {
					myMap[start[0] + i][start[1]] = ship;
				}
			}
			case "left" -> {
				if (length - start[1] > 0) return false;
				for (int i = 0; i < length; i++) {
					myMap[start[0]][start[1] - i] = ship;
				}
			}
			case "right" -> {
				if (length + start[1] > 9) return false;
				for (int i = 0; i < length; i++) {
					myMap[start[0]][start[1] + i] = ship;
				}
			}
			default -> {
			}
		}
		return true;
	}

	//fire on another player. both player and enemy will change their maps in response
	public void fireUpon(int row, int col, Game enemyGame){
		if(enemyGame.getMyMap()[row][col] == ship){
			setEnemyMap(row, col, hit);
		} else {
			setEnemyMap(row, col, miss);
		}
	}
	
	public void takeFire(int row, int col){
		if(getMyMap()[row][col] == ship){
			setMyMap(row, col, hit);
			takeHealth();
		} else {
			setMyMap(row, col, miss);
		}
	}



}

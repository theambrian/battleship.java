package GameElements;

import java.util.HashMap;
public abstract class Ship {
	private HashMap<Integer, Boolean> body;
	private final int row, col; // starting coordinates for the ship
	private final int length;
	private final String orientation; // direction that the ship points, outward from the start.
								// up/down/left/right
	private final String name;

	public Ship(int newLength, int newRow, int newCol, String newOrientation, String newName){

		body = new HashMap<>();
		for(int i = 0; i < newLength; i++){
			body.put(i, true);
		}
		length = newLength;
		row = newRow;
		col = newCol;
		orientation = newOrientation;
		name = newName;
	}

	public int length(){return body.size();}
	public HashMap<Integer, Boolean> getBody(){return body;}
	public String getOrientation(){return orientation;}
	public int getRow(){return row;}
	public int getCol(){return col;}
	public String getName() {return name;}

	public void hit(int target){
		body.put(target, false);
	}

	public boolean isPlaceable(){
		switch(orientation){
			case "up" -> {return !(row - length < 0);}
			case "down" -> {return !(length + row > 9);}
			case "left" -> {return !(length - col > 0);}
			case "right" -> {return !(length + col > 9);}
			default -> {return false;}
		}
	}

}

package GameElements;


public abstract class Ship {
	private final int row, col;
	private final int length;

	// direction that the ship points, outward from the start.
	// "up" | "down" | "left" | "right"
	private final String orientation;

	public Ship(int newLength, int newRow, int newCol, String newOrientation){

		length = newLength;
		row = newRow;
		col = newCol;
		orientation = newOrientation;

	}

	public int length(){return length;}




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

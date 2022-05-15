package GameElements;

import java.util.HashMap;
public abstract class Ship {
	private HashMap<Integer, Boolean> targets;
	private int[] start; // starting coordinate of the ship
	private String orientation; // direction that the ship points, outward from the start.
								// up/down/left/right

	public Ship(int length, int[] start, String orientation){

		targets = new HashMap<>();
		for(int i = 0; i < length; i++){
			targets.put(i, true);
		}

		this.start = start;
		this.orientation = orientation;
	}
}

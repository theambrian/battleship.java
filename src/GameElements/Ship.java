package GameElements;

import java.util.HashMap;
public abstract class Ship {
	private HashMap<Integer, Boolean> body;
	private int[] start; // starting coordinate of the ship
	private String orientation; // direction that the ship points, outward from the start.
								// up/down/left/right
	private String name;

	public Ship(int length, int[] start, String orientation, String name){

		this.body = new HashMap<>();
		for(int i = 0; i < length; i++){
			this.body.put(i, true);
		}

		this.start = start;
		this.orientation = orientation;
		this.name = name;
	}

	public int length(){return this.body.size();}
	public HashMap<Integer, Boolean> getBody(){return this.body;}
	public String getOrientation(){return this.orientation;}
	public int[] getStart(){return this.start;}

	public String getName() {return name;}

	public void hit(int target){
		body.put(target, false);
	}
}

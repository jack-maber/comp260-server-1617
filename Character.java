import java.util.HashMap;

public class Character {

	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;
	
	// Potential next X
	public int nextX = 0;
	//Potential next Y
	private int nextY = 0;
	
	// Stores which player this character is attached to.
	private int playerID;
	
	// Possible movement commands
	private static String movementCommands[] = {
	        "north", "south", "east", "west"
	    };
	
	private Map map = Map.getInstance();

	// Sets character x and y on initialisation
	public Character(int initialX, int initialY, int playerID) {
		setPosition(initialX, initialY);
		this.playerID = playerID;
	}

	// Getters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// Sets character position
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

	// Checks to see which cell on the map the character is currently on
	public Cell getCurrentCell(){
		return map.getCells()[x][y];
	}
	
	// Checks a cell in the map to see if it's free
	public boolean checkForFreeCell(){
		if (map.getCells()[nextX][nextY].getCellContent() == null 
				&& !(map.getCells()[nextX][nextY].cellTaken))
			return true;
		else
			return false;
	}	
	
	// Checks string is valid and updates character position
	public synchronized void moveCharacter(String inputMovement){
		if (inputMovement == "north")
		{
			nextX += 1;
		}
		else if (inputMovement == "south")
		{
			nextX -= 1;
		}
		else if (inputMovement == "east")
		{
			nextY += 1;
		}
		else if (inputMovement == "west")
		{
			nextY -= 1;
		}

		if (checkForFreeCell())
		{
			getCurrentCell().cellTaken = false;
			setPosition(nextX, nextY);
			getCurrentCell().cellTaken = true;
		}
	}
	
}

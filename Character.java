import java.util.HashMap;

public class Character {

	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;
	
	// Potential next X
	private int nextX = 0;
	//Potential next Y
	private int nextY = 0;
	
	// Stores which player this character is attached to.
	private int playerID;
	
	private Cell currentCell;
	
	private static String movementCommands[] = {
	        "north", "south", "east", "west"
	    };
	
	private Map map = Map.getInstance();

	// Sets character x and y on initialisation
	public Character(int initialX, int initialY) {
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

	// Setter
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

	// Checks to see which cell on the map the character is currently on
	public void getCurrentCell(){
		currentCell = map.getCells()[x][y];
	}
	
	// Checks a cell in the map to see if it's free
	public boolean checkForFreeCell(){
		if (map.getCells()[nextX][nextY].getCellContent() == null 
				&& !(map.getCells()[nextX][nextY].cellTaken))
			return true;
		else
			return false;
	}	
	
	
	public synchronized void moveCharacter(String inputMovement){
		if (inputMovement == movementCommands[0])
		{
			nextX += 1;
		}
		else if (inputMovement == movementCommands[1])
		{
			nextX -= 1;
		}
		else if (inputMovement == movementCommands[3])
		{
			nextY += 1;
		}
		else if (inputMovement == movementCommands[4])
		{
			nextY -= 1;
		}
		
		if (checkForFreeCell())
		{
			setPosition(nextX, nextY);
			currentCell.cellTaken = false;
			currentCell = map.getCells()[x][y];
			currentCell.cellTaken = true;
		}
	}
	
}

public class Character {
	
	Character(){
		
	}
	
	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;
	
	// Potential next X
	public int nextX = 0;
	//Potential next Y
	private int nextY = 0;
	
	// Possible movement commands
	private static String movementCommands[] = {
	        "north", "south", "east", "west"
	    };
	
	private Map map = Map.getInstance();

	// Sets character x and y on initialisation
	public Character(int initialX, int initialY) {
		setPosition(initialX, initialY);
		nextX = x;
		nextY = y;
	}

	// Gets character position
	public int[] getPosition() {
		int[] array = new int[2];
		array[0] = x;
		array[1] = y;
		return array;
	}
	
	//Gets X value
	public int getX(){
		return x;
	}
	// Gets Y value
	public int getY(){
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
		if (inputMovement == movementCommands[0])
		{
			nextX++;
		}
		else if (inputMovement == movementCommands[1])
		{
			nextX--;
		}
		else if (inputMovement == movementCommands[2])
		{
			nextY++;
		}
		else if (inputMovement == movementCommands[3])
		{
			nextY--;
		}

		if (checkForFreeCell())
		{
			getCurrentCell().cellTaken = false;
			setPosition(nextX, nextY);
			getCurrentCell().cellTaken = true;
		}
	}
	
}

import java.util.HashMap;

public class Character {

	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;
	
	// Stores which player this character is attached to.
	private int playerID;
	
	private Cell currentCell;
	
	HashMap movementCommands = new HashMap(); 
	
	Map map = Map.getInstance();

	// Sets character x and y on initialisation
	public Character(int initialX, int initialY, int playerID) {
		setPosition(initialX, initialY);
		this.playerID = playerID;
		movementCommands.put("north", 1);
		movementCommands.put("south", -1);
	}

	// Getters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// Setters
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}
/*
	public void getCurrentCell(Map map){
		currentCell = map.getCells()[x][y];
	}
	
	public boolean checkCell(Map map,int moveToX, int moveToY){
		if (map.getCells()[moveToX][moveToY].getCellContent() == null 
				&& (map.getCells()[moveToX][moveToY].cellTaken))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	public synchronized void moveCharacter(String inputMovement, Map map){
		if (checkCell(map))
		{
			setPosition(moveToX, moveToY);
			currentCell.cellTaken = false;
			currentCell = map.getCells()[x][y];
			currentCell.cellTaken = true;
		}
	}*/
	
}

public class Character {

	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;
	
	// Stores which player this character is atteched to.
	private int playerID;
	
	private Cell currentCell;

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

	// Setters
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

	public void getCurrentCell(Map map){
		currentCell = map.getCells()[x][y];
	}
	
	public synchronized void moveCharacter(int moveToX, int moveToY, Map map){
		if (map.getCells()[moveToX][moveToY].getCellContent() == null && (map.getCells()[moveToX][moveToY].cellTaken))
		{
			setPosition(moveToX, moveToY);
			currentCell.cellTaken = false;
			currentCell = map.getCells()[x][y];
			currentCell.cellTaken = true;
		}
	}
	
}

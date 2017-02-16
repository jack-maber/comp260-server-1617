public class Character {

	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;
	
	private Cell currentCell;

	// Sets character x and y on initialisation
	public Character(int initialX, int initialY) {
		setPosition(initialX, initialY);
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
	
	public void moveCharacter(int moveToX, int moveToY, Map map){
		if (map.getCells()[moveToX][moveToY].getCellContent() == null)
		{
			setPosition(moveToX, moveToY);
			currentCell = map.getCells()[x][y];
		}
	}

}

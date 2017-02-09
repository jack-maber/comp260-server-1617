
public class Character {

	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;
			
	// Sets character x and y on initialisation
	public Character (int initialX, int initialY){
		setX(initialX);
		setY(initialY);
	}
	//Getters
	public int getX(){return x;}
	public int getY(){return y;}
	// Setters
	public void setX(int newX){x = newX;}
	public void setY(int newY){y = newY;}

	// Get Cell that the character is on
	// Only works within regions
	public Cell getCell(Chunk mapChunk)
	{
		return mapChunk.getCells()[x][y];
	}
}


public class Character {

	private int x = 0;
	private int y = 0;
	private boolean isActive = true;
	
	public Character (int initialX, int initialY){
		setX(initialX);
		setY(initialY);
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	
	public void setX(int newX){x = newX;}
	public void setY(int newY){y = newY;}
	
	
}

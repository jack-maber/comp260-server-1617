

public class Enemy {

	// Bool for whether the virus is active
	boolean virusStatus = true;
	// Int for virus X location
	private int x = 0;
	// Int for virus Y location
	private int y = 0;
	
	// Sets Enemy position
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}
	// Constructor to set intial position
	public Enemy(int initialX, int initialY) {
		setPosition(initialX, initialY);
	}	
}

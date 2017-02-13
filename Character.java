public class Character {

	// Character X location
	private int x = 0;
	// Character Y location
	private int y = 0;

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


	// Get Cell that the character is on
	// Only works within regions

}

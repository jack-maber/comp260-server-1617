package db;

public class Enemy {
	
	public void virus(){
		
	boolean virusStatus = true;  //stores whether they are alive or dead true is alive false is dead
		
	}
	private int x = 0;
	private int y = 0;
	
	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public Enemy(int initialX, int initialY) {
		setPosition(initialX, initialY);
	}
	
	
}

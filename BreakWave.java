import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class BreakWave {
	
	static int interval;
	static Timer timer;

	public synchronized static void main(String[] args) {
		
		boolean round = false;  //The round is false which means that the break time countdown starts
		
		while (round = false);
		{
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Break has began ");
	    int secs = 30;
	    int delay = 500;
	    int period = 500;
	    timer = new Timer();
	    interval = 30;
	    System.out.println(secs);
	    timer.scheduleAtFixedRate(new TimerTask() {

	        public void run() {
	            System.out.println("Time left " + setInterval());

	        }
	    }, delay, period);
	}}

	private synchronized static final int setInterval() { //synchronized is used to keep the reading of this for all threads consistent so they follow the same time
	    if (interval == 1)
	        timer.cancel();
	    	boolean round = true; //if the interval is finished sets the round value to true which will start the enemy wave
	    return --interval;
	}
	
	
}

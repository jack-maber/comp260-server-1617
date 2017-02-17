import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class BreakWave {

	static int interval;
	static Timer timer;

	public synchronized static void main(String[] args) {
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
	}

	private static final int setInterval() {
	    if (interval == 1)
	        timer.cancel();
	    return --interval;
	}
	
}

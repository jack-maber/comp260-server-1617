// singleton timer

public class Timer {
	
	public int ticksPass;
	
	private static Timer timer = new Timer();

	private Thread thread;

	private Timer() {
		thread = new TickThread();
		thread.start();
	}

	public static Timer getInstance() {
		return timer;
	}

	protected Thread getTick() {
		ticksPass++;
		return thread;
	}

	public int getTicksPass(){
		return ticksPass;
	}
	
	protected void killTimer() {
		thread.interrupt();
	}
}

class TickThread extends Thread {
	// waits for a specified time and then will go through all the commands.
	public void run() {
		Timer timer = Timer.getInstance();
		while (true) {
			try {
				timer.ticksPass = timer.ticksPass + 1;//every 5th of a second adds 1 
				//System.out.println(timer.ticksPass);
				Thread.sleep(200);
			}
			catch (InterruptedException threadShutdown) {
				break;
			}
		}
	}
}

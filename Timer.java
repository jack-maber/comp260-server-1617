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
	
	protected Thread getTickThread(){
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
		Parser parser = Parser.getInstance();
		Timer timer = Timer.getInstance();
		while (true) {
			try {
				timer.ticksPass = timer.ticksPass + 1; //every 5th of a second adds 1
				parser.executeCommands();
				parser.emptyCommandList();
				System.out.println(parser.getCommands());
				//System.out.println(timer.ticksPass);
				Thread.sleep(200);
			}
			catch (InterruptedException threadShutdown) {
				break;
			}
		}
	}
}

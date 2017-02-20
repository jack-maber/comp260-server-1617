// singleton timer

public class Timer {

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
		return thread;
	}

	protected void killTimer() {
		thread.interrupt();
	}
}

class TickThread extends Thread {
	// waits for a specified time and then will go through all the commands.
	public void run() {
		Parser parser = Parser.getInstance();
		while (true) {
			//parser.
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException threadShutdown) {
				break;
			}
		}
	}
}

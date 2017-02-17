// singleton timer

public class Tick extends Thread{
	
	private static Tick tick = new Tick();
	
	private Tick() {
		while (true){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Tick getInstance(){
		return tick;
	}
}

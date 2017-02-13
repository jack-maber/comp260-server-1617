//To do:
//when player starts wave it starts the wave length timer
//when player kills wave it starts break timer
//when break timer finishes it starts wave timer
import java.util.Timer;
import java.util.TimerTask;

 
public class VirusWave extends TimerTask {

	
	@Override
	public void run(){
		System.out.println("Wave Started");
		completeWave();
		System.out.println("Wave Ended");
	}
	
	
	private void completeWave(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String arg[]){
		TimerTask waveTimer = new VirusWave();
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(waveTimer, 0, 10*500);
		System.out.println("Wave Started");
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("Wave cancelled");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		
		
	}
	
	
	
	
	
}


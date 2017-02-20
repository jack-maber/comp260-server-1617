import java.util.Scanner;


public class BreakWave {


	public synchronized static void main(String[] args) {
		
		Timer timer = Timer.getInstance();
		
		boolean round = false;
		
		while (!round)
		{
			System.out.println(timer.ticksPass);
			if (timer.getTicksPass() > 10)
			{
				System.out.println("Break Over");
				round = true;
			}
		}
	}
}

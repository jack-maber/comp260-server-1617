/*
 * WaveManager to handle when the enemy waves are active
 */
public class WaveManager {
	// Instance of Time singleton used to get ticks
	Timer timer = Timer.getInstance();
	
	// Enum of possible wave states
	public enum Round {BREAK, ENEMY}
	
	public static class EnumTest {
	    Round round;
	    
	    public EnumTest(Round round) {
	        this.round = round;
	    }
	
	        public void currentRound() {
	            switch (round) {
	                case BREAK:
	                    System.out.println("Break");
	                    break;
	                        
	                case ENEMY:
	                    System.out.println("Enemy");
	                    break;  
	         }
	     }
	   // Code to be adapted into function to switch between waves	        
	   /*public static void main(String[] args) {
	   EnumTest firstDay = new EnumTest(Round.BREAK);
	   firstDay.currentRound();
	   EnumTest thirdDay = new EnumTest(Round.ENEMY);
       thirdDay.currentRound();
	    }*/
	  
	// Code from original BreakManager that uses ticks to switch waves
	/* while (!round)
		{
			System.out.println(timer.ticksPass);
			if (timer.getTicksPass() > 10)
			{
				System.out.println("Break Over");
				round = true;
			}
		} */
	}
}
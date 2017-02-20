public class WaveManager {

	Timer timer = Timer.getInstance();
	
	
	
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
	
	   /*public static void main(String[] args) {
	   EnumTest firstDay = new EnumTest(Round.BREAK);
	   firstDay.currentRound();
	   EnumTest thirdDay = new EnumTest(Round.ENEMY);
       thirdDay.currentRound();
	    }*/
	  }
	
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
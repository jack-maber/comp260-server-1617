
public class Region {
	/* contains multiple chunks preferably the use of multiple
	 * regions should be for large numbers of people
	*/
	private Chunk[][] region = new Chunk[5][5];
	
	public Chunk[][] getChunks() {return region;}

}

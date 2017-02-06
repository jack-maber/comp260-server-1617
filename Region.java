
public class Region {
	/* contains multiple chunks preferably the use of multiple
	 * regions should be for large numbers of people
	*/
	public Region(){
		for (int x = 0; x < region.length; x++){
			for (int y = 0; y < region[0].length; y++){
				region[x][y] = new Chunk();
			}
		}
	}
	
	private Chunk[][] region = new Chunk[5][5];
	
	public Chunk[][] getChunks() {return region;}

}

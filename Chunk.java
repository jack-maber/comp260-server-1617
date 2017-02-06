
public class Chunk {
	// smallest area to be loaded at once for usability reasons

	public Chunk(){
		
		//populates the chunk
		for (int x = 0; x < chunk.length; x++){
			for (int y = 0; y < chunk[0].length; y++){
				chunk[x][y] = new Cell();
			}
		}
	}
	
	private Cell[][] chunk = new Cell[5][5];
	
	public Cell[][] getCells() { return chunk; };
}

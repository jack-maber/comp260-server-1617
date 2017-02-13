
public class Map {
	
	public Map(){
		
		//populates the map
		for (int x = 0; x < cells.length; x++){
			for (int y = 0; y < cells[0].length; y++){
				cells[x][y] = new Cell();
			}
		}
	}
	
	private Cell[][] cells = new Cell[100][100];
	
	public void setCell(int x, int y, String content){
		getCells()[x][y].setCellContent(content);
		}
	
	public Cell[][] getCells() { return cells; }
	
	//uncomment if we go back into a tree based system:
	
	//private Region[][] map = new Region[4][4];
	
	//public Region[][] getRegions() { return map; }

}

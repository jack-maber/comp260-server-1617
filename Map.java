
public class Map {
	
	public Map(){
		
		//populates the map
		for (int x = 0; x < map.length; x++){
			for (int y = 0; y < map[0].length; y++){
				map[x][y] = new Region();
			}
		}
	}
	
	private Region[][] map = new Region[4][4];
	
	public Region[][] getRegions() { return map; }

}

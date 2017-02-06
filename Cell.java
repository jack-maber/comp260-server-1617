
public class Cell {
	// the smallest form of area
	public Cell(){
		cellContent = new Contents(0);
	}
	
	private Contents cellContent;
	
	public void setCellContent() {cellContent.setIndex(0);}
	
	public int getCellContent() {return cellContent.getIndex();}
}

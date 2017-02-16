
public class Cell {
	// the smallest form of area
	public Cell(){
	}
	
	private String cellContent;
	
	public boolean cellTaken = false;
	
	public void setCellContent(String content) {cellContent = content;}
	
	public String getCellContent() {return cellContent;}
}

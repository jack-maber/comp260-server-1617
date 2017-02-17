
public class Cell {
	// the smallest form of area
	public Cell(){
	}
	// Whether there is anything in the cell
	private String cellContent;
	// Bool for whether a character is in the cell
	public boolean cellTaken = false;
	// Setter
	public void setCellContent(String content) {cellContent = content;}
	// Getter
	public String getCellContent() {return cellContent;}
}

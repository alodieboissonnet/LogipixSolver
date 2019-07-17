
public class Cell {
	int x;
	int y;
	int number;
	boolean isLinked;
	int color;  // color = 0 if not-colored, color = 1 if maybe colored, color = 2 if colored
	
// Constructor
	public Cell(int x, int y, int number) {
		this.x = x;
		this.y = y;
		this.number = number;
		this.color = 0;
		this.isLinked = false;
	}
}

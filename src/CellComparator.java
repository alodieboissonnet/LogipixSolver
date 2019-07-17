import java.util.Comparator;

public class CellComparator implements Comparator<Cell>{
	
// Defines a comparator for cells
	@Override
	public int compare(Cell c1, Cell c2) {
		return c1.number - c2.number;
	}
}

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class Test4 {

// Applies combination/exclusion to a logipix puzzle
	public static void main(String[] args) throws IOException {
			Logipix logipix = new Logipix("Man.txt");   // we can choose the file we want to work on
			Logipix.paint(logipix);
			
			Comparator<Cell> comparator = new CellComparator();
			PriorityQueue<Cell> Q = new PriorityQueue<Cell>(logipix.height * logipix.width / 10, comparator);
			for (int x = 0; x < logipix.height; x++) {
				for (int y = 0; y < logipix.width; y++) {
					if (logipix.cells[x][y].number != 0 && !logipix.cells[x][y].isLinked)
						Q.add(logipix.cells[x][y]);
				}
			}
			long t0 = System.currentTimeMillis();
			Logipix.combinationExclusion(logipix, Q);
			long t1 = System.currentTimeMillis();
			Logipix.paint(logipix);
			System.out.println("Using combination/exclusion takes " + (t1-t0) + " ms.");
	}

}

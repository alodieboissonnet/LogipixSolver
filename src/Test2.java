import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

public class Test2 {

// Finds all possible broken lines corresponding to a given clue and colors all cells of these lines
	public static void main(String[] args) throws IOException {
		Logipix logipix = new Logipix("Man.txt");     // we can choose the file we want to work on
		Vector<LinkedList<Cell>> allBrokenLines = Logipix.allBrokenLines(logipix,logipix.cells[0][6]); 
		for (LinkedList<Cell> l : allBrokenLines) { 
			for (Cell c : l) 
				c.color = 1; 
		}
		Logipix.paint(logipix);
	}
}

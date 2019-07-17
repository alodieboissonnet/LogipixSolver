import java.io.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;
import java.util.Comparator;

public class Logipix {
	int height;
	int width;
	Cell[][] cells;

	// Constructs a logipix puzzle from a file
	public Logipix(String file) throws IOException {
		Scanner inFile = new Scanner(new FileReader(file));
		if (inFile.hasNextLine()) {
			this.width = Integer.parseInt(inFile.nextLine());
			this.height = Integer.parseInt(inFile.nextLine());
		}
		this.cells = new Cell[height][width];
		for (int i = 0; i < this.height; i++) {
			String s = inFile.nextLine();
			String[] numbers = s.split(" ");
			for (int j = 0; j < numbers.length; j++) 
				cells[i][j] = new Cell(i, j, Integer.parseInt(numbers[j]));
		}
		inFile.close();
	}

	// Draws a state of the logipix puzzle
	public static void paint(Logipix logipix) {
		BinaryImage createdImg = new BinaryImage(logipix.width * 20, logipix.height * 20);
		createdImg.fill(logipix.height, logipix.width, logipix.cells);
		new ImageViewer(createdImg, logipix.cells);
	}

	// Checks if a cell can be part of a possible broken line corresponding to a given clue
	public static boolean isValid(Logipix logipix, int x, int y, LinkedList<Cell> L) {
		if (x >= logipix.height || x < 0 || y >= logipix.width || y < 0 || logipix.cells[x][y].color == 2
				|| L.contains(logipix.cells[x][y]))
			return false;
		return true;
	}

	// Checks if a broken line can be a possible broken line corresponding to a given clue
	public static int possibleLine(LinkedList<Cell> L, Cell c0) {
		if (L.size() == c0.number) {
			if (L.getLast().number == c0.number) 
				return 0; // it can a possible broken line
			else
				return 1; // it can't be a possible broken line
		} 
		else if (L.size() != 1 && L.getLast().number != 0)
			return 1; // it can't be a possible broken line
		else
			return 2; // the line is not long enough
	}

	// Returns a list of all possible broken lines corresponding to a given clue
	public static Vector<LinkedList<Cell>> allBrokenLines(Logipix logipix, Cell c0) {
		Vector<LinkedList<Cell>> allBrokenLines = new Vector<LinkedList<Cell>>();
		Vector<LinkedList<Cell>> tmp = new Vector<LinkedList<Cell>>();
		LinkedList<Cell> L0 = new LinkedList<Cell>();
		L0.add(c0);
		tmp.add(L0);
		LinkedList<Cell> L = new LinkedList<Cell>();
		int possible = 0;
		Cell c = c0;

		if (possibleLine(L0, c0) == 0) // if c0.number == 1, there is only one possible broken line that contains only c0
			return tmp;

		while (!tmp.isEmpty()) {
			L = tmp.remove(0);
			c = L.getLast();

			// cas 1 : we look at the cell on (x+1,y)
			if (isValid(logipix, c.x + 1, c.y, L)) {
				LinkedList<Cell> L1 = new LinkedList<Cell>();
				L1.addAll(L);
				L1.add(logipix.cells[c.x + 1][c.y]);
				possible = possibleLine(L1, c0);
				if (possible == 0)
					allBrokenLines.add(L1);
				else if (possible == 2)
					tmp.add(L1);
			}

			// cas 2 : we look at the cell on (x,y+1)
			if (isValid(logipix, c.x, c.y + 1, L)) {
				LinkedList<Cell> L2 = new LinkedList<Cell>();
				L2.addAll(L);
				L2.add(logipix.cells[c.x][c.y + 1]);
				possible = possibleLine(L2, c0);
				if (possible == 0)
					allBrokenLines.add(L2);
				else if (possible == 2)
					tmp.add(L2);
			}

			// cas 3 : we look at the cell on (x-1,y)
			if (isValid(logipix, c.x - 1, c.y, L)) {
				LinkedList<Cell> L3 = new LinkedList<Cell>();
				L3.addAll(L);
				L3.add(logipix.cells[c.x - 1][c.y]);
				possible = possibleLine(L3, c0);
				if (possible == 0)
					allBrokenLines.add(L3);
				else if (possible == 2)
					tmp.add(L3);
			}

			// cas 4 : we look at the cell on (x,y-1)
			if (isValid(logipix, c.x, c.y - 1, L)) {
				LinkedList<Cell> L4 = new LinkedList<Cell>();
				L4.addAll(L);
				L4.add(logipix.cells[c.x][c.y - 1]);
				possible = possibleLine(L4, c0);
				if (possible == 0)
					allBrokenLines.add(L4);
				else if (possible == 2)
					tmp.add(L4);
			}
		}
		return allBrokenLines;
	}

	// Solves a logipix puzzle with simple backtracking
	public static boolean logipixSolver1(Logipix logipix) {
		Vector<LinkedList<Cell>> logipixSol = new Vector<LinkedList<Cell>>();

		// initialization of the priority queue
		Comparator<Cell> comparator = new CellComparator();
		PriorityQueue<Cell> Q = new PriorityQueue<Cell>(logipix.height * logipix.width / 10, comparator);
		for (int x = 0; x < logipix.height; x++) {
			for (int y = 0; y < logipix.width; y++) {
				if (logipix.cells[x][y].number != 0 && !logipix.cells[x][y].isLinked)
					Q.add(logipix.cells[x][y]);
			}
		}

		// solver with backtracking
		while (!Q.isEmpty()) {
			Cell c1 = Q.poll();

			// special case
			if (c1.number == 1 || c1.number == 2) {
				c1.color = 2;
				c1.isLinked = true;
				continue;
			}

			Vector<LinkedList<Cell>> allBrokenLines = allBrokenLines(logipix, c1);
			if (allBrokenLines.isEmpty())
				return false;
			for (LinkedList<Cell> L : allBrokenLines) {
				logipixSol.add(L);
				for (Cell c2 : L)
					c2.color = 2;
				c1.isLinked = true;
				L.getLast().isLinked = true;
				if (logipixSolver1(logipix))
					return true;
				else {
					for (Cell c2 : L)
						c2.color = 0;
					c1.isLinked = false;
					L.getLast().isLinked = false;
				}
			}
			return false;
		}
		return true;
	}

	// Combination idea : returns cells belonging to all possible broken lines corresponding to a given clue
	public static LinkedList<Cell> combination(Logipix logipix, Cell c0, PriorityQueue<Cell> Q0) {
		Vector<LinkedList<Cell>> allBrokenLines = allBrokenLines(logipix, c0);
		LinkedList<Cell> commonCells = new LinkedList<Cell>();

		if (allBrokenLines.isEmpty()) throw new Error("Ce logipix n'a pas de solution.");

		else if (allBrokenLines.size() == 1) { // if there is only one possible broken line, this line takes part of the solution												// solution
			LinkedList<Cell> L = allBrokenLines.remove(0);
			L.getFirst().isLinked = true;
			L.getLast().isLinked = true;
			for (Cell c : L)
				c.color = 2;
			return null;
		}

		else {
			LinkedList<Cell> L = allBrokenLines.remove(0); // allBrokenLines can't be empty if we have a solution
			commonCells.addAll(L);

			while (!allBrokenLines.isEmpty()) {
				L = allBrokenLines.remove(0);
				Vector<Cell> tmp = new Vector<Cell>();
				for (Cell c1 : commonCells) {
					if (L.contains(c1))
						tmp.add(c1);
				}
				commonCells.clear();
				commonCells.addAll(tmp);
			}

			for (Cell c2 : commonCells)
				c2.color = 1;

			return commonCells;
		}
	}

	// combination and exclusion ideas : removes lines that are not compatible anymore
	public static void combinationExclusion(Logipix logipix, PriorityQueue<Cell> Q0) {
		boolean hasChanged = true;
		while (hasChanged) { // ends when there is no more changes
			hasChanged = false;
			for (Cell c : Q0) {
				if (!c.isLinked) {
					LinkedList<Cell> commonCells = combination(logipix, c, Q0);
					if (commonCells == null) 
						hasChanged = true;
				}
			}
		}
	}

	// Solves a logipix puzzle with backtracking and combination/exclusion ideas
	public static boolean logipixSolver2(Logipix logipix) {
		// initialization of a priority queue
		Comparator<Cell> comparator = new CellComparator();
		PriorityQueue<Cell> Q = new PriorityQueue<Cell>(logipix.height * logipix.width / 10, comparator);
		for (int x = 0; x < logipix.height; x++) {
			for (int y = 0; y < logipix.width; y++) {
				if (logipix.cells[x][y].number != 0 && !logipix.cells[x][y].isLinked)
					Q.add(logipix.cells[x][y]);
			}
		}

		// combination and exclusion ideas
		combinationExclusion(logipix, Q);
		return logipixSolver1(logipix);
	}
}

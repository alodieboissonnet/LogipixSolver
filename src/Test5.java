import java.io.IOException;

public class Test5 {

// Solves a logipix puzzle with simple backtracking and prints the running time
	public static void main(String[] args) throws IOException {
		Logipix logipix = new Logipix("Immensite.txt");    // we can choose the file we want to work on
		Logipix.paint(logipix);
			
		long t0 = System.currentTimeMillis();
		Logipix.logipixSolver2(logipix);
		long t1 = System.currentTimeMillis();
		System.out.println("Solving this logipix puzzle with backtraking and combination/exclusion takes " + (t1-t0) + " ms.");
		Logipix.paint(logipix);
	}

}

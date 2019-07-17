# LogipixSolver
Logipix solver in Java

# Description of the project
The purpose of this project is to write an efficient solver for logipix puzzles. The goal of a logipix puzzle is to discover an hidden picture thanks to some numerical clues. Each puzzle consists of a grid containing easy empty cells or cells with numbers. Every number, except for the 1’s, is half of a pair. The object is to link the pairs and painting the paths so that the number of squares in the path, including the squares at the ends, equals the value of the clues being linked together. Squares containing 1 represent paths that are 1-square long. Paths may follow horizontal or vertical directions, can turn and are not allowed to cross other paths. Such a path will be called a broken line in the rest of the text.


# Setting up the game
Reading a file and instanciate a game. A logipix puzzle will be given by a file of the following format. The first two lines give respectively the width and the height of the grid. Then each line gives the clues line by line, where the empty cells are represented by zeroes.
For instance, a logipix file can look like :
11

9

2 2 3 0 0 0 0 0 0 4 1 

0 1 0 0 0 0 0 0 0 1 0 

0 0 3 1 0 0 0 1 4 0 0 

0 0 0 4 0 1 0 6 0 0 0

...


# Solving a Logipix puzzle with backtracking
The first method we are going to use to solve a logipix puzzle is quite brutal. The idea is the following. For each clue, we generate the set of all possible broken lines. We start with the first clue and pick one of the possibility. Then we move to the second clue, do the same, while ensuring that this is compatible with the possibility we picked for the first clue and so on and so forth. If, at some point, we run out of possibilities, we change one of the choice we made before, hence the name backtracking.


# Combination and exclusion
We now consider combinations of partial possible solutions. Generate all the possible broken lines corresponding to a given clue. If the intersection of all these lines, if it is non empty, then we can conclude that the cases in the intersection belong to the solution.

After identifying successfully some cases of the solution, some lines are not compatible anymore with the new current state. We can then apply ”exclusion”, remove these lines and possibly identify some new occupied cases.

# Tests
This project contains 5 test classes, to test all functions. There are no need for arguments, but it is possible to change the file we are working on ("logipiX.txt", "Man.txt", etc).

- Test 1 displays the Logipix grid
- Test 2 illustrates the allBrokenLine method, and displays a Logipix on which all squares accessible from a given starting squared are colored in gray.
- Test 3 solves a Logipix only by using a backtracking method, and displays the solved Logipix
- Test 4 applies the combination/exclusion method to a Logipix while there are changes, and displays a new Logipix with some path already colored
- Test 5 solves a Logipix by using both combination/exclusion and backtracking methods, and displays the solved Logipix.

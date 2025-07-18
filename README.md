# Java Sudoku Solver

This is a simple Java-based Sudoku Solver that uses a recursive backtracking algorithm to solve 9x9 Sudoku puzzles. It was built as a first project to explore algorithms, recursion, and file I/O in Java.

## Features

- Solves standard 9x9 Sudoku puzzles
- Accepts input via keyboard or text file (`unsolvedsudoku.txt`)
- Outputs the solved puzzle to the console and to `solvedsudoku.txt`
- Includes input validation for rows, columns, and 3x3 subgrids
- Cleanly formatted Sudoku display with subgrid separators

## How It Works

The solver recursively attempts to fill each unassigned cell (marked with 0) with a valid number (1–9), backtracking when necessary until the puzzle is solved.

## Usage

### 1. Compile and Run

```bash
javac SudokuSolver.java
java SudokuSolver
```

### 2. Input Methods

Upon running, you'll be prompted to choose:

- `1`: Exit
- `2`: Input Sudoku manually via keyboard
- `3`: Load Sudoku from `unsolvedsudoku.txt` (must be 9 rows of 9 space-separated digits, using 0 for empty cells)

Example `unsolvedsudoku.txt` format:

```
5 3 0 0 7 0 0 0 0
6 0 0 1 9 5 0 0 0
0 9 8 0 0 0 0 6 0
8 0 0 0 6 0 0 0 3
4 0 0 8 0 3 0 0 1
7 0 0 0 2 0 0 0 6
0 6 0 0 0 0 2 8 0
0 0 0 4 1 9 0 0 5
0 0 0 0 8 0 0 7 9
```

### 3. Output

- The solved Sudoku is printed in the console with grid formatting.
- It’s also saved to `solvedsudoku.txt` in the same directory.

## File Structure

```
.
├── SudokuSolver.java
├── unsolvedsudoku.txt
└── solvedsudoku.txt
```

## Technologies

- Java
- File I/O
- Recursive backtracking algorithm

## License

This project is open source and free to use.

## Author

Created by Edward Ziy – feel free to explore the code and try solving your own puzzles!

package code;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class SudokuSolver {
	
	public static final int gridSize = 9;		//size of the 9x9 sudoku
	public static final int subGridSize = 3;		//size of the 3x3 sub grids
	public static final int unassignedValue = 0;		//value of the unfilled boxes in an unsolved sudoku
	public static final int [][] sudokuGrid = new int [gridSize][gridSize];		//this is the 9x9 grid of the sudoku
	public static final File solvedSudokuFile = new File("solvedsudoku.txt");		//file to store the solved sudoku
	public static final File unsolvedSudokuFile = new File("unsolvedsudoku.txt");		//file for the user to input an unsolved sudoku
	
	public static void main(String[] args) throws Exception
	{
		int menuChoice;
		
		do{
			System.out.println("Welcome to sudoku solver!\n\nplease select 1 of the following 3 methods\nyou wish to use to solve your sudoku:\n");		//main menu for the user to choose their preferred way of inputing the unsolved sudoku
			System.out.println("1. Exit the program");
			System.out.println("2. input the sudoku using your keyboard");
			System.out.println("3. input the sudoku through a file\n");
			menuChoice = menuErrorTrapping(1,3);
		
			if (menuChoice == 1)		//this is choice number 1, it lets the user exited out of the program
			{
				System.out.println("\nYou have exited the program, have a nice day.");
				
				break;
			}
			
			if (menuChoice == 2)		//this is choice number 2, it lets the user to input the unsolved sudoku by hand using the keyboard
			{	
				userInput();
				
				break;	
			}
		
			if (menuChoice == 3)		//this is choice number 3, it lets the user to input the unsolved sudoku by input it through the file
			{
				fileInput();		
				
				break;
			}
		
		}while(menuChoice != 0);	
	}
	
	public static boolean checkInputValueInRow(int row, int inputValue)		//method to check if the number that the user inputed is already in the row of the sudoku
	{
		for(int x = 0; x < gridSize; x++)
		{
			if(sudokuGrid[row][x] == inputValue)		//method returns true if the input value already exists in the sudoku row
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean checkInputValueInColumn(int column, int inputValue)		//method to check if the number that the user inputed is already in the column of the sudoku
	{
		for(int y = 0; y < gridSize; y++)
		{
			if(sudokuGrid[y][column] == inputValue)		//method returns true if the input value already exists in the suodku column
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean checkInputValueInSubGrid(int row, int column, int inputValue)		//method to check if the number that the user inputed is already in the 3x3 sub grid of the sudoku
	{
		int r = row - row % subGridSize;
		
		int c = column - column % subGridSize;
		
		for(int x = r; x < r + subGridSize; x++)
		{
			for(int y = c; y < c + subGridSize; y++)
			{
				if(sudokuGrid[x][y] == inputValue)		//method returns true if the input value already exists in the 3x3 sub grid in the sudoku
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean isInputValueAllowed(int row, int column, int inputValue)		//method that combines the three methods above to ensure that the user input value is safe to store in the cell
	{
		if((checkInputValueInRow(row, inputValue) 
		|| checkInputValueInColumn(column, inputValue) || 
			checkInputValueInSubGrid(row, column, inputValue)))		//if all the 3 previously mentioned methods return true, then isInputValueAllowed returns false as the input value can no longer exists anywhere in the sudoku
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean solveSudoku()		//method to solve the sudoku by using recursive backtracking algorithm
	{
		for(int row = 0; row < gridSize; row++)
		{
			for(int column = 0; column < gridSize; column++)
			{
				if(sudokuGrid[row][column] == unassignedValue)
				{
					for(int inputValue = 1; inputValue <= gridSize; inputValue++)
					{
						if(isInputValueAllowed(row, column, inputValue))		//this checks all the numbers from 1 to 9 and assigned the first possible value to the top left cell
						{
							sudokuGrid[row][column] = inputValue;
							
							if(solveSudoku())
							{
								return true;
							}
							
							else
							{
								sudokuGrid[row][column] = unassignedValue;		//this method starts with the first cell that has the unassigned value of 0
							}
						}
					}
					
					return false;		//if no numbers can be place that fits the requirements of all previous methods, it will return false
				}
			}
		}
		
		return true;		//this method will backtrack over and over until all numbers from 1 to 9 are filled within the unsolved cells thus solving the sudoku as a whole
	}

	public static void displaySudoku()		//method to display the sudoku entered through file back to the user
	{
		System.out.println("\n_________________________________\n\n");
		
		for(int x = 0; x < gridSize; x++)
		{
			if(x % subGridSize == 0 && x != 0)
			{
				System.out.println("");
				
				System.out.println("---------------------------------\n");		//separates each row of the 3x3 sub grids
			}
			
			for(int y = 0; y < gridSize; y++)
			{
				if(y % subGridSize == 0 && y != 0)
				{
					System.out.print(" | ");		//separates each column of the 3x3 sub grids
				}				
				System.out.print(" " + sudokuGrid[x][y] + " ");
				
			}	
			System.out.println();
			
		}
		System.out.println("\n_________________________________\n\n");
	}
	
	public static void userInput() throws Exception		//method for the use to input the values of the unsolved sudoku using keyboard into the sudoku solver
	{
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("");
		
		for(int x = 0; x < gridSize; x++)
		{
			System.out.println("Please enter the number " + (x+1) + " row of the unsolved sudoku\nBe aware that for unknown values please enter 0:");
			
			for(int y = 0; y < gridSize; y++)
			{
				sudokuGrid[x][y] = keyboard.nextInt();		//user input for each row of 9 numbers of the sudoku
			}
		}
		
		keyboard.close();
		
		System.out.println("This is the unsolved sudoku that you have entered:");
		
		displaySudoku();		//displays the unsolved sudoku back to the user
		
		if(solveSudoku())		//if the suodku if solved by the method, the it saves the solved suodku to the file and displays the solved sudoku back to the user
		{
			fileOutput();
			
			System.out.println("This is the solved sudoku, this is\nalso printed in solvedsudoku.txt");
			
			displaySudoku();
			
			System.out.println("Thank you for using sudoku solver, have a nice day.");
		}
		
		else
		{
			System.out.println("An error has occured, please go back and check if\nall the numbers have been propertly entered");
		}
	}
	
	public static void fileInput() throws Exception		//method for the use to input the values of the unsolved sudoku using file input into the sudoku solver
	{
		Scanner file = new Scanner(unsolvedSudokuFile);
	
		for(int x = 0; x < gridSize; x++)
		{
			for(int y = 0; y < gridSize; y++)
			{
				sudokuGrid[x][y] = file.nextInt();		//file input of the unsolved sudoku
			}
		}
		
		file.close();
		
		System.out.println("\nThis is the unsolved sudoku detected\nin unsolvedsudoku.txt:");
		
		displaySudoku();		//displays the unsolved sudoku back to the user
		
		if(solveSudoku())		//if the suodku if solved by the method, the it saves the solved suodku to the file and displays the solved sudoku back to the user
		{
			fileOutput();
			
			System.out.println("This is the solved sudoku, this is\nalso printed in solvedsudoku.txt");
			
			displaySudoku();
			
			System.out.println("Thank you for using sudoku solver, have a nice day.");
		}
		
		else
		{
			System.out.println("An error has occured, please go back and check if\nall the numbers have been propertly entered");
		}
	}
	
	public static void fileOutput() throws Exception		//method for printing the solved sudoku onto a separate file
	{
		PrintWriter output = new PrintWriter(solvedSudokuFile);		//prints the solved sudoku to solvedsudoku.txt
		
		for(int x = 0; x < gridSize; x++)
		{
			if(x != 0)
			{
				output.println("");
			}
			
			for(int y = 0; y < gridSize; y++)
			{
				if(y != 0)
				{
					output.print(" ");
				}
				
				output.print(sudokuGrid[x][y]);
			}
		}
		
		output.close();
	}
	
	public static int menuErrorTrapping(int min, int max)		//error trap method for the menu input
	{
		Scanner menuInput = new Scanner(System.in);
		
		int number = 0;
		
		boolean inputGood = true;
		
		do{
			inputGood = true;
			
			try 
			{
				number = menuInput.nextInt();
			}
			
			catch (Exception e)
			{
				inputGood = false;
				
				String garbage = menuInput.nextLine();
			}
			
			if (number < min || number > max)		//if input is not 1, 2, or 3, then the method does not accept the user input value
			{
				System.out.println("Program does not recognize this option,\nplease try again:");
				
				inputGood = false;
			}
			
		}while(!inputGood);
		
		return number;
	}
	
}
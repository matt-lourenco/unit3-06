/****************************************************************************
 *
 * Created by: Matthew lourenco
 * Created on: May 2018
 * This program simulates a game of Tic Tac Toe
 *
 ****************************************************************************/

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {
	
	private enum Shapes {
		VOID,
		O,
		X;
	}
	
	/** This is a grid of shapes used in a Tic Tac Toe game. The computer is
	 * O and the user is X. x is vertical and positive in the downward
	 * direction. y is horizontal and positive in the right direction. */
	Shapes[][] grid = new Shapes[3][3];
	
	
	/** This array keeps track of the chances to win when choosing
	 *  Each tile on the grid. */
	int[][] winChances = new int[3][3];
	
	
	Game() throws Exception {
		//Default constructor
		
		//Fill grid with void
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				grid[row][column] = Shapes.VOID;
			}
		}
	}
	
	public void playGame() throws Exception {
		//Play a game of Tic Tac Toe
		
		printGrid();
		placeShape();
		printGrid();
		
		while(findWinner() == Shapes.VOID) {
			
			startGen(cloneGrid(grid));
			choosePlay();
			printGrid();
			placeShape();
		}
		
	}
	
	private Shapes[][] cloneGrid(Shapes[][] grid) {
		//Create a clone of the inputted grid
		
		Shapes[][] gridClone = new Shapes[3][3];
		
		for(int row = 0; row < gridClone.length; row++) {
			for(int tile = 0; tile < gridClone[row].length; tile++) {
				gridClone[row][tile] = grid[row][tile];
			}
		}
		
		return gridClone;
	}
	
	private void placeShape() throws Exception {
		//Ask the user where they want to place a shape and try to place it.
		BufferedReader reader = new BufferedReader(new InputStreamReader
				(System.in));
		String input = "";
		int x = -1;
		int y = -1;
		
		while(true) {
			System.out.println("Input coordinates of your next shape:"
					+ " (Two numbers separated by a space)");
			
			//Get user input
			input = reader.readLine();
			
			if(input.length() == 3) {
				x = Integer.parseInt(input.substring(0, 1));
				y = Integer.parseInt(input.substring(2, 3));
				
				if(x >= 0 && x < 3 && y >= 0 && y < 3) {
					//Edit coordinate input to conform to setup of grid
					if(grid[-1*y + 2][x].equals(Shapes.VOID)) {
						break;
					} else {
						System.out.println("That square is already filled");
					}
				} else {
					System.out.println("Enter integers between 0 and 2");
				}
			} else {
				System.out.println("Enter two numbers separated by a space");
			}
		}
		
		//Edit coordinate input to conform to setup of grid
		grid[-1*y + 2][x] = Shapes.X;
	}
	
	private Shapes findWinner() {
		//Finds the winner on the board
		
		if(grid[0][0].equals(grid[0][1]) &&
				grid[0][0].equals(grid[0][2]) &&
				!grid[0][0].equals(Shapes.VOID)) {
			return grid[0][0];
		} else if(grid[1][0].equals(grid[1][1]) &&
				grid[1][0].equals(grid[1][2]) &&
				!grid[1][0].equals(Shapes.VOID)) {
			return grid[1][0];
		} else if(grid[2][0].equals(grid[2][1]) &&
				grid[2][0].equals(grid[2][2]) &&
				!grid[2][0].equals(Shapes.VOID)) {
			return grid[2][0];
		} else if(grid[0][0].equals(grid[1][0]) &&
				grid[0][0].equals(grid[2][0]) &&
				!grid[0][0].equals(Shapes.VOID)) {
			return grid[0][0];
		} else if(grid[0][1].equals(grid[1][1]) &&
				grid[0][1].equals(grid[2][1]) &&
				!grid[0][1].equals(Shapes.VOID)) {
			return grid[0][1];
		} else if(grid[0][2].equals(grid[1][2]) &&
				grid[0][2].equals(grid[2][2]) &&
				!grid[0][2].equals(Shapes.VOID)) {
			return grid[0][2];
		} else if(grid[0][0].equals(grid[1][1]) &&
				grid[0][0].equals(grid[2][2]) &&
				!grid[0][0].equals(Shapes.VOID)) {
			return grid[0][0];
		} else if(grid[0][2].equals(grid[1][1]) &&
				grid[0][2].equals(grid[2][0]) &&
				!grid[0][2].equals(Shapes.VOID)) {
			return grid[0][2];
		} else {
			return Shapes.VOID;
		}
	}
	
	private void printGrid() {
		//Prints the grid
		System.out.println("+---+---+---+");
		
		for(int row = 0; row < grid.length; row++) {
			System.out.print("| ");
			for(Shapes shape: grid[row]) {
				if(shape.equals(Shapes.VOID)) { //Print a space if blank
					System.out.print("  | ");
				} else {
					System.out.print(shape + " | ");
				}
			}
			System.out.println("\n+---+---+---+");
		}
	}
	
	private void startGen(Shapes[][] grid) {
		//Start the recursive generation every possible state of the board
		
		for(int row = 0; row < grid.length; row++) {
			for(int tile = 0; tile < grid[row].length; tile++) {
				
				winChances[row][tile] = 0; //Return win chanes to zero
				
				if(grid[row][tile].equals(Shapes.VOID)) {
					grid[row][tile] = Shapes.O;
					genPossibilities(cloneGrid(grid), false, row, tile);
				}
			}
		}
	}
	
	private void genPossibilities(Shapes[][] grid,
				boolean cpuTurn, int originalX, int originalY) {
		//Recursively generate every state of the board
		
		if(findWinner().equals(Shapes.X)) {
			winChances[originalX][originalY]--;
			return;
		} else if(findWinner().equals(Shapes.O)) {
			winChances[originalX][originalY]++;
			return;
		}
		
		for(int row = 0; row < grid.length; row++) {
			for(int tile = 0; tile < grid[row].length; tile++) {
				if(grid[row][tile].equals(Shapes.VOID) && cpuTurn) {
					grid[row][tile] = Shapes.O;
					genPossibilities(cloneGrid(grid), !cpuTurn, originalX, originalY);
				} else if(grid[row][tile].equals(Shapes.VOID) && !cpuTurn) {
					grid[row][tile] = Shapes.X;
					genPossibilities(cloneGrid(grid), !cpuTurn, originalX, originalY);
				}
			}
		}
	}
	
	private void choosePlay() {
		//Chooses the best plac to place an O considering the chances
		
		int[] highestChance = new int[2];
		
		for(int row = 0; row < winChances.length; row++) {
			for(int tile = 0; tile < winChances[row].length; tile++) {
				if(winChances[row][tile] >
				winChances[highestChance[0]][highestChance[1]]) {
					highestChance[0] = row;
					highestChance[1] = tile;
				}
			}
		}
		
		grid[highestChance[0]][highestChance[1]] = Shapes.O;
	}
	
	public static void main(String[] args) throws Exception {
		//Instantiate a game
		
		Game game = new Game();
		game.playGame();
	}
}
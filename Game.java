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
	
	public static void main(String[] args) throws Exception {
		//Instantiate a game
		
		Game game = new Game();
		game.playGame();
	}
}
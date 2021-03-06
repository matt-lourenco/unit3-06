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
		VOID (0),
		O (-1),
		X (1);
		
		int score;
		
		Shapes(int inScore) {
			//Sets the score related to each shape
			score = inScore;
		}
		
		private int getScore() { return score; } //Getter
	}
	
	/** This is a grid of shapes used in a Tic Tac Toe game. The computer is
	 * O and the user is X. x is vertical and positive in the downward
	 * direction. y is horizontal and positive in the right direction. */
	Shapes[][] grid = new Shapes[3][3];
	
	
	/** This array keeps track of the chances to win when choosing
	 *  Each tile on the grid.*/
	int[][] winChances = new int[3][3];
	
	
	Game() throws Exception {
		//Default constructor
		
		//Fill grid with void and chances with 0
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				grid[row][column] = Shapes.VOID;
				winChances[row][column] = 0;
			}
		}
	}
	
	public void playGame() throws Exception {
		//Play a game of Tic Tac Toe
		
		printGrid(grid);
		placeShape();
		
		int filledTiles = 1;
		boolean cpuTurn = true;
		
		while(findWinner(grid) == Shapes.VOID && filledTiles < 9) {
			
			if(cpuTurn) {
				startGen(cloneGrid(grid));
				choosePlay();
			} else {
				printGrid(grid);
				placeShape();
			}
			filledTiles++;
			cpuTurn = !cpuTurn;
		}
		
		printGrid(grid);
		
		if(findWinner(grid) == Shapes.VOID) {
			System.out.println("You tied!");
		} else if(findWinner(grid) == Shapes.O) {
			System.out.println("You lost. Too bad.");
		} else {
			System.out.println("You wont ever see this because it's"
					+ " impossible to win but hey, congrats.");
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
		int x = 0;
		int y = 0;
		
		while(true) {
			System.out.println("Input coordinates of your next shape:"
					+ " (Two separated numbers)");
			
			//Get user input
			input = reader.readLine();
			
			if(input.length() == 3) {
				try {
					x = Integer.parseInt(input.substring(0, 1));
					y = Integer.parseInt(input.substring(2, 3));
					
					if(x > 0 && x < 4 && y > 0 && y < 4) {
						//Edit coordinate input to conform to setup of grid
						if(grid[-1*y + 3][x - 1] == Shapes.VOID) {
							break;
						} else {
							System.out.println("That square is already filled");
						}
					} else {
						System.out.println("Enter integers between 1 and 3");
					}
				} catch(NumberFormatException notInt) {}
			} else {
				System.out.println("Enter two separated numbers");
			}
		}
		
		//Edit coordinate input to conform to setup of grid
		grid[-1*y + 3][x - 1] = Shapes.X;
	}
	
	private Shapes findWinner(Shapes[][] grid) {
		//Finds the winner on the board
		
		if(grid[0][0] == grid[0][1] && grid[0][0] == grid[0][2] && grid[0][0] != Shapes.VOID) {
			return grid[0][0];
		} else if(grid[1][0] == grid[1][1] && grid[1][0] == grid[1][2] && grid[1][0] != Shapes.VOID) {
			return grid[1][0];
		} else if(grid[2][0] == grid[2][1] && grid[2][0] == grid[2][2] && grid[2][0] != Shapes.VOID) {
			return grid[2][0];
		} else if(grid[0][0] == grid[1][0] && grid[0][0] == grid[2][0] && grid[0][0] != Shapes.VOID) {
			return grid[0][0];
		} else if(grid[0][1] == grid[1][1] && grid[0][1] == grid[2][1] && grid[0][1] != Shapes.VOID) {
			return grid[0][1];
		} else if(grid[0][2] == grid[1][2] && grid[0][2] == grid[2][2] && grid[0][2] != Shapes.VOID) {
			return grid[0][2];
		} else if(grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2] && grid[0][0] != Shapes.VOID) {
			return grid[0][0];
		} else if(grid[0][2] == grid[1][1] && grid[0][2] == grid[2][0] && grid[0][2] != Shapes.VOID) {
			return grid[0][2];
		} else {
			return Shapes.VOID;
		}
	}
	
	private boolean userClose(Shapes[][] grid) {
		//Finds if the winner a turn away from winning
		
		if(grid[0][0].getScore() + grid[0][1].getScore() + grid[0][2].getScore() == 2) {
			return true;
		} else if(grid[1][0].getScore() + grid[1][1].getScore() + grid[1][2].getScore() == 2) {
			return true;
		} else if(grid[2][0].getScore() + grid[2][1].getScore() + grid[2][2].getScore() == 2) {
			return true;
		} else if(grid[0][0].getScore() + grid[1][0].getScore() + grid[2][0].getScore() == 2) {
			return true;
		} else if(grid[0][1].getScore() + grid[1][1].getScore() + grid[2][1].getScore() == 2) {
			return true;
		} else if(grid[0][2].getScore() + grid[1][2].getScore() + grid[2][2].getScore() == 2) {
			return true;
		} else if(grid[0][0].getScore() + grid[1][1].getScore() + grid[2][2].getScore() == 2) {
			return true;
		} else if(grid[0][2].getScore() + grid[1][1].getScore() + grid[2][0].getScore() == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	private void printGrid(Shapes[][] grid) {
		//Prints the grid
		System.out.println("+---+---+---+");
		
		for(int row = 0; row < grid.length; row++) {
			System.out.print("| ");
			for(Shapes shape: grid[row]) {
				if(shape == Shapes.VOID) { //Print a space if blank
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
				
				winChances[row][tile] = 0; //Return win chances to zero
				
				if(grid[row][tile] == Shapes.VOID) {
					Shapes[][] gridClone = cloneGrid(grid);
					gridClone[row][tile] = Shapes.O;
					genPossibilities(gridClone, 2, false, row, tile);
				}
			}
		}
	}
	
	private void genPossibilities(Shapes[][] grid, int tilesFilled,
				boolean cpuTurn, int originalX, int originalY) {
		//Recursively generate every state of the board
		
		//Check if a winner was decided
		if(tilesFilled == 9 && findWinner(grid) == Shapes.VOID) {
			return;
		}
		//return if cpu wins
		if(findWinner(grid) == Shapes.O) {
			return;
		}
		//reduced win chances if user wins
		if(findWinner(grid) == Shapes.X) {
			winChances[originalX][originalY] -= 9 - tilesFilled;
			return;
		}
		//prioritize blocking the user
		if(userClose(grid) && cpuTurn) {
			winChances[originalX][originalY] += 9 - tilesFilled;
		}
		
		//If no winner has been decided, clone the grid and check the next move
		for(int row = 0; row < grid.length; row++) {
			for(int tile = 0; tile < grid[row].length; tile++) {
				if(grid[row][tile] == Shapes.VOID) {
					
					Shapes[][] gridClone = cloneGrid(grid);
					if(cpuTurn) {
						gridClone[row][tile] = Shapes.O;
					} else {
						gridClone[row][tile] = Shapes.X;
					}
					
					genPossibilities(gridClone, tilesFilled + 1,
							!cpuTurn, originalX, originalY);
				}
			}
		}
	}
	
	private void choosePlay() {
		//Chooses the play that has the highest chance of success
		
		int[] chance = new int[2];
		
		//Find the best possible move
		for(int row = 0; row < winChances.length; row++) {
			for(int tile = 0; tile < winChances[row].length; tile++) {
				//Find a valid empty space to start checking
				if(grid[chance[0]][chance[1]] != Shapes.VOID) {
					chance[0] = row;
					chance[1] = tile;
				}
				if(grid[row][tile] == Shapes.VOID && 
						winChances[row][tile] > winChances[chance[0]][chance[1]]) {
					chance[0] = row;
					chance[1] = tile;
				}
			}
		}
		
		//Place a shape
		if(grid[chance[0]][chance[1]] == Shapes.VOID) {
			grid[chance[0]][chance[1]] = Shapes.O;
		}
	}
	
	public static void main(String[] args) throws Exception {
		//Instantiate a game
		
		Game game = new Game();
		game.playGame();
	}
}
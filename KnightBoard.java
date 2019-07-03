/**
 * Represents a chess board that the knight can play on which is 
 * a 2d array of n*n elements. 
 * @author Lucas
 *
 */
public class KnightBoard {
	private int n = 10;
	private int[][] board;
	private int startLocationX;
	private int startLocationY;
	private int heuristic;
	
	/**
	 * Constructs a board checking that user entered correct values and defaults otherwise.
	 * @param size (Size > 2)
	 */
	public KnightBoard(int x, int y, int size, int searchAlgorithm) {
		
		startLocationX = x;
		startLocationY = y;
		n = size;
		heuristic = searchAlgorithm;
		
		// initialize board with -1 values so we have something to check
		board = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = -1;
			}
		}
		
		// check that the user entered the right values
		if(!Position.isValid(x, y, size, board)) {
			throw new NumberFormatException();
		}
		
		board[startLocationX][startLocationY] = 1;
	}
	
	/**
	 * Recursive method to solve and display the solved knight tour to the console.
	 */
	public void solveKnightTour() {
		StringBuilder string = new StringBuilder();
		boolean result = !Position.solveKnightTour(startLocationX, startLocationY, n, board, heuristic);
		string.append("java KnightTour " + heuristic + " " + n + " " + startLocationX + " " + startLocationY + " " + "\n");
		string.append("The total number of moves is " + Position.getTotalMoves() + "\n");
		if(result) {
			string.append("No solution found!\n");
		}else {
			for (int z = 0; z < n; z++) {
				for (int j = 0; j < n; j++) {
					string.append(board[z][j] + ",");
				}
				string.deleteCharAt(string.length()-1);
				string.append("\n");
			}
		}
		System.out.println(string);
	}
}

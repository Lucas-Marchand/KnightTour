/**
 * Utility class to determine solutions for the KnightTour Chess board
 * 
 * @author Lucas
 *
 */
public class Position {

	private static int move = 1;
	private static int totalMoves = 1;

	/**
	 * Recursive algorithm to move around the sol array
	 * 
	 * @param x   position
	 * @param y   position
	 * @param n   size of array
	 * @param sol 2d array of moves
	 * @return true if a solution was found otherwise false
	 */
	public static boolean solveKnightTour(int x, int y, int n, int[][] sol, int heuristic) {
		int[] xMoves = { 1, 2, 2, 1, -1, -2, -2, -1 };
		int[] yMoves = { -2, -1, 1, 2, 2, 1, -1, -2 };

		// check for true condition to end recursion
		if (move == n * n) {
			return true;
		}

		// choose a heuristic if thats that case
		if (heuristic == 1) {
			useHeuristicI(x, y, n, xMoves, yMoves, sol);
		} else if (heuristic == 2) {
			useHeuristicII(x, y, n, xMoves, yMoves, sol);
		}

		// loop through all of the possible moves in clockwise sequential order.
		for (int i = 0; i < 8; i++) {
			int nextX = x + xMoves[i];
			int nextY = y + yMoves[i];
			// only take it if it is valid
			if (isValid(nextX, nextY, n, sol)) {
				sol[nextY][nextX] = ++move;
				++totalMoves;
				// recursively move through all the tours.
				if (solveKnightTour(nextX, nextY, n, sol, heuristic))
					return true;
				else {
					sol[nextY][nextX] = -1;// backtracking
					move--;
				}
			}
		}
		return false;
	}

	/**
	 * Method to rearrange the knights move search sequence to choose the move that
	 * is closest to the border of the chess board.
	 * 
	 * @param x      location of the knight
	 * @param y      location of the knight
	 * @param n      size of the board
	 * @param xMoves array of sequential x axis moves for the knight
	 * @param yMoves array of sequential y axis moves for the knight
	 * @param sol    solution matrix of the board.
	 */
	public static void useHeuristicI(int x, int y, int n, int[] xMoves, int[] yMoves, int[][] sol) {
		// find out which index is the closest to the border
		int minDist = n;
		int minIndex = n;
		
		for (int i = 0; i < 8; i++) { // loop through all possible next moves
			int nextX = x + xMoves[i];
			int nextY = y + yMoves[i];
			if (isValid(nextX, nextY, n, sol)) {
				
				int left = n-1-nextX;
				int right = nextX;
				int bottom = n-1-nextY;
				int top = nextY;
				
				// TODO: need to find the best way to decide what is closest
				if(left+top < minDist) {
					minDist = left+top;
					minIndex = i;
				}
				if(left+bottom < minDist) {
					minDist = left+bottom;
					minIndex = i;
				}
				if(right+top < minDist) {
					minDist = right+top;
					minIndex = i;
				}
				if(right+bottom < minDist) {
					minDist = right+bottom;
					minIndex = i;
				}
			}
		}

		// Rearrange sequence to look at closest border move first
		// then continue with the clockwise search sequence.
		int swapX = xMoves[minIndex];
		int swapY = yMoves[minIndex];

		for (int i = minIndex; i > 0; i--) {
			xMoves[i] = xMoves[i - 1];
			yMoves[i] = yMoves[i - 1];
		}

		xMoves[0] = swapX;
		yMoves[0] = swapY;
	}

	/**
	 * Method to rearrange the knights move search sequence to choose the move that
	 * will have a minimal number of onward moves.
	 * 
	 * @param x      location of the knight
	 * @param y      location of the knight
	 * @param n      size of the board
	 * @param xMoves array of sequential x axis moves for the knight
	 * @param yMoves array of sequential y axis moves for the knight
	 * @param sol    solution matrix of the board.
	 */
	public static void useHeuristicII(int x, int y, int n, int[] xMoves, int[] yMoves, int[][] sol) {
		// find out which move has the minimal onward moves
		int minDegree = xMoves.length;
		int minIndex = n;
		for (int i = 0; i < xMoves.length; i++) { // loop through all possible next moves
			int nextX = x + xMoves[i];
			int nextY = y + yMoves[i];
			if (isValid(nextX, nextY, n, sol)) { // only look at moves that are valid next moves
				int degree = getDegree(nextX, nextY, n, xMoves, yMoves, sol);
				if (degree < minDegree) { // find the index of the minimal degree.
					minDegree = degree;
					minIndex = i;
				}
			}
		}

		if (minDegree != 0 || minDegree != 8) {
			// Rearrange sequence to make the move with the minimal degree
			// then continue with the clockwise search sequence.
			int swapX = xMoves[minIndex];
			int swapY = yMoves[minIndex];

			for (int i = minIndex; i > 0; i--) {
				xMoves[i] = xMoves[i - 1];
				yMoves[i] = yMoves[i - 1];
			}

			xMoves[0] = swapX;
			yMoves[0] = swapY;
		}
	}

	/**
	 * Gets the degree of moves from location (x,y)
	 * 
	 * @param x   position
	 * @param y   position
	 * @param n   size of board
	 * @param sol solution matrix
	 * @return degree of onward moves from (x,y), 0 if no moves were found.
	 */
	private static int getDegree(int x, int y, int n, int[] xMoves, int[] yMoves, int[][] sol) {
		int degree = 0;
		for (int i = 0; i < xMoves.length; i++) { // loop through all possible next moves
			int nextX = x + xMoves[i];
			int nextY = y + yMoves[i];
			if (isValid(nextX, nextY, n, sol)) { // only look at moves that are valid next moves
				degree++;
			}
		}
		return degree;
	}
	
	/**
	 * Getter for the number of moves made
	 * 
	 * @return the current move
	 */
	public static int getTotalMoves() {
		return totalMoves;
	}

	/**
	 * Utility method to check that the next move is a valid move on the chess board
	 * with size n
	 * 
	 * @param x   next position index
	 * @param y   next position index
	 * @param n   size of board
	 * @param sol the chess board itself
	 * @return true if move is on the chess board else false
	 */
	public static boolean isValid(int x, int y, int n, int[][] sol) {
		if (y >= 0 && x >= 0 && x < n && y < n && sol[y][x] == -1) {
			return true;
		} else {
			return false;
		}
	}
}

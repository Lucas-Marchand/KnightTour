/**
 * Knight tour is a driver class that represents the knight tour problem
 * which consists of an n*n board where n>2 and the knight can start from
 * anywhere on the board and must adhere to the chess knights 1x2 and 2x1
 *
 * @author Lucas
 *
 */
public class KnightTour {
	/**
	 * Entry for program.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try { // Get board size
			int heuristic = Integer.parseInt(args[0]);
			int n = Integer.parseInt(args[1]);
			int x = Integer.parseInt(args[2]);
			int y = Integer.parseInt(args[3]);
			
			KnightBoard board = new KnightBoard(x,y,n,heuristic);
			
			board.solveKnightTour();
			
		}catch(NumberFormatException e){
			System.out.println("Usage: java KnightTour <0/1/2 (no/heuristicI/heuristicII search)> <n> <x> <y>");
		}
	}
}

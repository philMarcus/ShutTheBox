import java.util.ArrayList;

public class Game {

	final static int MAX = 9; // the game consists of "closing" numbers 1 - 9.
	final String[] OPEN = { "*", "*", "*", "*", "*", "*", "*", "*", "*" };
	final String[] CLOSED = { "N", "A", "N", "T", "U", "C", "K", "E", "T" };
	final int numDice = 2;
	final int diceSides = 6;

	// the game board (each element starts "false", game is won if all are "true"
	private boolean[] board;
	private ArrayList<boolean[]> options;
	public static final ArrayList<ArrayList<boolean[]>> ROLL_OPTS= new ArrayList<ArrayList<boolean[]>>();

	public Game() {
		board = new boolean[MAX];

		for (int i = 0; i<= numDice*diceSides;i++) {
			ROLL_OPTS.add(getRollOptions(i));
		}
	}

	public void removeOptionsWithout(int num) {
		for (int i = 0; i < options.size(); i++) {
			boolean check = false;
			// check to see if "num" occurs in the option
			for (int j = 1; j <= MAX; j++) {
				if (options.get(i)[j - 1])
					if (j == num)
						check = true;
			}
			// remove option if check is still false
			if (!check) {
				options.remove(i);
				i--;
			}
		}
	}
	
	// eliminate options that don't contain the digit(s) closest to num found across all
	// options. (Tie goes to highest (b/c "<=" and index increases)
	public void selectClosestTo(int num) {
		int close=99;
		// TODO
		for (boolean[] o : options) {
			for (int i = 1; i <= MAX; i++) {
				if (o[i - 1])
					if (Math.abs(num - i) <= Math.abs(num - close))
						close = i;
			}
		}
		// remove options that don't contain "close"
		removeOptionsWithout(close);
	}

	

	public boolean[] makeChoice(String strategy) {
		selectClosestTo(9);
		int r = (int) (Math.random() * options.size());
		return options.get(r);
	}

	public boolean isLegal(boolean[] opt, int roll) {
		int sum = 0;
		for (int n = 1; n <= MAX; n++) {
			if (opt[n - 1]) {
				if (isShut(n))
					return false; // illegal if the number is shut
				else
					sum += n; // add n to sum if it is part of the option
			}
		}
		return (sum == roll); // the choice is legal if all options add up to the roll
	}

	public void setOptions(int roll) {
		ArrayList<boolean[]> opts = new ArrayList<>();
		for (int i = 0; i < Math.pow(2, MAX); i++) {
			boolean[] opt = new boolean[MAX];
			int digits = i;
			for (int n = 0; n < MAX; n++) {
				opt[n] = (digits % 2 == 0);
				digits /= 2;
			}
			// check to see if option is legal before adding
			if (isLegal(opt, roll))
				opts.add(opt);
		}
		options = opts;
	}
	
	public static ArrayList<boolean[]> getRollOptions(int roll){
		ArrayList<boolean[]> opts = new ArrayList<>();
		for (int i = 0; i < Math.pow(2, MAX); i++) {
			boolean[] opt = new boolean[MAX];
			int digits = i;
			for (int n = 0; n < MAX; n++) {
				opt[n] = (digits % 2 == 0);
				digits /= 2;
			}
			// find sum of nums in option
			int sum = 0;
			for (int n = 1; n <= MAX; n++) {
				if (opt[n - 1]) {
					sum += n; // add n to sum if it is part of the option
				}
			}
			if(sum == roll) // add if the nums add up to roll
				opts.add(opt);
		}
		return opts;
	}

	public boolean isShut(int num) {
		return board[num - 1];
	}


	public void updateBoard(boolean[] choice) {
		board = getProspectiveBoard(choice);
	}
	
	public boolean[] getProspectiveBoard(boolean[] option) {
		boolean[] brd = new boolean[MAX];
		for(int i=0;i<MAX;i++) {
			brd[i] = board[i] || option[i];
		}
		return brd;
	}

	public boolean isWin() {
		for (boolean n : board) {
			if (!n)
				return false;
		}
		return true;
	}

	public int getNumOpts() {
		return options.size();
	}

	public void reset() {
		board = new boolean[MAX];
	}
	// rolls an x-sided die
	// returns a random int between 1 and sides
	public static int rollDie(int sides) {
		return (int) (Math.random() * sides + 1);
	}

	// rolls any number of x-sided dice
	public static int rollDice(int sides, int num) {
		int sum = 0;
		// This calls the rollDie method, num times.
		for (int i = 0; i < num; i++) {
			sum += rollDie(sides);
		}
		return sum;
	}

	public void printBoard() {
		for (int i = 0; i < MAX; i++) {
			if (board[i])
				System.out.print(CLOSED[i]);
			else
				System.out.print(OPEN[i]);

		}
		System.out.println();
	}

	public void printOption(boolean[] opt) {
		for (int i = 1; i <= MAX; i++) {
			if (opt[i - 1])
				System.out.print(i + " ");
		}
		System.out.println();
	}

	public void printOptions() {
		for (boolean[] o : options) {
			printOption(o);
		}
		System.out.println();
	}

}

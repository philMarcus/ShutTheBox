import java.util.ArrayList;

public class Game {

	final int MAX = 9; // the game consists of "closing" numbers 1 - 9.
	final String[] OPEN = { "*", "*", "*", "*", "*", "*", "*", "*", "*" };
	final String[] CLOSED = { "N", "A", "N", "T", "U", "C", "K", "E", "T" };

	// the game board (each element starts "false", game is won if all are "true"
	private boolean[] board;
	private ArrayList<boolean[]> options;

	public Game() {
		board = new boolean[MAX];
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
	
	// eliminate options that don't contain the digit(s) closest to 7 found across all
	// options
	public void selectClosestToSeven() {
		int high = 1;
		// find the highest number across all options
		for (boolean[] o : options) {
			for (int i = 1; i <= MAX; i++) {
				if (o[i - 1])
					if (i > high)
						high = i;
			}
		}
		// remove options that don't contain "high"
		removeOptionsWithout(high);
	}

	// eliminate options that don't contain the highest number found across all
	// options
	public void selectHighest() {
		int high = 1;
		// find the highest number across all options
		for (boolean[] o : options) {
			for (int i = 1; i <= MAX; i++) {
				if (o[i - 1])
					if (i > high)
						high = i;
			}
		}
		// remove options that don't contain "high"
		removeOptionsWithout(high);
	}

	// eliminate options that don't contain the lowest number found across all
	// options
	public void selectLowest() {
		int low = MAX;
		// find the highest number across all options
		for (boolean[] o : options) {
			for (int i = 1; i <= MAX; i++) {
				if (o[i - 1])
					if (i < low)
						low = i;
			}
		}
		// remove options that don't contain "low"
		removeOptionsWithout(low);
	}

	public boolean[] makeChoice(String strategy) {
		selectHighest();
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

	public boolean isShut(int num) {
		return board[num - 1];
	}

//	public void shut(int num) {
//		board[num - 1] = true;
//	}

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

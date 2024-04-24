import java.util.ArrayList;

public class ShutTheBoxTester {

	public static void main(String[] args) {
		Game g = new Game();
		int wins = 0;
		final int GAMES = 100000;
		for (int i = 1; i <= GAMES; i++) {
			g.reset();
			g.setOptions(Game.rollDice(6, 2));
			while (g.getNumOpts() > 0 && !g.isWin()) {
				//g.printOptions();
				boolean[] choice = g.makeChoice("");
				//g.printOption(choice);
				g.updateBoard(choice);
				g.printBoard();
				g.setOptions(Game.rollDice(6, 2));
			}
			if (g.isWin())
				wins++;
			System.out.println(wins + " / " + i);
		}
	}

}

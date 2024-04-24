import java.util.Comparator;

//This comparator is used to sort available options for the ShutTheBox game.
//The highest scoring options will be the ones that, when added to the current
//board, leave the most total 6,7,and 8 options remaining. Ties will be broken by looking
//at the number of 7-options remaining.
public class ComboComparator implements Comparator<boolean[]> {
	boolean[] currentBoard;
	
	public ComboComparator(boolean[] currentBoard){
		super();
		this.currentBoard = currentBoard;
	}
	
	private int getScore(boolean[] opt, int[] combos[]) {
		
		return 0;
	}

	@Override
	public int compare(boolean[] o1, boolean[] o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}

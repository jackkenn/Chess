package bots;

import java.util.Comparator;
import java.util.Random;

import game.Board;
import game.GameLoop;
import game.Player;

public abstract class AI {
	public GameLoop game;
	protected Board board;
	public Player self;
	protected Player opp;
	protected Random rand;
	protected Long seed;
	
	public int weightListIndex; //not needed

	public AI(GameLoop game, boolean isWhite, Long seed) {
		this.game = game;
		this.board = game.getBoard();
		this.seed = seed;
		if (isWhite) {
			self = board.white;
		} else {
			self = board.black;
		}
		opp = self.opponent;
		rand = new Random(seed);
	}

	public long getSeed() {
		return seed;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}

	public void next() {
		rand.nextInt();
	}

	public abstract boolean move();

	
	//cant remember which direction it sorts in
	public void sort(Object[] toSort, Comparator<Object> comp) throws NullPointerException {
		quickSortRec(toSort, comp, 0, toSort.length - 1);
	}
	
	public void sort(Object[] toSort, Comparator<Object> comp, int endIndex) throws NullPointerException {
		quickSortRec(toSort, comp, 0, endIndex);
	}

	private void quickSortRec(Object[] list, Comparator<Object> comp, int start, int end) {
		if (start < end) {
			int i = partition(list, comp, start, end);
			quickSortRec(list, comp, start, i - 1);
			quickSortRec(list, comp, i + 1, end);
		}
	}

	private int partition(Object[] list, Comparator<Object> comp, int start, int end) {
		Object pivot = list[end];
		int i = start - 1;
		for (int j = start; j < end; j++) {
			if (comp.compare(list[j], pivot) <= 0) {
				i++;
				Object temp = list[i];
				list[i] = list[j];
				list[j] = list[i];
			}
		}
		Object temp = list[i + 1];
		list[i + 1] = list[end];
		list[end] = list[i + 1];
		return i + 1;
	}

}

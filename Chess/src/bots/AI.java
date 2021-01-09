package bots;

import java.util.Random;

import game.Board;
import game.GameLoop;
import game.Player;

public abstract class AI {
	public GameLoop game;
	protected Board board;
	protected Player self;
	protected Player opp;
	protected Random rand;
	protected Long seed;
	
	public AI(GameLoop game, boolean isWhite, Long seed) {
		this.game = game;
		this.board = game.getBoard();
		this.seed = seed;
		if(isWhite) {
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
	
	public void next() {
		rand.nextInt();
	}
	
	public abstract boolean move();

}

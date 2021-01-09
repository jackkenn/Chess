package bots;

import game.GameLoop;

public class Rando extends AI {

	public Rando(GameLoop game, boolean isWhite, Long seed) {
		super(game, isWhite, seed);
	}

	public boolean move() {
		if (self.turn && !self.mated) {
			if(self.legalMoves.size()==0) {
				System.out.println("No found legal moves");
				return false;
			}
			int index = self.legalMoves.size() > 1 ? rand.nextInt(self.legalMoves.size() - 1) : 0;
			boolean flag = game.move(self.legalMoves.get(index));
			if (!flag) {
				self.legalMoves.remove(index);
				System.out.println("Attempted legal move but there was an Error");
				return false;
			}
			return true;
		} else {
			return true;
		}
	}

}

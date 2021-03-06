package bots;

import game.GameLoop;

public class Rando extends AI {

	public Rando(GameLoop game, boolean isWhite, Long seed) {
		super(game, isWhite, seed);
	}

	public boolean move() {
		if (self.mated || self.opponent.mated) {
			return true;
		}
		if (self.turn) {
			Long delta = System.currentTimeMillis();
			int index = self.legalMoves.size() > 1 ? rand.nextInt(self.legalMoves.size() - 1) : 0;
			while (self.legalMoves.size() > 0
					? (!self.isLegalMove(self.legalMoves.get(index)[0], self.legalMoves.get(index)[1]))
					: false) {
				self.legalMoves.remove(index);
				index = self.legalMoves.size() > 1 ? rand.nextInt(self.legalMoves.size() - 1) : 0;
			}
			if (self.legalMoves.size() == 0) {
				System.out.println(self.toString() + " Found no legal moves");
				return true;
			}
			boolean flag = game.move(self.legalMoves.get(index));
			if (!flag) {
				System.out.println(self.toString() + "Attempted legal move but there was an Error");
				return false;
			}
			System.out.println("Calculation time taken: " + ((System.currentTimeMillis() - delta) / 1000.0));
			return true;
		} else {
			return true;
		}
	}

}

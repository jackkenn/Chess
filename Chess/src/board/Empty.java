package board;

import java.util.ArrayList;

public class Empty extends Piece {
	
	public Empty(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.EMPTY;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " ";
	}

	@Override
	public ArrayList<Cord> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}

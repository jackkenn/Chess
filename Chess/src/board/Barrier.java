package board;

import java.util.ArrayList;

public class Barrier extends Piece{
	
	public Barrier(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}
	public Barrier(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.BARRIER;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "X";
	}
	
	@Override
	public ArrayList<Cord> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

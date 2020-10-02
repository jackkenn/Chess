package board;

import java.util.ArrayList;

public class Knight extends Piece{

	public Knight(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}
	
	public Knight(Piece oldPiece) {
		super(oldPiece);
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.KNIGHT;
	}

	@Override
	public String toString() {
		return player.DIRECTION==1 ? new String("H"): new String ("h");
	}

	@Override
	public ArrayList<Cord> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}

package board;

import java.util.ArrayList;

public class Rook extends Piece{

	public Rook(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}
	
	public Rook(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.ROOK;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return player.DIRECTION==1 ? new String("R"): new String ("r");
	}

	@Override
	public ArrayList<Cord> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}

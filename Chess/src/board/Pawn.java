package board;

import java.util.ArrayList;

public class Pawn extends Piece{
	
	public Pawn(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}

	@Override
	public PieceType getType() {
		return PieceType.PAWN;
	}

	@Override
	public String toString() {
		return player.DIRECTION==1 ? new String("P"): new String ("p");
	}

	@Override
	public ArrayList<Cord> getMoves() {
		moves.clear();
		for(int i=-1; i<2; i++) {
			try {
				Piece toCheck = board.getPiece(cord.row+player.DIRECTION, cord.column+i);
				if(toCheck.getType()!=PieceType.EMPTY&&toCheck.getType()!=PieceType.BARRIER&&toCheck.player!=player) {
					moves.add(new Cord(cord.row+player.DIRECTION, cord.column+i));
				}
				else if(toCheck.getType()==PieceType.EMPTY&&i==0) {
					moves.add(new Cord(cord.row+player.DIRECTION, cord.column+i));
				}
			} catch (NullPointerException e) {}
		}
		return moves;
	}

}

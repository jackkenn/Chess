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
		moves.clear();
			for(int i=-1; i<2; i+=2) {
				for(int j=-1; j<2; j+=2) {
					try {
						Piece toCheck = board.getPiece(cord.row+(i*2), cord.column+(j));
						if(toCheck.getType()==PieceType.EMPTY) {
							moves.add(new Cord(toCheck.cord));
						}
						else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
							moves.add(new Cord(toCheck.cord));
						}
						toCheck = board.getPiece(cord.row+(i), cord.column+(j*2));
						if(toCheck.getType()==PieceType.EMPTY) {
							moves.add(new Cord(toCheck.cord));
						}
						else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
							moves.add(new Cord(toCheck.cord));
						}
				} catch (java.lang.IndexOutOfBoundsException e) {}
			}
		}
		return moves;
	}

}
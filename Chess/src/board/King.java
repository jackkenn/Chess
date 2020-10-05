package board;

import java.util.ArrayList;

public class King extends Piece{

	public King(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}
	
	public King(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.KING;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return player.DIRECTION==1 ? new String("K"): new String ("k");
	}

	@Override
	public ArrayList<Cord> getMoves() {
		moves.clear();
		for(int i=-1; i<2; i++) {
			for(int j=-1; j<2; j++) {
				Piece toCheck = board.getPiece(cord.row+i, cord.column+j);
				if(toCheck.getType()==PieceType.EMPTY) {
					moves.add(new Cord(toCheck.cord));
				}
				else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
					moves.add(new Cord(toCheck.cord));
				}
			}
		}
		return moves;
	}
	
	public boolean castle(Spot next) {
		if(!moved&&next.piece.getType()==PieceType.ROOK&&!next.piece.moved) {
			
		}
		return false;
	}

}

package board;

import java.util.ArrayList;

public class Pawn extends Piece{
	
	public Pawn(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}
	
	public Pawn(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
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
		Piece toCheck = board.getPiece(cord.row+player.DIRECTION, cord.column);
		if(toCheck.getType()==PieceType.EMPTY) {
			moves.add(new Cord(toCheck.cord));
			toCheck = board.getPiece(cord.row+player.DIRECTION+player.DIRECTION, cord.column);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
			}
		}
		toCheck = board.getPiece(cord.row+player.DIRECTION, cord.column+1);
		if(toCheck.getType()!=PieceType.BARRIER) {
			if(toCheck.getType()!=PieceType.EMPTY&&toCheck.player!=player) {
				moves.add(new Cord(toCheck.cord));
			}
			if(toCheck.getType()==PieceType.EMPTY) {
				if(toCheck.enPassant != null) {
					if(spot==toCheck.spot&&toCheck.enPassant.player!=player) {
						moves.add(new Cord(toCheck.cord));
					}
				}
			}
		}
		toCheck = board.getPiece(cord.row+player.DIRECTION, cord.column-1);
		if(toCheck.getType()!=PieceType.BARRIER) {
			if(toCheck.getType()!=PieceType.EMPTY&&toCheck.player!=player) {
				moves.add(new Cord(toCheck.cord));
			}
			if(toCheck.getType()==PieceType.EMPTY) {
				if(toCheck.enPassant != null) {
					if(spot==toCheck.spot&&toCheck.enPassant.player!=player) {
						moves.add(new Cord(toCheck.cord));
					}
				}
			}
		}
		return moves;
	}
	
	public boolean enPassant(Spot next) {
		Piece toCheck = board.getPiece(next.cord);
		if(toCheck.getType()==PieceType.EMPTY) {
			if(toCheck.enPassant != null) {
				if(toCheck.enPassant.player!=player) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean doubleMove(Spot next) {
		Piece toCheck = board.getPiece(cord.row+player.DIRECTION, cord.column);
		if(toCheck.getType()==PieceType.EMPTY&&!moved) {
			toCheck = board.getPiece(cord.row+player.DIRECTION+player.DIRECTION, cord.column);
			if(toCheck.getType()==PieceType.EMPTY) {
				return toCheck.spot==next ? true : false;
			}
		}
		return false;
	}
}
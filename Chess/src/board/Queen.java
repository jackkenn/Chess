package board;

import java.util.ArrayList;

public class Queen extends Piece{

	public Queen(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
		// TODO Auto-generated constructor stub
	}
	
	public Queen(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.QUEEN;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return player.DIRECTION==1 ? new String("Q"): new String ("q");
	}

	@Override
	public ArrayList<Cord> getMoves() {
		moves.clear();
		int i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row+i, cord.column+i);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row-i, cord.column+i);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row-i, cord.column-i);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row+i, cord.column-i);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row+i, cord.column);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row-i, cord.column);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row, cord.column+i);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		i = 1;
		while(true) {
			Piece toCheck = board.getPiece(cord.row, cord.column-i);
			if(toCheck.getType()==PieceType.EMPTY) {
				moves.add(new Cord(toCheck.cord));
				i++;
			}
			else if(toCheck.getPlayer()!=player&&toCheck.getType()!=PieceType.BARRIER) {
				moves.add(new Cord(toCheck.cord));
				break;
			}
			else {
				break;
			}
		}
		return moves;
	}

}

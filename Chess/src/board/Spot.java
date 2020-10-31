package board;

import java.util.ArrayList;

public class Spot {
	public Piece piece;
	public Cord cord;
	public Board board;
	public ArrayList<Piece> possiblePieces = new ArrayList<Piece>();

	public Spot(int givenRow, int givenColumn, Board giveBoard) {
		cord = new Cord(givenRow, givenColumn);
		board = giveBoard;
	}

	public void setPiece(Piece GivenPiece) {
		piece = GivenPiece;
	}
	
	public boolean isAttacked(Player player) {
		boolean attacked = false;
		for (int i = 0; i < possiblePieces.size(); i++) {
				attacked = possiblePieces.get(i).player == player.opponent ? true : false;
		}
		return attacked;
	}

	private boolean enPassant(Spot next) {
		if (((Pawn) piece).enPassant(next)) {
			Piece temp = new Empty(next.piece.clone(this));
			Piece temp1 = piece.clone(next);
			next.piece.delete();
			piece.removeMoves();
			next.piece.enPassant.spot.piece = new Empty(next.piece.clone(next.piece.enPassant.spot));
			next.piece = temp1;
			temp.moved = true;
			piece = temp;
			board.getPiece(cord.row + piece.player.DIRECTION, cord.column).enPassant = null;
			piece.player.enPassant = null;
			return true;
		}
		return false;
	}

	private boolean doubleMove(Spot next) {
		if (((Pawn) piece).doubleMove(next)) {
			Piece temp = next.piece.clone(this);
			Piece temp1 = piece.clone(next);
			next.piece.removeMoves();
			piece.removeMoves();
			next.piece = temp1;
			temp.moved = true;
			piece = temp;
			Piece t = board.getPiece(cord.row + next.piece.player.DIRECTION, cord.column);
			t.enPassant = (Pawn) next.piece;
			piece.player.enPassant = (Empty) board.getPiece(cord.row + piece.player.DIRECTION, cord.column);
			return true;
		}
		return false;
	}

	private boolean castle(Spot next) {
		if(((King) piece).castle(next)) {
			Spot kingSpot = null;
			Spot rookSpot = null;
			if (next.cord.column < cord.column) { // queen side
				kingSpot = board.getSpot(cord.row, cord.column-2);
				rookSpot = board.getSpot(cord.row, cord.column-1);
			}else { //king side
				kingSpot = board.getSpot(cord.row, cord.column+2);
				rookSpot = board.getSpot(cord.row, cord.column+1);
			}
			
			Piece newPiece = kingSpot.piece.clone(this);
			Piece oldPiece = piece.clone(kingSpot);
			kingSpot.piece.removeMoves();
			piece.removeMoves();
			kingSpot.piece = oldPiece;
			oldPiece.moved = true;
			piece = newPiece;
			
			newPiece = rookSpot.piece.clone(this);
			oldPiece = next.piece.clone(rookSpot);
			rookSpot.piece.removeMoves();
			next.piece.removeMoves();
			rookSpot.piece = oldPiece;
			oldPiece.moved = true;
			next.piece = newPiece;
			
			return true;
		}
		return false;
	}
	
	public boolean movePiece(Spot next) {
		if (piece.getType() == PieceType.PAWN) {
			if(doubleMove(next)) {}
			else if(enPassant(next)) {}
			piece.addMoves();
			return true;
		}
		
		if (piece.getType() == PieceType.KING) {
			if(castle(next)) {}
			piece.addMoves();
			return true;
		}

		piece.addMoves();
		if (next.possiblePieces.contains(piece)) {
			if (next.piece.getType() == PieceType.EMPTY) {
				Piece temp = next.piece.clone(this);
				Piece temp1 = piece.clone(next);
				next.piece.removeMoves();
				piece.removeMoves();
				next.piece = temp1;
				temp.moved = true;
				piece = temp;
			} else if (next.piece.player != piece.player) {// already checked
				Piece temp = new Empty(next.piece.clone(this));
				Piece temp1 = piece.clone(next);
				next.piece.delete();
				piece.removeMoves();
				next.piece = temp1;
				temp.moved = true;
				piece = temp;
			}
		} else {
			return false;
		}
		return true;
	}

	public void movePiece(String givenStringCord) {
		movePiece(board.getSpot(new Cord(givenStringCord)));
	}
}

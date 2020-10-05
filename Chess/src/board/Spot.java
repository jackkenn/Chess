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

	private void enPassant(Spot next) {
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
		}
	}

	private void doubleMove(Spot next) {
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
		}
	}

	public boolean movePiece(Spot next) {
		if (piece.getType() == PieceType.PAWN) {
			doubleMove(next);
			enPassant(next);
		}
		
		if (piece.getType() == PieceType.KING) {
			
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

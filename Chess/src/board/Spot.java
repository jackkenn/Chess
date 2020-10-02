package board;

import java.util.ArrayList;

public class Spot {
	public Piece piece;
	public Cord scord;
	public Board board;
	public ArrayList<Piece> possiblePieces = new ArrayList<Piece>();
	
	public Spot(int givenRow, int givenColumn, Board giveBoard) {
		scord = new Cord(givenRow, givenColumn);
		board = giveBoard;
	}
	
	public void setPiece(Piece GivenPiece) {
		piece = GivenPiece;
	}
	
	public boolean movePiece(Spot next) {
		piece.addMoves();
		if(next.possiblePieces.contains(piece)) {
			if(next.piece.getType()==PieceType.EMPTY) {
				Piece temp = next.piece.clone(next);
				Piece temp1 = piece.clone(this);
				next.piece.removeMoves();
				piece.removeMoves();
				next.piece = temp1;
				piece = temp;
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	public void movePiece(String givenStringCord) {
		movePiece(board.getSpot(new Cord(givenStringCord)));
	}
}

package game.pieces;

import java.util.ArrayList;
import game.Board;
import game.Board.Cord;
import game.Board.PieceType;
import game.Board.Spot;
import game.Player;

public abstract class Piece implements Cloneable {
	protected Player player;
	protected Spot spot;
	protected Board board;
	public ArrayList<Spot> possibleMoves = new ArrayList<Spot>();
	public Pawn enPassant = null;
	public boolean moved = false;
	public ArrayList<Spot[]> pinned;
	public ArrayList<Spot[]> pinning;

	public Piece(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		player = givenPlayer;
		spot = givenSpot;
		board = givenBoard;
		player.pieces.add(this);
	}

	public Piece(Piece givenPiece) {
		player = givenPiece.player;
		spot = givenPiece.spot;
		board = givenPiece.board;
	}
	
	public Piece clone(Player givenPlayer, Spot givenSpot, Board givenBoard) throws CloneNotSupportedException {
		Piece clone = (Piece) super.clone();
		clone.player = givenPlayer;
		clone.spot = givenSpot;
		clone.board = givenBoard;
		givenPlayer.pieces.add(clone);
		clone.moved = moved;
		givenSpot.setPiece(clone);
		return clone;
	}

	public void delete() { //likely not needed
		player.pieces.remove(this);
		removeMoves();
	}

	public void move(Spot next) {
		next.piece.delete();
		spot.piece = new Empty(board.empty, spot, board);
		spot = next;
		spot.piece = this;
		moved = true;
	}

	public Spot getSpot() {
		return spot;
	}

	public Piece clone(Spot givenSpot) {
		Piece piece = null;
		try {
			piece = (Piece) super.clone();
			piece.spot = givenSpot;
			piece.player.pieces.add(piece);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return piece;
	}

	public Player getPlayer() {
		return player;
	}

	public String[] getMovesList() {
		getMoves();
		String[] str = new String[possibleMoves.size()];
		for (int i = 0; i < possibleMoves.size(); i++) {
			str[i] = possibleMoves.get(i).cord.toString();
		}
		return str;
	}

	public void addMoves() {
		removeMoves();
		getMoves();
		for (Spot s : possibleMoves) {
			s.possiblePieces.add(this);
		}
	}

	public void removeMoves() {
		for (Spot s : possibleMoves) {
			s.possiblePieces.remove(this);
		}
	}

	public abstract PieceType getType();

	public abstract String toString();

	public abstract ArrayList<Spot> getMoves();

}
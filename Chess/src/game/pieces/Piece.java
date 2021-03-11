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
	public ArrayList<Piece[]> pinned = new ArrayList<Piece[]>(); //split this
	public ArrayList<Piece[]> pinning = new ArrayList<Piece[]>(); //split this

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
		clone.pinned = new ArrayList<Piece[]>();
		clone.pinning = new ArrayList<Piece[]>();
		clone.possibleMoves = new ArrayList<Spot>();
		clone.enPassant = null;
		return clone;
	}

	public void delete() {
		player.pieces.remove(this);
		removePins();
		spot.piece = new Empty(board.empty, spot, board);
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

	/*public Piece clone(Spot givenSpot) {
		Piece piece = null;
		try {
			piece = (Piece) super.clone();
			piece.spot = givenSpot;
			piece.player.pieces.add(piece);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return piece;
	}*/

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
		possibleMoves.clear();
		getMoves();
		for (Spot s : possibleMoves) {
			s.possiblePieces.add(this);
		}
	}
	
	public void removePins() {
		pinning.clear();
		pinned.clear();
	}
	
	public int getPinned(Piece toCheck) {
		for(int i = 0; i<pinned.size(); i++) {
			if(pinned.get(i)[0].equals(toCheck)) {
				return i;
			}
			if(pinned.get(i)[1].equals(toCheck)) {
				return i;
			}
			if(pinned.get(i).length == 3) {
				if(pinned.get(i)[2].equals(toCheck)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int getPinning(Piece toCheck) {
		for(int i = 0; i<pinning.size(); i++) {
			if(pinning.get(i)[0].equals(toCheck)) {
				return i;
			}
			if(pinning.get(i).length == 2) {
				if(pinning.get(i)[1].equals(toCheck)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public void addPinned(Piece pinnedBy, Piece pinned, Piece pinnedTo) {
		if(pinnedTo == null) {
			pinnedBy.pinning.add(new Piece[] {pinned});
			pinned.pinned.add(new Piece[] {pinnedBy, pinned});
		} else {
			pinnedBy.pinning.add(new Piece[] {pinned, pinnedTo});
			pinned.pinned.add(new Piece[] {pinnedBy, pinned, pinnedTo});
			pinnedTo.pinned.add(new Piece[] {pinnedBy, pinned, pinnedTo});
		}
	}
	
	public void addPinned(Piece pinnedBy, Piece pinned) {
		addPinned(pinnedBy, pinned, null);
	}

	
	public boolean isProtected(Player player) {
		for(Piece[] s: pinned) {
			if(s[0].player.equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAttacked(Player player) {
		return isProtected(player.opponent);
	}
	
	public abstract PieceType getType();

	public abstract String toString();

	public abstract ArrayList<Spot> getMoves();
	
	public abstract int getValue();

}

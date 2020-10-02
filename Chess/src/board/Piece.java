package board;

import java.util.ArrayList;

public abstract class Piece implements Cloneable{
	protected Player player;
	protected Spot spot;
	protected Board board;
	protected ArrayList<Cord> moves = new ArrayList<Cord>();
	protected Cord cord;
	public ArrayList<Spot> possibleMoves = new ArrayList<Spot>();
	
	public Piece(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		player = givenPlayer;
		spot = givenSpot;
		board = givenBoard;
		cord = new Cord(spot.scord.row, spot.scord.column);
		
	}
	
	public Piece(Piece oldPiece) {
		player = oldPiece.getPlayer();
	}
	
	public Piece clone(Spot givenSpot) {
		Piece piece = null;
		try {
			piece = (Piece) super.clone();
			piece.spot = givenSpot;
			addMoves();
			
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
		String[] s = new String[moves.size()];
		for(int i=0; i<moves.size(); i++) {
			s[i] = moves.get(i).cord;
		}
		return s;
	}
	
	public void addMoves() {
		getMoves();
		removeMoves();
		possibleMoves.clear();
		for(int i=0; i<moves.size(); i++) {
			possibleMoves.add(board.getSpot(moves.get(i).row, moves.get(i).column));
			possibleMoves.get(possibleMoves.size()-1).possiblePieces.add(this);
		}
	}
	
	public void removeMoves() {
		for(int i=0; i<possibleMoves.size(); i++) {
			possibleMoves.get(i).possiblePieces.remove(this);
		}
	}
	
	public abstract PieceType getType();
	public abstract String toString();
	public abstract ArrayList<Cord> getMoves();

}

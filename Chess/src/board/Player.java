package board;

import java.util.ArrayList;

public class Player {
	public ArrayList<Piece> pieces;
	public ArrayList<Spot> moves;
	public Board board;
	public final int DIRECTION;
	public Player opponent;
	public boolean turn = false;
	public Empty enPassant = null;
	
	public Player(Board givenBoard, int givenDirection) {
		board = givenBoard;
		DIRECTION = givenDirection;
		
		pieces = new ArrayList<Piece>();
		moves = new ArrayList<Spot>();
	}
	
	public ArrayList<Spot> getMoves(){
		moves.clear();
		for(int i=0; i<pieces.size(); i++) {
			pieces.get(i).addMoves();
			moves.addAll(pieces.get(i).possibleMoves);
		}
		return moves;
	}

}

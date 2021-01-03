package board;

import java.util.ArrayList;

public class Player {
	public ArrayList<Piece> pieces;
	public ArrayList<Spot[]> moves;
	public Board board;
	public final int DIRECTION;
	public Player opponent;
	public boolean turn = false;
	public Empty enPassant = null;
	public King king = null;
	
	public Player(Board givenBoard, int givenDirection) {
		board = givenBoard;
		DIRECTION = givenDirection;
		
		pieces = new ArrayList<Piece>();
		moves = new ArrayList<Spot[]>();
	}
	
	public ArrayList<Spot[]> getMoves(){
		moves.clear();
		for(int i=0; i<pieces.size()-1; i++) { //uhh
			pieces.get(i).addMoves();
			int temp = pieces.size()-1;
			for(int j=0; j<pieces.get(i).possibleMoves.size()-1; i++) {
				moves.add(new Spot[] {pieces.get(i).spot, pieces.get(i).possibleMoves.get(j)});
			}
		}
		return moves;
	}
	
	public boolean isMated() {
		if(king.spot.isAttacked(this)) {
			ArrayList<Spot> checkingPieceMoves = new ArrayList<Spot>();
			for(int i=0; i<king.spot.possiblePieces.size(); i++) {
				if(king.spot.possiblePieces.get(i).player==opponent) {
					for(int j=0; j<king.spot.possiblePieces.get(i).possibleMoves.size(); j++) {
						
					}
				}
			}
			
		}
		return false;
	}

}

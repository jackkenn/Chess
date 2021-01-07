package game;

import java.util.ArrayList;
import game.Board.*;
import game.pieces.*;

public class Player implements Cloneable {
	public ArrayList<Piece> pieces;
	public ArrayList<Spot[]> moves;
	public Board board;
	public final int DIRECTION;
	public Player opponent;
	public boolean turn = false;
	public Empty enPassant = null;
	public King king = null;
	public ArrayList<Spot[]> legalMoves;
	public boolean mated = false;

	public Player(Board givenBoard, int givenDirection) {
		board = givenBoard;
		DIRECTION = givenDirection;

		pieces = new ArrayList<Piece>();
		moves = new ArrayList<Spot[]>();
		legalMoves = new ArrayList<Spot[]>();
	}
	
	public Player clone(Board givenBoard) throws CloneNotSupportedException {
		Player clone = new Player(givenBoard, DIRECTION);
		for(Piece p : pieces) {
			if(p.getType() == PieceType.KING) {
				clone.king = (King) p.clone(clone, givenBoard.getSpot(p.getSpot().cord), givenBoard);
			} else {
				p.clone(clone, givenBoard.getSpot(p.getSpot().cord), givenBoard);
			}
		}
		clone.turn = turn;
		clone.mated = mated;
		if(enPassant != null) {
			clone.enPassant = (Empty) givenBoard.getPiece(enPassant.getSpot().cord);
			
		}
		return clone;
	}

	public ArrayList<Spot[]> getMoves() {
		moves.clear();
		for (Piece p : pieces) {
			p.getMoves();
			for(Spot s : p.possibleMoves) {
				moves.add(new Spot[] {p.getSpot(), s});
			}
		}
		return moves;
	}

	public boolean isMated() {
		if (king.getSpot().isAttacked(this)) {
			legalMoves.clear();
			for(Spot s : king.possibleMoves) {
				if(!s.isAttacked(this)) {
					legalMoves.add(new Spot[] {king.getSpot(), s});
				}
			}
			ArrayList<Spot> spotsToCheck = new ArrayList<Spot>();
			for(Piece p : king.getSpot().possiblePieces) {
				if(p.getPlayer()==opponent) {
					spotsToCheck.add(p.getSpot());
					if(p.getType() != PieceType.KNIGHT) {
						spotsToCheck.addAll(p.possibleMoves);
					}
				}
			}
			for(Piece p : pieces) {
				for(Spot s: spotsToCheck) {
					if(hasMove(p.getSpot(), s)) { //
						Board checkingBoard = new Board(board);
						Player inCheckPlayer = checkingBoard.white.turn ? checkingBoard.white : checkingBoard.black;
						checkingBoard.getPiece(p.getSpot().cord).move(s);
						inCheckPlayer.opponent.getMoves();
						if(!inCheckPlayer.king.getSpot().isAttacked(inCheckPlayer.opponent)) {
							legalMoves.add(new Spot[] {p.getSpot(), s});
						}
					}
				}
			}
			return legalMoves.size() > 0;
		}
		return false;
	}

	public ArrayList<Spot[]> findLegalMoves() {
		legalMoves.clear();
		if(king.getSpot().isAttacked(this)) {
			mated = isMated();
			return legalMoves;
		}
		legalMoves.addAll(getMoves());
		return legalMoves;
	}
	
	public boolean isLegalMove(Spot piece, Spot nextSpot) {
		findLegalMoves();
		for(Spot[] s : legalMoves) {
			if(s[0].equals(piece)) {
				if(s[1].equals(nextSpot)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasMove(Spot piece, Spot nextSpot) {
		for(Spot[] s : moves) {
			if(s[0].equals(piece)) {
				if(s[1].equals(nextSpot)) {
					return true;
				}
			}
		}
		return false;
	}

	public void startTurn() {
		turn = true;
		if(enPassant != null) {
			enPassant.enPassant=null;
			enPassant = null;
		}
		findLegalMoves();
	}
	
	public void endTurn() {
		turn = false;
		findLegalMoves();
		opponent.startTurn();
	}
	
	public String toString() {
		if(DIRECTION != 0) {
			return (DIRECTION == -1 ? new String("WHITE") : new String("BLACK"));
		}
		return new String("EMPTY");
	}
}

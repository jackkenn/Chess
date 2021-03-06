package game.pieces;

import java.util.ArrayList;

import game.Board;
import game.Board.*;
import game.Player;

public class Pawn extends Piece {

	public Pawn(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}

	public Pawn(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		return PieceType.PAWN;
	}

	@Override
	public String toString() {
		return player.DIRECTION == 1 ? new String("P") : new String("p");
	}

	@Override
	public ArrayList<Spot> getMoves() {
		possibleMoves.clear();
		Piece toCheck = board.getPiece(spot.cord.row + player.DIRECTION, spot.cord.column);
		if (toCheck.getType() == PieceType.EMPTY) {
			possibleMoves.add(toCheck.spot);
			if (!moved) {
				toCheck = board.getPiece(spot.cord.row + (player.DIRECTION * 2), spot.cord.column);
				if (toCheck.getType() == PieceType.EMPTY) {
					possibleMoves.add(toCheck.spot);
				}
			}
		}
		toCheck = board.getPiece(spot.cord.row + player.DIRECTION, spot.cord.column + 1);
		if (toCheck.getType() != PieceType.BARRIER) {
			this.addPinned(this, toCheck);
			if (toCheck.getType() != PieceType.EMPTY) {
				if (toCheck.player != player) {
					possibleMoves.add(toCheck.spot);
				}
			}
			if (toCheck.getType() == PieceType.EMPTY) {
				if (toCheck.enPassant != null) {
					if (toCheck.enPassant.player != player) {
						possibleMoves.add(toCheck.spot);
						this.addPinned(this, toCheck.enPassant);
					}
				}
			}
		}
		toCheck = board.getPiece(spot.cord.row + player.DIRECTION, spot.cord.column - 1);
		if (toCheck.getType() != PieceType.BARRIER) {
			this.addPinned(this, toCheck);
			if (toCheck.getType() != PieceType.EMPTY) {
				if (toCheck.player != player) {
					possibleMoves.add(toCheck.spot);
				}
			}
			if (toCheck.getType() == PieceType.EMPTY) {
				if (toCheck.enPassant != null) {
					if (toCheck.enPassant.player != player) {
						possibleMoves.add(toCheck.spot);
						this.addPinned(this, toCheck.enPassant);
					}
				}
			}
		}
		return possibleMoves;
	}

	public boolean enPassant(Spot next) {
		Piece toCheck = board.getPiece(next.cord);
		if (toCheck.getType() == PieceType.EMPTY) {
			if (toCheck.enPassant != null) {
				if (toCheck.enPassant.player != player) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void move(Spot next) {
		if (spot.cord.row + (player.DIRECTION * 2) == next.cord.row) {
			board.getPiece(spot.cord.row + player.DIRECTION, spot.cord.column).enPassant = this;
			player.enPassant = (Empty) board.getPiece(spot.cord.row + player.DIRECTION, spot.cord.column);
		}
		if (next.piece.enPassant != null ? next.piece.enPassant.player.equals(player.opponent) : false) {
			next.piece.enPassant.delete();
		}
		next.piece.delete();
		spot.piece = new Empty(board.empty, spot, board);
		spot = next;
		spot.piece = this;
		moved = true;
		if (next.cord.row == 1 || next.cord.row == 8) {
			this.delete();
			next.piece = new Queen(player, next, board);
		}
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 1;
	}
}

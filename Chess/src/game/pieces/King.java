package game.pieces;

import java.util.ArrayList;

import game.Board;
import game.Board.*;
import game.Player;


public class King extends Piece {

	public King(Piece oldPiece) {
		super(oldPiece);
		player.king = this;
		// TODO Auto-generated constructor stub
	}

	public King(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
		player.king = this;
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.KING;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return player.DIRECTION == 1 ? new String("K") : new String("k");
	}

	@Override
	public ArrayList<Spot> getMoves() {
		possibleMoves.clear();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Piece toCheck = board.getPiece(spot.cord.row + i, spot.cord.column + j);
				if (toCheck.getType() == PieceType.EMPTY) {
					possibleMoves.add(toCheck.spot);
				} else if (toCheck.getPlayer() != player && toCheck.getType() != PieceType.BARRIER) {
					possibleMoves.add(toCheck.spot);
				}
			}
		}
		if(!spot.isAttacked(player) && !moved) {
			Spot s = board.getSpot(board.new Cord(spot.cord.row, spot.cord.column - 3));
			if(castle(s)) {
				possibleMoves.add(s);
			}
			s = board.getSpot(board.new Cord(spot.cord.row, spot.cord.column + 4));
			if(castle(s)) {
				possibleMoves.add(s);
			}
		}
		return possibleMoves;
	}

	public boolean castle(Spot next) {
		boolean canCastle = false;
		if (!moved && next.piece.getType() == PieceType.ROOK && !next.piece.moved) {
			if(!spot.isAttacked(player)) {
				if (next.cord.column < spot.cord.column) { // queen side
					for (int i = spot.cord.column - 1; i >=next.cord.column; i--) {
						Spot temp = board.getSpot(spot.cord.row, i);
						if (temp.piece.getType() == PieceType.EMPTY) {
							canCastle = !temp.isAttacked(player);
						}
					}
				} else { // king side
					for (int i = spot.cord.column + 1; i <= next.cord.column; i++) {
						Spot temp = board.getSpot(spot.cord.row, i);
						if (temp.piece.getType() == PieceType.EMPTY) {
							canCastle = !temp.isAttacked(player);
						}
					}
				}
			}
		}
		return canCastle;
	}

}

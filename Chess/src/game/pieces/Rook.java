package game.pieces;

import java.util.ArrayList;
import game.Board;
import game.Board.*;
import game.Player;

public class Rook extends Piece {

	public Rook(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}

	public Rook(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.ROOK;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return player.DIRECTION == 1 ? new String("R") : new String("r");
	}

	@Override
	public ArrayList<Spot> getMoves() {
		possibleMoves.clear();
		int i = 1;
		while (true) {
			Piece toCheck = board.getPiece(spot.cord.row + i, spot.cord.column);
			if (toCheck.getType() == PieceType.EMPTY) {
				possibleMoves.add(toCheck.spot);
				i++;
			} else if (toCheck.getType() != PieceType.BARRIER) {
				if (toCheck.getPlayer() != player) {
					possibleMoves.add(toCheck.spot);
					while (true) {
						i++;
						Piece toCheckIfPinned = board.getPiece(spot.cord.row + i, spot.cord.column);
						if (toCheckIfPinned.getType() == PieceType.BARRIER) {
							this.addPinned(this, toCheck);
							break;
						} else if (toCheckIfPinned.player.equals(player.opponent)) {
							this.addPinned(this, toCheck, toCheckIfPinned);
							break;
						}
					}
				} else {
					this.addPinned(this, toCheck);
				}
				break;
			} else {
				break;
			}
		}
		i = 1;
		while (true) {
			Piece toCheck = board.getPiece(spot.cord.row - i, spot.cord.column);
			if (toCheck.getType() == PieceType.EMPTY) {
				possibleMoves.add(toCheck.spot);
				i++;
			} else if (toCheck.getType() != PieceType.BARRIER) {
				if (toCheck.getPlayer() != player) {
					possibleMoves.add(toCheck.spot);
					while (true) {
						i++;
						Piece toCheckIfPinned = board.getPiece(spot.cord.row - i, spot.cord.column);
						if (toCheckIfPinned.getType() == PieceType.BARRIER) {
							this.addPinned(this, toCheck);
							break;
						} else if (toCheckIfPinned.player.equals(player.opponent)) {
							this.addPinned(this, toCheck, toCheckIfPinned);
							break;
						}
					}
				} else {
					this.addPinned(this, toCheck);
				}
				break;
			} else {
				break;
			}
		}
		i = 1;
		while (true) {
			Piece toCheck = board.getPiece(spot.cord.row, spot.cord.column + i);
			if (toCheck.getType() == PieceType.EMPTY) {
				possibleMoves.add(toCheck.spot);
				i++;
			} else if (toCheck.getType() != PieceType.BARRIER) {
				if (toCheck.getPlayer() != player) {
					possibleMoves.add(toCheck.spot);
					while (true) {
						i++;
						Piece toCheckIfPinned = board.getPiece(spot.cord.row, spot.cord.column + i);
						if (toCheckIfPinned.getType() == PieceType.BARRIER) {
							this.addPinned(this, toCheck);
							break;
						} else if (toCheckIfPinned.player.equals(player.opponent)) {
							this.addPinned(this, toCheck, toCheckIfPinned);
							break;
						}
					}
				} else {
					this.addPinned(this, toCheck);
				}
				break;
			} else {
				break;
			}
		}
		i = 1;
		while (true) {
			Piece toCheck = board.getPiece(spot.cord.row, spot.cord.column - i);
			if (toCheck.getType() == PieceType.EMPTY) {
				possibleMoves.add(toCheck.spot);
				i++;
			} else if (toCheck.getType() != PieceType.BARRIER) {
				if (toCheck.getPlayer() != player) {
					possibleMoves.add(toCheck.spot);
					while (true) {
						i++;
						Piece toCheckIfPinned = board.getPiece(spot.cord.row, spot.cord.column - i);
						if (toCheckIfPinned.getType() == PieceType.BARRIER) {
							this.addPinned(this, toCheck);
							break;
						} else if (toCheckIfPinned.player.equals(player.opponent)) {
							this.addPinned(this, toCheck, toCheckIfPinned);
							break;
						}
					}
				} else {
					this.addPinned(this, toCheck);
				}
				break;
			} else {
				break;
			}
		}
		return possibleMoves;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 5;
	}

}

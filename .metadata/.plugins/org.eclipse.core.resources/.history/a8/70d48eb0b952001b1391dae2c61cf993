package game.pieces;

import java.util.ArrayList;

import game.Board;
import game.Board.*;
import game.Player;

public class Knight extends Piece{

	public Knight(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}
	
	public Knight(Piece oldPiece) {
		super(oldPiece);
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.KNIGHT;
	}

	@Override
	public String toString() {
		return player.DIRECTION==1 ? new String("H"): new String ("h");
	}

	@Override
	public ArrayList<Spot> getMoves() {
		possibleMoves.clear();
			for(int i=-1; i<2; i+=2) {
				for(int j=-1; j<2; j+=2) {
					try {
						Piece toCheck = board.getPiece(spot.cord.row+(i*2), spot.cord.column+(j));
						if(toCheck.getType()==PieceType.EMPTY) {
							possibleMoves.add(toCheck.spot);
						}
						else if(toCheck.getType()!=PieceType.BARRIER) {
							this.addPinned(this, toCheck);
							if(toCheck.getPlayer()!=player) {
								possibleMoves.add(toCheck.spot);
							}
						}
						toCheck = board.getPiece(spot.cord.row+(i), spot.cord.column+(j*2));
						if(toCheck.getType()==PieceType.EMPTY) {
							possibleMoves.add(toCheck.spot);
						}
						else if(toCheck.getType()!=PieceType.BARRIER) {
							this.addPinned(this, toCheck);
							if(toCheck.getPlayer()!=player) {
								possibleMoves.add(toCheck.spot);
							}
						}
				} catch (java.lang.IndexOutOfBoundsException e) {}
			}
		}
		return possibleMoves;
	}

}

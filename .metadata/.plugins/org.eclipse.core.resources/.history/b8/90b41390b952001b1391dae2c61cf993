package game.pieces;

import java.util.ArrayList;

import game.Board;
import game.Board.*;
import game.Player;

public class Barrier extends Piece{
	
	public Barrier(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
	}
	public Barrier(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.BARRIER;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(spot.cord.column==0) {
			switch(spot.cord.row) {
			case 1:
				return "1";
			case 2:
				return "2";
			case 3:
				return "3";
			case 4:
				return "4";
			case 5:
				return "5";
			case 6:
				return "6";
			case 7:
				return "7";
			case 8:
				return "8";
			default:
				return "X";
			}
		}
		if(spot.cord.row==0) {
			switch(spot.cord.column) {
			case 1:
				return "A";
			case 2:
				return "B";
			case 3:
				return "C";
			case 4:
				return "D";
			case 5:
				return "E";
			case 6:
				return "F";
			case 7:
				return "G";
			case 8:
				return "H";
			default:
				return "X";
			}
		}
		return "X";
	}
	
	@Override
	public ArrayList<Spot> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

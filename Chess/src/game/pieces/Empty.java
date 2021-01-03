package game.pieces;

import java.util.ArrayList;

import game.Board;
import game.Board.*;
import game.Player;

public class Empty extends Piece {
	
	public Empty(Player givenPlayer, Spot givenSpot, Board givenBoard) {
		super(givenPlayer, givenSpot, givenBoard);
		// TODO Auto-generated constructor stub
	}

	public Empty(Piece oldPiece) {
		super(oldPiece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PieceType getType() {
		// TODO Auto-generated method stub
		return PieceType.EMPTY;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " ";
	}

	@Override
	public ArrayList<Spot> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}

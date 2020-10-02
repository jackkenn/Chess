package board;

import java.util.ArrayList;

public class Player {
	public ArrayList<Piece> pieces;
	public Board board;
	public final int DIRECTION;
	
	public Player(Board givenBoard, int givenDirection) {
		board = givenBoard;
		DIRECTION = givenDirection;
	}

}

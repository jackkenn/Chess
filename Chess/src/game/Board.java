package game;

import java.util.ArrayList;

import game.pieces.Barrier;
import game.pieces.Bishop;
import game.pieces.Empty;
import game.pieces.King;
import game.pieces.Knight;
import game.pieces.Pawn;
import game.pieces.Piece;
import game.pieces.Queen;
import game.pieces.Rook;

public class Board implements Cloneable {
	public ArrayList<ArrayList<Spot>> grid;
	public Player empty, white, black;

	public Board() {
		grid = new ArrayList<ArrayList<Spot>>();
		white = new Player(this, 1);
		black = new Player(this, -1);
		empty = new Player(this, 0);
		white.opponent = black;
		black.opponent = white;
		white.turn = true;
		genGrid(8, 8);
		white.findLegalMoves();
		black.findLegalMoves();
	}

	public Board(Board givenBoard) {
		grid = new ArrayList<ArrayList<Spot>>();
		empty = new Player(this, 0);
		for (int i = 0; i < 8 + 2; i++) {
			grid.add(new ArrayList<Spot>());
			for (int j = 0; j < 8 + 2; j++) {
				grid.get(i).add(new Spot(i, j, this));
				if (i == 0 || i == 8 + 1 || j == 0 || j == 8 + 1) {
					getSpot(i, j).setPiece(new Barrier(empty, getSpot(i, j), this));
				} else {
					getSpot(i, j).setPiece(new Empty(empty, getSpot(i, j), this));
				}
			}
		}
		try {
			white = givenBoard.white.clone(this);
			black = givenBoard.black.clone(this);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		white.opponent = black;
		black.opponent = white;
		for (Piece p : givenBoard.white.pieces) {
			if (p.enPassant != null) {
				this.getPiece(p.getSpot().cord).enPassant = (Pawn) this.getPiece(p.enPassant.getSpot().cord);
			}
		}
		for (Piece p : givenBoard.black.pieces) {
			if (p.enPassant != null) {
				this.getPiece(p.getSpot().cord).enPassant = (Pawn) this.getPiece(p.enPassant.getSpot().cord);
			}
		}
		white.getMoves();
		black.getMoves();
	}

	private void genGrid(int rows, int columns) {
		for (int i = 0; i < rows + 2; i++) {
			grid.add(new ArrayList<Spot>());
			for (int j = 0; j < columns + 2; j++) {
				grid.get(i).add(new Spot(i, j, this));
				if (i == 0 || i == rows + 1 || j == 0 || j == columns + 1) {
					getSpot(i, j).setPiece(new Barrier(empty, getSpot(i, j), this));
				}

				else if (i == 2) {
					getSpot(i, j).setPiece(new Pawn(white, getSpot(i, j), this));
				} else if (i == rows - 1) {
					getSpot(i, j).setPiece(new Pawn(black, getSpot(i, j), this));
				}

				else if (i == 1 && (j == 1 || j == 8)) {
					getSpot(i, j).setPiece(new Rook(white, getSpot(i, j), this));
				} else if (i == 8 && (j == 1 || j == 8)) {
					getSpot(i, j).setPiece(new Rook(black, getSpot(i, j), this));
				}

				else if (i == 1 && (j == 2 || j == 7)) {
					getSpot(i, j).setPiece(new Knight(white, getSpot(i, j), this));
				} else if (i == 8 && (j == 2 || j == 7)) {
					getSpot(i, j).setPiece(new Knight(black, getSpot(i, j), this));
				}

				else if (i == 1 && (j == 3 || j == 6)) {
					getSpot(i, j).setPiece(new Bishop(white, getSpot(i, j), this));
				} else if (i == 8 && (j == 3 || j == 6)) {
					getSpot(i, j).setPiece(new Bishop(black, getSpot(i, j), this));
				}

				else if (i == 1 && j == 4) {
					getSpot(i, j).setPiece(new Queen(white, getSpot(i, j), this));
				} else if (i == 8 && j == 4) {
					getSpot(i, j).setPiece(new Queen(black, getSpot(i, j), this));
				}

				else if (i == 1 && j == 5) {
					getSpot(i, j).setPiece(new King(white, getSpot(i, j), this));
				} else if (i == 8 && j == 5) {
					getSpot(i, j).setPiece(new King(black, getSpot(i, j), this));
				}

				else {
					getSpot(i, j).setPiece(new Empty(empty, getSpot(i, j), this));
				}
			}
		}
	}

	public Spot getSpot(int row, int column) {
		/*
		if (row >= grid.size() || row < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (column >= grid.get(0).size() || column < 0) {
			throw new IndexOutOfBoundsException();
		}*/
		return grid.get(row).get(column);
	}

	public Spot getSpot(Cord cord) {
		return getSpot(cord.row, cord.column);
	}

	public Spot getSpot(String givenStringCord) {
		return getSpot(new Cord(givenStringCord));
	}

	public Piece getPiece(int row, int column) {
		return getSpot(row, column).piece;
	}

	public Piece getPiece(Cord cord) {
		return getSpot(cord).piece;
	}

	public Piece getPiece(String s) {
		return getSpot(new Cord(s)).piece;
	}

	public String toString() {
		String s = new String();
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				try {
					s += " " + getPiece(i, j).toString() + " ";
				} catch (Exception e) {
					s += " # ";
					System.out.print("null spot at " + i + ", " + j + "\n");
				}
			}
			s += "\n";
		}
		return s;
	}
	
	public void clearSpotsPieces() {
		for(ArrayList<Spot> arr : grid) {
			for(Spot s : arr) {
				s.possiblePieces.clear();
			}
		}
	}

	public class Spot {
		public Piece piece;
		public Cord cord;
		public Board board;
		public ArrayList<Piece> possiblePieces = new ArrayList<Piece>();

		public Spot(int givenRow, int givenColumn, Board giveBoard) {
			cord = new Cord(givenRow, givenColumn);
			board = giveBoard;
		}

		public void setPiece(Piece GivenPiece) {
			piece = GivenPiece;
		}

		public boolean isAttacked(Player player) {
			for (Piece p : possiblePieces) {
				if (p.getPlayer() == player.opponent) {
					return true;
				}
			}
			return false;
		}
		
		public String toString() {
			return cord.toString();
		}
		
		public boolean equals(Spot s) {
			return cord.equals(s.cord);
		}

	}

	public enum PieceType {
		BARRIER, EMPTY, KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;
	}

	public class Cord implements Cloneable {
		public int row, column;
		public String cord;

		public Cord(int givenRow, int givenColumn) {
			row = givenRow;
			column = givenColumn;
			String s = new String((char) (column + 65) + Integer.toString(row));
			cord = new String((char) (column + 65 - 1) + Integer.toString(row));

		}

		public Cord(String s) {
			try {
				column = (int) s.toCharArray()[0] - 65 + 1;
				row = (int) s.toCharArray()[1] - 48;
				cord = new String(s);
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		public Cord(Cord GivenCord) {
			Cord temp = GivenCord.clone();
			row = temp.row;
			column = temp.column;
			cord = temp.cord;
		}

		@Override
		public String toString() {
			return new String((char) (column + 64) + Integer.toString(row));
		}

		@Override
		public Cord clone() {
			try {
				return (Cord) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public boolean equals(Cord c) {
			if(row != c.row) {
				return false;
			}
			if(column != c.column) {
				return false;
			}
			return true;
		}

	}

}

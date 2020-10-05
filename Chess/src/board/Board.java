package board;

import java.util.ArrayList;

public class Board {
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
	}
	
	private void genGrid(int rows, int columns){
		for(int i=0; i<rows+2; i++) {
			grid.add(new ArrayList<Spot>());
			for(int j=0; j<columns+2; j++) {
				grid.get(i).add(new Spot(i, j, this));
				if(i==0||i==rows+1||j==0||j==columns+1) {
					getSpot(i, j).setPiece(new Barrier(empty, getSpot(i, j), this));
				}
				
				else if(i==2) {
					getSpot(i, j).setPiece(new Pawn(white, getSpot(i, j), this));					
				}
				else if(i==rows-1) {
					getSpot(i, j).setPiece(new Pawn(black, getSpot(i, j), this));					
				}
				
				else if(i==1&&(j==1||j==8)) {
					getSpot(i, j).setPiece(new Rook(white, getSpot(i, j), this));
				}
				else if(i==8&&(j==1||j==8)) {
					getSpot(i, j).setPiece(new Rook(black, getSpot(i, j), this));
				}
				
				else if(i==1&&(j==2||j==7)) {
					getSpot(i, j).setPiece(new Knight(white, getSpot(i, j), this));
				}
				else if(i==8&&(j==2||j==7)) {
					getSpot(i, j).setPiece(new Knight(black, getSpot(i, j), this));
				}
				
				else if(i==1&&(j==3||j==6)) {
					getSpot(i, j).setPiece(new Bishop(white, getSpot(i, j), this));
				}
				else if(i==8&&(j==3||j==6)) {
					getSpot(i, j).setPiece(new Bishop(black, getSpot(i, j), this));
				}
				
				else if(i==1&&j==4) {
					getSpot(i, j).setPiece(new Queen(white, getSpot(i, j), this));
				}
				else if(i==8&&j==5) {
					getSpot(i, j).setPiece(new Queen(black, getSpot(i, j), this));
				}
				
				else if(i==1&&j==5) {
					getSpot(i, j).setPiece(new King(white, getSpot(i, j), this));
				}
				else if(i==8&&j==4) {
					getSpot(i, j).setPiece(new King(black, getSpot(i, j), this));
				}
				
				else {
					getSpot(i, j).setPiece(new Empty(empty, getSpot(i, j), this));					
				}
			}
		}
		
	}
	
	public Spot getSpot(int row, int column) {
		return grid.get(row).get(column);
	}
	
	public Spot getSpot(Cord cord) {
		return grid.get(cord.row).get(cord.column);
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
		for(int i=0; i<grid.size(); i++) {
			for(int j=0; j<grid.get(i).size(); j++) {
				try {
					s += " " + getPiece(i, j).toString() + " ";
				} catch (NullPointerException e) {
					s += " # ";
					System.out.print("null spot at " + i + ", " + j + "\n");
				}
			}
			s += "\n";
		}
		return s;
	}

}
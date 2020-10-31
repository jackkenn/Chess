package board;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static Board board = new Board();
	private static Scanner scan = new Scanner(System.in);
	private static String input = new String();
	private static ArrayList<String> inputList = new ArrayList<String>();
	private static Player turn = board.white;

	private static void print(String s) {
		System.out.print(s);
	}

	private static boolean move(String move) {
		Scanner scan = new Scanner(move);
		Cord curCord = new Cord(scan.hasNext() ? scan.next() : "00");
		if (curCord.row < board.grid.size() - 1 && curCord.row > 0 && curCord.column < board.grid.get(0).size() - 1
				&& curCord.column > 0) {
			Spot curSpot = board.getSpot(curCord);
			if (curSpot.piece.player == turn) {
				Cord nextCord = new Cord(scan.hasNext() ? scan.next() : "00");
				if (curCord.row < board.grid.size() - 1 && curCord.row > 0
						&& curCord.column < board.grid.get(0).size() - 1 && curCord.column > 0) {
					Spot nextSpot = board.getSpot(nextCord);
					turn.getMoves();
					//if (!turn.king.spot.isAttacked(turn)) {
						if (!turn.moves.isEmpty()) {
							if (curSpot.movePiece(nextSpot)) {
								inputList.add(input);
								turn.getMoves();
								turn = turn.opponent;
								print(board.toString());
								scan.close();
								return true;
							}
						} else {
							print("No Avaliable Moves\n");
							input = "done";
						}
				} else {
					print("Invalid location\n");
				}
			} else {
				print("Invalid piece\n");
			}
		} else {
			print("Invalid location\n");
		}
		scan.close();
		return false;
	}

	private static void test() {
		Board b = new Board();
		// print(new String(b.getSpot("A1").toString()+"."));
		print(b.toString());
		Scanner scan = new Scanner(System.in);
		String input = new String();
		ArrayList<String> inputList = new ArrayList<String>();
		/*
		 * b.getSpot("F1").movePiece("D3"); b.getSpot("E2").movePiece("E4");
		 * b.getSpot("F1").movePiece("D3"); b.getSpot("G1").movePiece("F3");
		 */
		b.getSpot("E2").movePiece("E4");
		b.getSpot("D1").movePiece("E2");
		b.getSpot("B2").movePiece("B4");
		b.getSpot("B1").movePiece("C3");
		b.getSpot("C1").movePiece("A3");
		b.getSpot("E1").movePiece("A1");

		print("\n" + b.toString());
		/*
		 * while(!input.contentEquals("done")) { if(scan.hasNext()) { input =
		 * scan.next(); String s = new String(input); Piece p = b.getPiece(input);
		 * if(scan.hasNext()) { input = scan.next(); s += "," + input; inputList.add(s);
		 * b.getSpot(input).movePiece(input); print(b.toString()); } } }
		 */
		print("\ndone");
	}

	public static void main(String[] args) {
		print(board.toString());
		while (!input.contentEquals("done")) {
			if (scan.hasNextLine()) {
				input = scan.nextLine();
				move(input.toUpperCase());
			}
		}
		scan.close();

	}
}

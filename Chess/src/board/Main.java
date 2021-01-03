package board;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static Board board = new Board();
	private static Scanner scan = new Scanner(System.in);
	private static String input = new String();
	private static ArrayList<String> inputList = new ArrayList<String>();
	private static Player turn = board.white;
	private static boolean runlast = false;
	private static boolean record = false;
	private static BufferedWriter moveList;

	private static void print(String s) {
		System.out.print(s);
	}

	private static boolean move(String move) throws IOException {
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
					if (turn.king.spot.isAttacked(turn)) {
						if (!turn.moves.isEmpty()) {
							Board checkBoard = board.clone();
							if (checkBoard.getSpot(curCord).movePiece(checkBoard.getSpot(nextCord))) {
								
							}
							
						} else {
							print("CheckMate\n");
							input = "done";
						}

					} else if (!turn.moves.isEmpty()) {
						if (curSpot.movePiece(nextSpot)) {
							inputList.add(curCord.cord + " " + nextCord.cord);
							if (record) {
								moveList.write(inputList.get(inputList.size() - 1) + "\n");
							}
							turn.getMoves();
							turn = turn.opponent;
							print(board.toString() + "\n");
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

	public static void main(String[] args) throws IOException {

		runlast = true;
		record = false;

		if (record) {
			moveList = new BufferedWriter(new FileWriter("MoveList.txt"));
		}

		print(board.toString());
		if (runlast) { // repeat last game
			File f = new File("MoveList.txt");
			Scanner tscan = new Scanner(f);
			while (tscan.hasNextLine()) {
				try {
					move(tscan.nextLine().toUpperCase());
				} catch (Exception e) {
					System.out.println(e.getStackTrace());
					input = "done";
				}
			}
			tscan.close();
		}
		while (!input.contentEquals("done")) {
			if (scan.hasNextLine()) {
				input = scan.nextLine();
				move(input.toUpperCase());
			}
		}

		moveList.close();
		scan.close();
	}
}

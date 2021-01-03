package game;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import game.Board.Cord;
import game.Board.Spot;

public class GameLoop {
	private static Board board = new Board();
	private static Scanner scan;
	private static String input = new String();
	private static ArrayList<String> inputList = new ArrayList<String>();
	private static Player turn = board.white;
	private static boolean runlast = false;
	private static boolean record = false;
	private static BufferedWriter moveList;
	private static GameWindow window;
	public static String movesIN = new String();
	public static GameLoop self;
	
	public GameLoop() {
		self = this;
	}

	private static void print(String s) {
		System.out.print(s);
	}

	private static boolean move(String move) throws IOException {
		Scanner scan = new Scanner(move);
		Cord curCord = board.new Cord(scan.hasNext() ? scan.next() : "00");
		if (curCord.row < board.grid.size() - 1 && curCord.row > 0 && curCord.column < board.grid.get(0).size() - 1
				&& curCord.column > 0) {
			Spot curSpot = board.getSpot(curCord);
			if (curSpot.piece.getPlayer() == turn) {
				Cord nextCord = board.new Cord(scan.hasNext() ? scan.next() : "00");
				if (nextCord.row < board.grid.size() - 1 && nextCord.row > 0
						&& nextCord.column < board.grid.get(0).size() - 1 && nextCord.column > 0) {
					Spot nextSpot = board.getSpot(nextCord);
					if (!turn.moves.isEmpty()) {
						if (turn.isLegalMove(curSpot, nextSpot)) {
							inputList.add(curCord.cord + " " + nextCord.cord);
							if (record) {
								moveList = new BufferedWriter(new FileWriter("MoveList.txt"));
								for(String s : inputList) {
									moveList.write(s + "\n");
								}
								moveList.close();
							}
							board.getPiece(curCord).move(nextSpot);
							turn = turn.opponent;
							print(board.toString() + "\n\n");
							scan.close();
							return true;
						} else {
							print("Illegal Move\n");
							scan.close();
							return false;
						}
					} else {
						print("No Avaliable Moves\n");
						scan.close();
						return false;
					}
				} else {
					print("Invalid location\n");
					scan.close();
					return false;
				}
			} else {
				print("Invalid piece\n");
				scan.close();
				return false;
			}
		} else {
			print("Invalid location\n");
			scan.close();
			return false;
		}
	}

	public static void main(String[] args) throws IOException {
		runlast = false;
		record = true;
		print(board.toString());
		
		if (runlast) { // repeat last game
			File f = new File("MoveList.txt");
			Scanner tscan = new Scanner(f);
			while (tscan.hasNextLine()) {
				try {
					move(tscan.nextLine().toUpperCase());
				} catch (Exception e) {
					System.out.println(e.getStackTrace());
				}
			}
			tscan.close();
		}
		
		/*
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		        window = new GameWindow(board, self);
		    }
		}); */
		
        Scanner scan = new Scanner(System.in);
        
        window = new GameWindow(board, self);
        
		while (true) {
			/*
			if (scan.hasNextLine()) {
				input = scan.nextLine();
				move(input.toUpperCase());
			}*/
			if(movesIN.length() >= 5) {
				move(movesIN);
				movesIN = new String();
				window.update();
			}
		}
	}
}

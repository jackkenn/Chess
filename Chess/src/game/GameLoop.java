package game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import bots.AI;
import bots.UltraInstictStockFish;
import bots.UltraInstictStockFish2;
import game.Board.Cord;
import game.Board.Spot;

public class GameLoop {
	private static Board board;
	private static Scanner scan = new Scanner(System.in);
	private static String input = new String();
	private static ArrayList<String> inputList = new ArrayList<String>();
	private static boolean runlast = false;
	private static boolean record = false;
	private static BufferedWriter moveList;
	private static GameWindow window;
	private static AI white;
	private static AI black;
	public static String movesIN = new String();
	public static boolean nextTurn = false;
	private static int numberOfMoves = 0;
	private static Long delta;

	public GameLoop() {
		board = new Board();
	}

	public Board getBoard() {
		return board;
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
			Player player = curSpot.piece.getPlayer();
			if (player.turn) {
				Cord nextCord = board.new Cord(scan.hasNext() ? scan.next() : "00");
				if (nextCord.row < board.grid.size() - 1 && nextCord.row > 0
						&& nextCord.column < board.grid.get(0).size() - 1 && nextCord.column > 0) {
					Spot nextSpot = board.getSpot(nextCord);
					if (player.isLegalMove(curSpot, nextSpot)) {
						inputList.add(curCord.cord + " " + nextCord.cord);
						if (record) {
							moveList = new BufferedWriter(new FileWriter("MoveList.txt"));
							if (black != null) {
								moveList.write("SEED: " + black.getSeed() + "\n");
							}
							for (String s : inputList) {
								moveList.write(s + "\n");
							}
							moveList.close();
						}
						board.getPiece(curCord).move(nextSpot);
						player.endTurn();
						print(board.toString() + "\n\n");
						scan.close();
						return true;
					} else {
						print("Illegal Move\n");
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

	public boolean move(Spot cur, Spot next) {
		Player player = board.getPiece(cur.cord).getPlayer();
		if (player.isLegalMove(board.getSpot(cur.cord), board.getSpot(next.cord))) {
			inputList.add(cur.cord + " " + next.cord);
			if (record) {
				try {
					moveList = new BufferedWriter(new FileWriter("MoveList.txt"));
					if (black != null) {
						moveList.write("SEED: " + black.getSeed() + "\n");
					}
					for (String s : inputList) {
						moveList.write(s + "\n");
					}
					moveList.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			board.getPiece(cur.cord).move(board.getSpot(next.cord));
			player.endTurn();
			print(board.toString() + "\n\n");
			return true;
		} else {
			print("Illegal Move\n");
			return false;
		}
	}

	public boolean move(Spot[] s) {
		if (s.length == 2) {
			return move(s[0], s[1]);
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		GameLoop gameloop = new GameLoop();
		gameloop.runlast = false;
		gameloop.record = true;

		gameloop.window = new GameWindow(board, gameloop);
		print(board.toString());

		if (gameloop.runlast) { // repeat last game
			File f = new File("MoveList.txt");
			Scanner tscan = new Scanner(f);
			while (tscan.hasNextLine()) {
				try {
					String str = tscan.nextLine().toUpperCase();
					if (new String(str.copyValueOf(str.toCharArray(), 0, 5)).contentEquals("SEED:")) {
						Scanner seedScan = new Scanner(new String(str.copyValueOf(str.toCharArray(), 6, str.length() - 6)));
						Long Seed = seedScan.nextLong();
						white = new UltraInstictStockFish(gameloop, true, Seed);
						black = new UltraInstictStockFish2(gameloop, false, Seed);
						
					} else {
						gameloop.move(str);
						white.next();
						black.next();
						gameloop.numberOfMoves++;
					}
				} catch (Exception e) {
					System.out.println(e.getStackTrace());
				}
			}
			tscan.close();
		} else {
			white = new UltraInstictStockFish2(gameloop, true, System.currentTimeMillis());
			black = new UltraInstictStockFish(gameloop, false, System.currentTimeMillis());
		}
		
		while (!board.white.mated && !board.black.mated) {
			gameloop.window.update();
			if(nextTurn) {
				if(delta == null) {
					delta = System.currentTimeMillis();
				}
				/*if (gameloop.movesIN.length() >= 5) {
					gameloop.move(movesIN);
					gameloop.movesIN = new String();
					gameloop.window.unselectedAll();
					while (!bot.move());
				}*/
				if(board.white.turn) {
					white.move();
				} else {
					black.move();
				}
				gameloop.window.unselectedAll();
				//nextTurn = false;
				gameloop.numberOfMoves++;
			}
			if(numberOfMoves>200) {
				break;
			}
		}
		gameloop.window.unselectedAll();
		gameloop.window.update();
		gameloop.delta = (System.currentTimeMillis() - gameloop.delta);
		System.out.println("Total time to run: " + (delta / 1000.0));
		System.out.println("Total number of moves: " + gameloop.numberOfMoves);
	}
}

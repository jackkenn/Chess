package game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import bots.AI;
import bots.UltraInstictStockFish;
import game.Board.Cord;
import game.Board.Spot;
import game.pieces.Piece;

public class GameLoop {
	private Board board;
	private Scanner scan = new Scanner(System.in);
	private String input = new String();
	private ArrayList<String> inputList = new ArrayList<String>();
	private boolean runlast = true;
	private boolean record = false;
	private BufferedWriter moveList;
	private GameWindow window;
	public UltraInstictStockFish white;
	public UltraInstictStockFish black;
	public static String movesIN = new String();
	public boolean nextTurn = false;
	private int numberOfMoves = 0;
	private Long delta;

	public GameLoop() {
	}

	public Board getBoard() {
		return board;
	}

	private static void print(String s) {
		System.out.print(s);
	}

	private boolean move(String move) throws IOException {
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
						print(board.toString() + "\n");
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
		int numberOfMoves = 0;
		GameLoop gameloop = new GameLoop();
		gameloop.board = new Board();
		gameloop.runlast = false;
		gameloop.record = true;

		gameloop.window = new GameWindow(gameloop.board, gameloop);
		print(gameloop.board.toString());

		if (gameloop.runlast) { // repeat last game
			File f = new File("MoveList.txt");
			Scanner tscan = new Scanner(f);
			while (tscan.hasNextLine()) {
				try {
					String str = tscan.nextLine().toUpperCase();
					if (new String(str.copyValueOf(str.toCharArray(), 0, 5)).contentEquals("SEED:")) {
						Scanner seedScan = new Scanner(
								new String(str.copyValueOf(str.toCharArray(), 6, str.length() - 6)));
						Long Seed = seedScan.nextLong();
						gameloop.white = new UltraInstictStockFish(gameloop, true, Seed);
						gameloop.black = new UltraInstictStockFish(gameloop, false, Seed);

					} else {
						gameloop.move(str);
						gameloop.white.next();
						gameloop.black.next();
						gameloop.numberOfMoves++;
					}
				} catch (Exception e) {
					System.out.println(e.getStackTrace());
				}
			}
			tscan.close();
		} else {
			if(args.length > 0) {
				gameloop.white = new UltraInstictStockFish(gameloop, true, System.currentTimeMillis(), args[0], new Scanner(args[1]).nextInt());
				gameloop.black = new UltraInstictStockFish(gameloop, false, System.currentTimeMillis(), args[2], new Scanner(args[3]).nextInt());
			} else {
				gameloop.white = new UltraInstictStockFish(gameloop, true, System.currentTimeMillis());
				//gameloop.black = new UltraInstictStockFish(gameloop, false, System.currentTimeMillis());
			}
		}

		while (!gameloop.board.white.mated && !gameloop.board.black.mated && !gameloop.board.white.staleMated && !gameloop.board.black.staleMated) {
			gameloop.window.update();
			//if (nextTurn) {
				if (gameloop.delta == null) {
					gameloop.delta = System.currentTimeMillis();
				}
				
				if (gameloop.movesIN.length() >= 5) { 
					 gameloop.move(movesIN); 
					 gameloop.movesIN = new String();
					 gameloop.window.unselectedAll();
					 gameloop.window.update();
				}
				if (gameloop.board.white.turn) {
					gameloop.white.move();
					System.out.println("Black's turn");
					gameloop.numberOfMoves++;
				} else if(gameloop.board.black.turn) {
					//gameloop.black.move();
					//System.out.println("White's turn");
					//gameloop.numberOfMoves++;
				}
				 //nextTurn = false;
			//}
			if (gameloop.numberOfMoves > 150) {
				System.out.println("Game has exceeded max turns");
				/*
				int whiteValue = 0;
				int blackValue = 0;
				
				for(Piece p : gameloop.white.self.pieces) {
					whiteValue += p.getValue();
				}
				for(Piece p : gameloop.black.self.pieces) {
					blackValue += p.getValue();
				}
				if(whiteValue>blackValue) {
					(gameloop.black).writeWeights();
				} else {
					(gameloop.white).writeWeights();
				}
				break;
				*/
			}
		}
		if (gameloop.board.white.mated) {
			System.out.println("White has been CheckMated");
			//(gameloop.white).writeWeights();
		} else if (gameloop.board.white.staleMated) {
			System.out.println("White has been StaleMated");
		} else if (gameloop.board.black.mated) {
			System.out.println("Black has been CheckMated");
			//(gameloop.black).writeWeights();
		} else if (gameloop.board.black.staleMated) {
			System.out.println("Black has been StaleMated");
		}
		gameloop.window.update();
		gameloop.delta = (System.currentTimeMillis() - gameloop.delta);
		System.out.println("Total time to run: " + (gameloop.delta / 1000.0));
		System.out.println("Total number of moves: " + gameloop.numberOfMoves);
		//gameloop.window.setVisible(false);
	}
}

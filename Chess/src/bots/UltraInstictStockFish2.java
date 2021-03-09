package bots;

import java.util.ArrayList;
import java.util.Comparator;

import game.Board;
import game.Board.Spot;
import game.GameLoop;
import game.Player;
import game.pieces.Piece;

public class UltraInstictStockFish2 extends AI {
	private int turn = 0;
	private ArrayList<RankedMove> moves = new ArrayList<RankedMove>();
	private double pieceWeight = 3; // Material value * this
	private double spotWeight = 0.12; // where it is
	private double movesWeight = 0.02; // where it can go
	private double pinningWeight = 0.5; // pinning
	private double protectWeight = 0.75; // protecting
	private double attackingWeight = 1.25; // can capture
	private double attackedWeight = 1; // can be captured
	private double selfPreference = 1.05; // our board value * this
	private double checkWeight = 3; // putting opp in check
	private double futureWeight = 0.95; //

	public UltraInstictStockFish2(GameLoop game, boolean isWhite, Long seed) {
		super(game, isWhite, seed);
		if(isWhite) {
			self.focus.clear();
			self.focus.add(game.getBoard().getSpot(4, 4));
			self.focus.add(game.getBoard().getSpot(4, 5));
			self.opponent.focus.clear();
			self.opponent.focus.add(game.getBoard().getSpot(5, 5));
			self.opponent.focus.add(game.getBoard().getSpot(5, 4));
			
		} else {
			self.focus.clear();
			self.focus.add(game.getBoard().getSpot(5, 5));
			self.focus.add(game.getBoard().getSpot(5, 4));
			self.opponent.focus.clear();
			self.opponent.focus.add(game.getBoard().getSpot(4, 4));
			self.opponent.focus.add(game.getBoard().getSpot(4, 5));
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean move() {
		if (self.mated || self.opponent.mated) {
			return true;
		} else if (self.turn) {
			moves.clear();
			Long delta = System.currentTimeMillis();
			moves.addAll(calcMoves(self, 2, true));
			Comparator<RankedMove> comp = new Comparator<RankedMove>() {

				@Override
				public int compare(RankedMove o1, RankedMove o2) {
					return (int) Math
							.round(((o2.value[0] + o2.value[1]) * 1000) - ((o1.value[0] + o1.value[1]) * 1000));
				}

			};
			moves.sort(comp);
			for (RankedMove move : moves) {
				if (game.move(move.move[0], move.move[1])) {
					System.out.println("Calculation time taken: " + ((System.currentTimeMillis() - delta) / 1000.0));
					turn++;
					return true;
				}
			}
			System.out.println("There be an Error");
			return true;
		} else {
			return true;
		}
	}

	private ArrayList<RankedMove> calcMoves(Player p, int numOfMoves, boolean firstIter) {
		ArrayList<RankedMove> moves = new ArrayList<RankedMove>();
		for (Spot initMove[] : p.legalMoves) {
			if (self.isLegalMove(initMove[0], initMove[1])) {
				Board initBoard = new Board(p.board);
				Player fSelf = initBoard.getPiece(initMove[0].cord).getPlayer();
				Board postBoard = null;
				Player fOpp = null;
				if(fSelf.king != null) {
					setFocus(fSelf.king);
				}
				initBoard.getPiece(initMove[0].cord).move(initBoard.getSpot(initMove[1].cord));
				fSelf.endTurn();
				double valueOfBoard[] = new double[] { 0, 0 };
				if (fSelf.opponent.mated) {
					moves.add(new RankedMove(initMove, new double[] { 100, 100 }, fSelf));
				} else {
					ArrayList<RankedMove> oppMoves = new ArrayList<RankedMove>();
					for (Spot oppMove[] : fSelf.opponent.legalMoves) {
						if (self.isLegalMove(initMove[0], initMove[1])) {
							postBoard = new Board(initBoard);
							fOpp = postBoard.getPiece(oppMove[0].cord).getPlayer();
							if(fOpp.king != null) {
								setFocus(fOpp.king);
							}
							postBoard.getPiece(oppMove[0].cord).move(postBoard.getSpot(oppMove[1].cord));
							fOpp.endTurn();
							valueOfBoard = valueOfBoard(fOpp.opponent);
							valueOfBoard[0] = valueOfBoard[0] * selfPreference;
							valueOfBoard[1] = valueOfBoard[1] * selfPreference;
							double temp[] = valueOfBoard(fOpp);
							valueOfBoard[0] -= temp[0];
							valueOfBoard[1] -= temp[1];
							oppMoves.add(new RankedMove(initMove, valueOfBoard, fOpp.opponent));
						}
					}
					Comparator<RankedMove> opComp = new Comparator<RankedMove>() {

						@Override
						public int compare(RankedMove o1, RankedMove o2) {
							return (int) Math
									.round(((o1.value[0] + o1.value[1]) * 1000) - ((o2.value[0] + o2.value[1]) * 1000));
						}

					};
					oppMoves.sort(opComp);
					moves.add(oppMoves.get(0));

				}
			}
		}
		Comparator<RankedMove> comp = new Comparator<RankedMove>() {

			@Override
			public int compare(RankedMove o1, RankedMove o2) {
				return (int) Math.round(((o2.value[0] + o2.value[1]) * 1000) - ((o1.value[0] + o1.value[1]) * 1000));
			}

		};
		moves.sort(comp);
		if (moves.size() > 6) {
			if (!firstIter) {
				ArrayList<RankedMove> temp = new ArrayList<RankedMove>();
				for (int i = 0; i < 6; i++) {
					temp.add(moves.get(i));
				}
				moves.clear();
				moves.addAll(temp);
			}
			if (numOfMoves > 0) {
				for (RankedMove rm : moves) {
					ArrayList<RankedMove> fMoves = calcMoves(rm.fSelf, numOfMoves - 1, false);
					if(fMoves.size()>0) {
						fMoves.sort(comp);
						rm.value[0] += fMoves.get(0).value[0] * futureWeight;
						rm.value[1] += fMoves.get(0).value[1] * futureWeight;
					}
				}
			}
		} else {
			if (numOfMoves > 0) {
				for (RankedMove rm : moves) {
					ArrayList<RankedMove> fMoves = calcMoves(rm.fSelf, numOfMoves - 1, false);
					if(fMoves.size()>0) {
						fMoves.sort(comp);
						rm.value[0] += (fMoves.get(0).value[0] / (double) fMoves.size()) * futureWeight;
						rm.value[1] += (fMoves.get(0).value[1] / (double) fMoves.size()) * futureWeight;
					}
				}
			}
		}
		return moves;
	}

	private double[] valueOfBoard(Player player) {
		double matValue = 0;
		double posValue = player.legalMoves.size() * movesWeight;
		for (Piece p : player.pieces) {
			if (p.getType() != Board.PieceType.KING) {
				matValue += p.getValue();
			}
			for (Piece[] piece : p.pinning) {
				if (piece[0].getPlayer().equals(self.opponent)) {
					if (piece[0].getType() == Board.PieceType.KING) {
						posValue += checkWeight;
					} else {
						posValue += attacking(piece[0]) * attackingWeight;
						if (piece.length == 2) {
							if (!piece[1].isProtected(piece[1].getPlayer()) || p.getValue() < piece[1].getValue()) {
								posValue += piece[1].getValue() * pinningWeight;
							}
						}
					}
				} else {
					if (piece[0].getType() != Board.PieceType.KING) {
						posValue += attacking(piece[0]) * protectWeight; // need to add defending case;
					}
				}
			}
			if (p.getType() != Board.PieceType.KING) {
				posValue -= distToFocus(p) * spotWeight * Math.max(3 - (turn/2), 1);
				if(!p.moved) {
					posValue -= distToFocus(p) * (spotWeight/2);
				}
				posValue -= attacking(p) * attackedWeight;
			}
		}
		return new double[] { matValue * pieceWeight, posValue };
	}

	private double distToFocus(Piece pieceToCheck) {
		double dist = 100;
		for (Spot s : pieceToCheck.getPlayer().focus) {
			int row = s.cord.row - pieceToCheck.getSpot().cord.row;
			int column = s.cord.column - pieceToCheck.getSpot().cord.column;
			double temp = Math.sqrt((row * row) + (column * column));
			dist = (temp < dist ? temp : dist); // prob not needed
		}
		return dist;
	}
	
	private void setFocus(Piece p) {
		if(turn > 6) {
			p.getPlayer().focus.add(p.getPlayer().king.getSpot());
		}
	}

	private int attacking(Piece piece) { // does not check legal moves
		int attackers = 0;
		int deffenders = 0;
		Piece aList[] = new Piece[piece.pinned.size()];
		Piece dList[] = new Piece[piece.pinned.size()];
		for (Piece[] p : piece.pinned) {
			if (p[0].getPlayer().equals(piece.getPlayer())) {
				dList[deffenders] = p[0];
				deffenders++;
			} else {
				aList[attackers] = p[0];
				attackers++;
			}
		}
		Comparator<Object> comp = new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				return ((Piece) o1).getValue() - ((Piece) o2).getValue();
			}

		};
		if (attackers > 1) {
			sort(aList, comp, attackers - 1);
		}
		if (deffenders > 1) {
			sort(dList, comp, deffenders - 1);
		}
		int value = attackers > 0 ? piece.getValue() : 0;
		if (attackers > 0 && deffenders > 0) {
			int values[] = new int[piece.pinned.size() + 1];
			values[0] = value;
			int i = 0;
			int j = 1;
			while (i < Math.min(attackers, deffenders)) {
				value -= aList[i].getValue();
				values[j] = value;
				j++;
				if (i < attackers) {
					value += dList[i].getValue();
					values[j] = value;
					j++;
				}
				i++;
			}
			return bestCapture(values, true, j);
		}
		return value;
	}

	private int bestCapture(int arr[], boolean findLow, int Endindex) {
		int index = 0;
		if (findLow) {
			for (int i = 0; i < Endindex / 2; i += 2) {
				if (arr[i] < arr[index]) {
					index = i;
				}
			}
			return bestCapture(arr, !findLow, index);
		} else {
			for (int i = 1; i < Endindex / 2; i += 2) {
				if (arr[i] > arr[index]) {
					index = i;
				}
			}
			return index;
		}
	}

	private class RankedMove {
		public Spot move[];
		public double value[];
		public Player fSelf;

		public RankedMove(Spot move[], double[] value, Player self) {
			this.move = move;
			this.value = value;
			this.fSelf = self;
		}

		public String toString() {
			return new String(value[0] + ", " + value[1]);
		}
	}
}

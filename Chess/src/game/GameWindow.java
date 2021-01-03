package game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Board.PieceType;
import game.Board.Spot;

public class GameWindow extends javax.swing.JFrame implements WindowListener {
	public Board board;
	public WindowSpot windowGrid[][] = new WindowSpot[8][8];
	public GameLoop gameLoop;

	public GameWindow(Board givenBoard, GameLoop givenGameLoop) {
		super("Chess");
		board = givenBoard;
		gameLoop = givenGameLoop;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(560, 580);
		this.setLocationRelativeTo(null);
		Image icon = new javax.swing.ImageIcon("graphics/board.png").getImage();
		this.setIconImage(icon);
		this.setResizable(false);
		this.setLayout(null);
		boolean flag = true;

		for (int i = 0; i < 8; i++) {
			flag = !flag;
			for (int j = 0; j < 8; j++) {
				if (flag) {
					windowGrid[i][j] = new WindowSpot((i * 60) + 30, (j * 60) + 30, board.getSpot(i + 1, j + 1), this);
					this.add(windowGrid[i][j]);
					
					JPanel panel = (JPanel) this.getContentPane();
					JLabel label = new JLabel();
					label.setIcon(new ImageIcon("graphics/BlackTile.png"));
					label.setBounds((i * 60) + 30, (j * 60) + 30, 60, 60);
					panel.add(label);
					flag = !flag;
				} else {
					windowGrid[i][j] = new WindowSpot((i * 60) + 30, (j * 60) + 30, board.getSpot(i + 1, j + 1), this);
					this.add(windowGrid[i][j]);
					
					JPanel panel = (JPanel) this.getContentPane();
					JLabel label = new JLabel();
					label.setIcon(new ImageIcon("graphics/WhiteTile.png"));
					label.setBounds((i * 60) + 30, (j * 60) + 30, 60, 60);
					panel.add(label);
					flag = !flag;
				}
			}
		}
		this.setVisible(true);
	}
	
	public void update() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				windowGrid[i][j].updateIcon();
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("About to close the window");

	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("The window has been closed");

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public class WindowSpot extends JButton {
		public Spot spot;
		private GameWindow window;

		public WindowSpot(int givenX, int givenY, Spot givenSpot, GameWindow givenWindow) {
			this.setBounds(givenX, givenY, 60, 60);
			this.window = givenWindow;
			this.spot = givenSpot;
			this.setBorder(null);
			setContentAreaFilled(false);
			setBorderPainted(false);
			updateIcon();
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(spot.cord.toString());
			        if(GameLoop.movesIN.length() > 0) {
			        	GameLoop.movesIN += spot.cord.toString() + "\n";
			        } else {
			        	GameLoop.movesIN = spot.cord.toString() + " ";
			        }
					updateIcon();
				}
			});

		}
		
		private void updateIcon() {
			if (spot.piece.getType() == PieceType.PAWN) {
				if (spot.piece.getPlayer().DIRECTION == 1) {
					this.setIcon(new ImageIcon("graphics/WhitePawn.png"));
				} else {
					this.setIcon(new ImageIcon("graphics/BlackPawn.png"));
				}
			} else if (spot.piece.getType() == PieceType.ROOK) {
				if (spot.piece.getPlayer().DIRECTION == 1) {
					this.setIcon(new ImageIcon("graphics/WhiteRook.png"));
				} else {
					this.setIcon(new ImageIcon("graphics/BlackRook.png"));
				}
			} else if (spot.piece.getType() == PieceType.KNIGHT) {
				if (spot.piece.getPlayer().DIRECTION == 1) {
					this.setIcon(new ImageIcon("graphics/WhiteKnight.png"));
				} else {
					this.setIcon(new ImageIcon("graphics/BlackKnight.png"));
				}
			} else if (spot.piece.getType() == PieceType.BISHOP) {
				if (spot.piece.getPlayer().DIRECTION == 1) {
					this.setIcon(new ImageIcon("graphics/WhiteBishop.png"));
				} else {
					this.setIcon(new ImageIcon("graphics/BlackBishop.png"));
				}
			} else if (spot.piece.getType() == PieceType.KING) {
				if (spot.piece.getPlayer().DIRECTION == 1) {
					this.setIcon(new ImageIcon("graphics/WhiteKing.png"));
				} else {
					this.setIcon(new ImageIcon("graphics/BlackKing.png"));
				}
			} else if (spot.piece.getType() == PieceType.QUEEN) {
				if (spot.piece.getPlayer().DIRECTION == 1) {
					this.setIcon(new ImageIcon("graphics/WhiteQueen.png"));
				} else {
					this.setIcon(new ImageIcon("graphics/BlackQueen.png"));
				}
			} else {
				this.setIcon(null);
			}
		}

	}

}

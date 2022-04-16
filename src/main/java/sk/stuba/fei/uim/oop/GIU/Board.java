
package sk.stuba.fei.uim.oop.GIU;

import sk.stuba.fei.uim.oop.MECHANIC.Reversi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Board extends javax.swing.JPanel implements MouseListener {


	/* constants for drawing */
	private static final int BOARD_OFFSET = 20;

	private static final int BORDER_OFFSET = 30;

	private static int SQUARE_WIDTH = 60;



	private static final int BORDER_BOARD_OFFSET = BORDER_OFFSET + BOARD_OFFSET;




	private static final int BOARD_SIZE_PIXEL = (BOARD_OFFSET + BORDER_OFFSET) * 2 +
			SQUARE_WIDTH * Reversi.B_size;


	private static final int PIECE_WIDTH = 35;


	private static int SQUARE_OFFSET = (SQUARE_WIDTH - PIECE_WIDTH) >> 1;

	private static final int DIVIDE_WIDTH = 1;

	private static final int BORDER_WIDTH = 3;




	private static final Color BOARD_COLOR = new Color(13, 99, 145, 255);


	private static final Color DIVIDE_COLOR = Color.BLACK;

	private static final Color SUGGEST_WHITE_COLOR = new Color(255, 255, 255, 80);

	private static final Color SUGGEST_BLACK_COLOR = new Color(0, 0, 0, 50);




	private static final Color BOARD_TEXT_COLOR = Color.WHITE;


	private static final Font BOARD_FONT = new Font("SansSerif", Font.BOLD, 16);

	private static String[] BOARD_ROW_TEXT;

	private static String[] BOARD_COL_TEXT;



	/*--------------------------------------*/


	//Images

	public static BufferedImage BlackPieceImage = null;


	public static BufferedImage WhitePieceImage = null;

	private void setPoints() {
		BOARD_COL_TEXT = new String[Reversi.B_size];
		BOARD_ROW_TEXT = new String[Reversi.B_size];

		for (int i = 0; i < Reversi.B_size; i++) {
			BOARD_COL_TEXT[i] = String.valueOf((char) (i + 65));
			BOARD_ROW_TEXT[i] = String.valueOf(i + 1);
		}
	}

	@Override
	public void paint(Graphics g) {
		// draw the board
		g.setColor(BOARD_COLOR);
		g.fillRect(0, 0, BOARD_SIZE_PIXEL, BOARD_SIZE_PIXEL);

		// board's texts
		g.setColor(BOARD_TEXT_COLOR);
		g.setFont(BOARD_FONT);
		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fm = g2d.getFontMetrics();

		for (int i = 0; i < BOARD_COL_TEXT.length; ++i) {
			g.drawString(BOARD_COL_TEXT[i],
					BORDER_BOARD_OFFSET + SQUARE_WIDTH * i +
							(SQUARE_WIDTH - fm.stringWidth(BOARD_COL_TEXT[i])) / 2,
					(BORDER_OFFSET + fm.getHeight() / 2) / 2);

			g.drawString(BOARD_ROW_TEXT[i],
					(BORDER_OFFSET - fm.stringWidth(BOARD_ROW_TEXT[i])) / 2,
					BORDER_BOARD_OFFSET + SQUARE_WIDTH * i +
							(SQUARE_WIDTH + fm.getHeight()) / 2);
		}

		//border
		g.setColor(DIVIDE_COLOR);
		g.fillRect(BORDER_OFFSET, BORDER_OFFSET, BORDER_WIDTH, BOARD_SIZE_PIXEL - BORDER_OFFSET * 2);
		g.fillRect(BORDER_OFFSET, BORDER_OFFSET, BOARD_SIZE_PIXEL - BORDER_OFFSET * 2, BORDER_WIDTH);
		g.fillRect(BORDER_OFFSET, BOARD_SIZE_PIXEL - BORDER_OFFSET,
				BOARD_SIZE_PIXEL - BORDER_OFFSET * 2 + BORDER_WIDTH, BORDER_WIDTH);
		g.fillRect(BOARD_SIZE_PIXEL - BORDER_OFFSET, BORDER_OFFSET,
				BORDER_WIDTH, BOARD_SIZE_PIXEL - BORDER_OFFSET * 2 + BORDER_WIDTH);

		// Squares' dividers

		for (int i = 0; i <= Reversi.B_size; ++i) {
			g.fillRect(i * SQUARE_WIDTH + BORDER_BOARD_OFFSET, BORDER_BOARD_OFFSET,
					DIVIDE_WIDTH, SQUARE_WIDTH * Reversi.B_size);
		}

		for (int i = 0; i <= Reversi.B_size; ++i) {
			g.fillRect(BORDER_BOARD_OFFSET, i * SQUARE_WIDTH + BORDER_BOARD_OFFSET,
					SQUARE_WIDTH * Reversi.B_size, DIVIDE_WIDTH);
		}

		char[][] board = Reversi.getInstance().getBoard();
		// Pieces
		for (int r = 0; r < Reversi.B_size; ++r)
			for (int c = 0; c < Reversi.B_size; ++c) {
				if (Reversi.getInstance().isNewPiece(r, c)) {
					drwNewPiece(g, r, c);
				}

				if (Reversi.getInstance().isEffectedPiece(r, c)) {
					drwEffectedPiece(g, r, c);
				}

				if (board[r][c] == Reversi.B_piece) {
					drwPiece(g, BlackPieceImage, r, c);

				} else if (board[r][c] == Reversi.W_piece) {
					drwPiece(g, WhitePieceImage, r, c);
				} else if (board[r][c] == Reversi.SUGGEST_B_piece) {
					g.setColor(SUGGEST_BLACK_COLOR);
					drwSuggestedPiece(g, r, c);
				} else if (board[r][c] == Reversi.SUGGEST_W_piece) {
					g.setColor(SUGGEST_WHITE_COLOR);
					drwSuggestedPiece(g, r, c);
				} else
					continue;

			}
	}


	public static void changeBoardScale() {
		SQUARE_WIDTH = 480 / Reversi.B_size;
		SQUARE_OFFSET = (SQUARE_WIDTH - PIECE_WIDTH) >> 1;
	}

	private void init() {
		// Image for pieces
		try {
			WhitePieceImage = ImageIO.read(getClass().getResourceAsStream("/whitepiece.png"));
			BlackPieceImage = ImageIO.read(getClass().getResourceAsStream("/blackpiece.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void drwPiece(Graphics g, BufferedImage img, int row, int col) {
		g.drawImage(img,
				(col * SQUARE_WIDTH) + SQUARE_OFFSET + BORDER_BOARD_OFFSET,
				(row * SQUARE_WIDTH) + SQUARE_OFFSET + BORDER_BOARD_OFFSET, null);
	}

	private void drwEffectedPiece(Graphics g, int row, int col) {
		g.setColor(new Color(0, 255, 0, 80));
		g.fillRect((col * SQUARE_WIDTH) + BORDER_BOARD_OFFSET + DIVIDE_WIDTH,
				(row * SQUARE_WIDTH) + BORDER_BOARD_OFFSET + DIVIDE_WIDTH,
				SQUARE_WIDTH - DIVIDE_WIDTH, SQUARE_WIDTH - DIVIDE_WIDTH);
	}

	private void drwNewPiece(Graphics g, int row, int col) {
		g.setColor(Color.GREEN);
		g.fillRect((col * SQUARE_WIDTH) + BORDER_BOARD_OFFSET + DIVIDE_WIDTH,
				(row * SQUARE_WIDTH) + BORDER_BOARD_OFFSET + DIVIDE_WIDTH,
				SQUARE_WIDTH - DIVIDE_WIDTH, SQUARE_WIDTH - DIVIDE_WIDTH);
	}



	private void drwSuggestedPiece(Graphics g, int row, int col) {
		g.fillOval((col * SQUARE_WIDTH) + SQUARE_OFFSET + BORDER_BOARD_OFFSET,
				(row * SQUARE_WIDTH) + SQUARE_OFFSET + BORDER_BOARD_OFFSET,
				PIECE_WIDTH, PIECE_WIDTH);
	}



	public Board() {

		setPoints();
		addMouseListener(this);


		this.setPreferredSize(new Dimension(BOARD_SIZE_PIXEL, BOARD_SIZE_PIXEL));

		init();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (Reversi.getInstance().getGameState() == Reversi.inProcess &&
				!Reversi.getInstance().getIsCompTurn()) {
			int col = (e.getX() - BORDER_BOARD_OFFSET) / SQUARE_WIDTH;
			int row = (e.getY() - BORDER_BOARD_OFFSET) / SQUARE_WIDTH;
			if (row >= 0 && row < Reversi.B_size && col >= 0 && col < Reversi.B_size)
				Reversi.getInstance().movePiece(row, col);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {


	}

	@Override
	public void mouseEntered(MouseEvent arg0) {


	}

	@Override
	public void mouseExited(MouseEvent arg0) {


	}

	@Override
	public void mousePressed(MouseEvent arg0) {


	}
}

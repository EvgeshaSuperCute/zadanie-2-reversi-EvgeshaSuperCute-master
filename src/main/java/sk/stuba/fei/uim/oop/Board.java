
package sk.stuba.fei.uim.oop;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board extends javax.swing.JPanel implements MouseListener {


	/******************************/
	/* CONSTANT FOR DRAWING BOARD */
	/******************************/
	/** size of board */
	private static final int sBOARD_SIZE = Reversi.sBOARD_SIZE;

	/** border offset */
	private static final int sBORDER_OFFSET = 30;

	/** board offset */
	private static final int sBOARD_OFFSET = 20;

	/** border and board offset */
	private static final int sBORDER_BOARD_OFFSET = sBORDER_OFFSET + sBOARD_OFFSET;

	/** size of a square. */
	private static final int sSQUARE_WIDTH = 45;

	/** size of the board in pixels */
	private static final int sBOARD_SIZE_PIXEL = (sBOARD_OFFSET + sBORDER_OFFSET) * 2 +
													sSQUARE_WIDTH * sBOARD_SIZE;

	/** radius of a piece */
	private static final int sPIECE_WIDTH = 35;

	/** offset piece inside a square */
	private static final int sSQUARE_OFFSET = (sSQUARE_WIDTH - sPIECE_WIDTH) >> 1;

	/** width of the border */
	private static final int sBORDER_WIDTH = 3;

	/** width of the division between square */
	private static final int sDIVIDE_WIDTH = 1;

	/** board color */
	private static final Color sBOARD_COLOR = new Color(13, 99, 145, 255);

	/** squares' divide color */
	private static final Color sDIVIDE_COLOR = Color.BLACK;

	/** color for susggest black piece */
	private static final Color sSUGGEST_BLACK_COLOR = new Color(0, 0, 0, 50);

	/** color for susggest white piece */
	private static final Color sSUGGEST_WHITE_COLOR = new Color(255, 255, 255, 80);

	/** color for board's texts */
	private static final Color sBOARD_TEXT_COLOR = Color.WHITE;

	/** font for board's texts */
	private static final Font sBOARD_FONT = new Font("SansSerif", Font.BOLD, 16);

	/** board column texts */
	private static  String[] sBOARD_COL_TEXT;// = {"A", "B", "C", "D", "E", "F", "G", "H"};

	/** board row texts */
	private static  String[] sBOARD_ROW_TEXT;// = {"1", "2", "3", "4", "5", "6", "7", "8"};

	/***********************************/
	/* END CONSTANTS FOR DRAWING BOARD */
	/***********************************/

	/** image of black piece */
	public static BufferedImage mBlackPieceImg = null;

	/** image of white piece */
	public static BufferedImage mWhitePieceImg = null;


	private void setPoints(){
		sBOARD_COL_TEXT = new String[sBOARD_SIZE];
		sBOARD_ROW_TEXT = new String[sBOARD_SIZE];

		for (int i = 0; i < sBOARD_SIZE; i++){
			sBOARD_COL_TEXT[i] = String.valueOf((char)(i + 65));
			sBOARD_ROW_TEXT[i] = String.valueOf(i+1);
		}
	}

    /**
     * Constructor
     * param reversiPanel
     * param reversiPanel the game frame
     */
    public Board() {
        // add mouse listener
		setPoints();
        addMouseListener(this);

        // set panel size to board size
        this.setPreferredSize(new Dimension(sBOARD_SIZE_PIXEL, sBOARD_SIZE_PIXEL));

        init();
    }

    private void init() {
    	// load image for pieces
		try {
			mBlackPieceImg = ImageIO.read(this.getClass().getResourceAsStream("C:\\Users\\Visgraat\\OOP\\zadanie-2-reversi-EvgeshaSuperCute-master\\images\\blackpiece.png"));
			mWhitePieceImg = ImageIO.read(this.getClass().getResourceAsStream("/img/whitepiece.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Draw the board
	 */
	@Override
	public void paint(Graphics g) {
		// draw the board with board color
		g.setColor(sBOARD_COLOR);
		g.fillRect(0, 0, sBOARD_SIZE_PIXEL, sBOARD_SIZE_PIXEL);

		// draw board's texts
		g.setColor(sBOARD_TEXT_COLOR);
		g.setFont(sBOARD_FONT);
		Graphics2D g2d = (Graphics2D)g;
		FontMetrics fm = g2d.getFontMetrics();

		for (int i = 0; i < sBOARD_COL_TEXT.length; ++i)
		{
			g.drawString(sBOARD_COL_TEXT[i],
					sBORDER_BOARD_OFFSET + sSQUARE_WIDTH * i +
					(sSQUARE_WIDTH - fm.stringWidth(sBOARD_COL_TEXT[i]))/2,
					(sBORDER_OFFSET + fm.getHeight()/2) / 2);

			g.drawString(sBOARD_ROW_TEXT[i],
					(sBORDER_OFFSET - fm.stringWidth(sBOARD_ROW_TEXT[i])) / 2,
					sBORDER_BOARD_OFFSET + sSQUARE_WIDTH * i +
					(sSQUARE_WIDTH + fm.getHeight()) / 2);
		}

		// draw border
		g.setColor(sDIVIDE_COLOR);
		g.fillRect(sBORDER_OFFSET, sBORDER_OFFSET, sBORDER_WIDTH, sBOARD_SIZE_PIXEL - sBORDER_OFFSET * 2);
		g.fillRect(sBORDER_OFFSET, sBORDER_OFFSET, sBOARD_SIZE_PIXEL - sBORDER_OFFSET * 2, sBORDER_WIDTH);
		g.fillRect(sBORDER_OFFSET, sBOARD_SIZE_PIXEL - sBORDER_OFFSET,
				sBOARD_SIZE_PIXEL - sBORDER_OFFSET * 2 + sBORDER_WIDTH, sBORDER_WIDTH);
		g.fillRect(sBOARD_SIZE_PIXEL - sBORDER_OFFSET, sBORDER_OFFSET,
				sBORDER_WIDTH, sBOARD_SIZE_PIXEL - sBORDER_OFFSET * 2 + sBORDER_WIDTH);

		// draw the squares' dividers
		// horizontal
		for (int i = 0; i <= sBOARD_SIZE; ++i) {
			g.fillRect(i * sSQUARE_WIDTH + sBORDER_BOARD_OFFSET, sBORDER_BOARD_OFFSET,
					sDIVIDE_WIDTH, sSQUARE_WIDTH * sBOARD_SIZE);
		}
		// vertical
		for (int i = 0; i <= sBOARD_SIZE; ++i) {
			g.fillRect(sBORDER_BOARD_OFFSET, i * sSQUARE_WIDTH + sBORDER_BOARD_OFFSET,
					sSQUARE_WIDTH * sBOARD_SIZE, sDIVIDE_WIDTH);
		}

		char[][] board = Reversi.getInstance().getBoard();
		// draw pieces
		for (int r = 0; r < sBOARD_SIZE; ++r)
			for (int c = 0; c < sBOARD_SIZE; ++c) {
				if (Reversi.getInstance().isNewPiece(r, c)) {
					drawNewPiece(g,r,c);
				}

				if (Reversi.getInstance().isEffectedPiece(r, c)) {
					drawEffectedPiece(g, r, c);
				}

				if (board[r][c] == Reversi.sBLACK_PIECE) {
					drawPiece(g, mBlackPieceImg, r, c);

				} else if (board[r][c] == Reversi.sWHITE_PIECE) {
					drawPiece(g, mWhitePieceImg, r, c);
				}
				else if (board[r][c] == Reversi.sSUGGEST_BLACK_PIECE) {
					g.setColor(sSUGGEST_BLACK_COLOR);
					drawSuggestedPiece(g,r,c);
				}
				else if (board[r][c] == Reversi.sSUGGEST_WHITE_PIECE) {
					g.setColor(sSUGGEST_WHITE_COLOR);
					drawSuggestedPiece(g,r,c);
				}
				else
					continue;

			}
	}

	private void drawNewPiece(Graphics g, int row, int col) {
		g.setColor(Color.GREEN);
		g.fillRect((col * sSQUARE_WIDTH) + sBORDER_BOARD_OFFSET + sDIVIDE_WIDTH,
				(row * sSQUARE_WIDTH)	+ sBORDER_BOARD_OFFSET + sDIVIDE_WIDTH,
				sSQUARE_WIDTH - sDIVIDE_WIDTH, sSQUARE_WIDTH - sDIVIDE_WIDTH);
	}

	private void drawEffectedPiece(Graphics g, int row, int col) {
		g.setColor(new Color(0, 255, 0, 80));
		g.fillRect((col * sSQUARE_WIDTH) + sBORDER_BOARD_OFFSET + sDIVIDE_WIDTH,
				(row * sSQUARE_WIDTH)	+ sBORDER_BOARD_OFFSET + sDIVIDE_WIDTH,
				sSQUARE_WIDTH - sDIVIDE_WIDTH, sSQUARE_WIDTH - sDIVIDE_WIDTH);
	}

	private void drawSuggestedPiece(Graphics g, int row, int col) {
		g.fillOval(	(col * sSQUARE_WIDTH) + sSQUARE_OFFSET + sBORDER_BOARD_OFFSET,
				(row * sSQUARE_WIDTH)	+ sSQUARE_OFFSET + sBORDER_BOARD_OFFSET,
				sPIECE_WIDTH, sPIECE_WIDTH);
	}

	private void drawPiece(Graphics g, BufferedImage img, int row, int col) {
		g.drawImage(img,
				(col * sSQUARE_WIDTH) + sSQUARE_OFFSET + sBORDER_BOARD_OFFSET,
				(row * sSQUARE_WIDTH) + sSQUARE_OFFSET + sBORDER_BOARD_OFFSET, null);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (Reversi.getInstance().getGameState() == Reversi.PLAYING &&
				!Reversi.getInstance().getIsCompTurn()) {
			int col = (e.getX() - sBORDER_BOARD_OFFSET) / sSQUARE_WIDTH;
			int row = (e.getY() - sBORDER_BOARD_OFFSET) / sSQUARE_WIDTH;
			if (row >= 0 && row <sBOARD_SIZE && col >=0 && col < sBOARD_SIZE)
				Reversi.getInstance().movePiece(row, col);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}

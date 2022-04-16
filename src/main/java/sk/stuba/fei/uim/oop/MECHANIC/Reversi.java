
package sk.stuba.fei.uim.oop.MECHANIC;

import sk.stuba.fei.uim.oop.INTERFACE.Agent;
import sk.stuba.fei.uim.oop.INTERFACE.Agent.MoveCoord;
import sk.stuba.fei.uim.oop.GIU.ReversiPanel;

import java.util.ArrayList;
import java.util.Vector;

public class Reversi {

	/* Thе uniquе instаncе  */
	private static Reversi sInstance;

	/* Gаmе Stаte */
	public static final int inProcess = 0;
	public static final int endProcess = 1;

	public static int B_size = 8;

	/* susggеst piece for black */
	public static final char SUGGEST_B_piece = 'p';

	/* susggеst piece for white */
	public static final char SUGGEST_W_piece = 'u';

	/** piеce represеnts blаck */
	public static final char B_piece = 'b';

	/** piеce represents white */
	public static final char W_piece = 'w';




	public static final char EMPTY_PIECE = '-';


	private static final int[] MOVE_ROW = { -1, -1, -1, 0, 0, 1, 1, 1 }; // {-1, -1, -1, 0, 0, 1, 1, 1};


	private static final int[] MOVE_COL = { -1, 0, 1, -1, 1, -1, 0, 1 }; // {-1, 0, 1, -1, 1, -1, 0, 1};


	private static char[][] INIT_BOARD;

	private boolean BlackTurn = false;


	private boolean CompTurn = false;


	private char[][] Main_Board;



	private boolean[][] mIsEffectedPiece;

	private Vector<String> mMoveList;

	private int BlackScore;

	private int NewPieceRow;
	private int NewPieceCol;

	private int WhiteScore;

	private int state;

	private Agent mAIAgent;





	private void setInitBoard() {
		INIT_BOARD = new char[B_size][B_size];
		for (int i = 0; i < B_size; i++) {
			for (int j = 0; j < B_size; j++) {
				if ((i == B_size / 2 - 1 && j == B_size / 2 - 1) ||
						(i == B_size / 2 && j == B_size / 2)) {
					INIT_BOARD[i][j] = B_piece;
				} else if ((i == B_size / 2 - 1 && j == B_size / 2) ||
						(i == B_size / 2 && j == B_size / 2 - 1)) {
					INIT_BOARD[i][j] = W_piece;
				} else {
					INIT_BOARD[i][j] = EMPTY_PIECE;
				}
			}
		}

	}

	// _________________________________________________________________________________
	public static int[][] BOARD_VALUE_s;

	private void setWeights() {
		BOARD_VALUE_s = new int[B_size][B_size];

		for (int i = 0; i < B_size; i++) {
			for (int j = 0; j < B_size; j++) {
				if ((i == 0 && j == 0) || (i == 0 && j == B_size - 1) || (i == B_size - 1 && j == 0)
						|| (i == B_size - 1 && j == B_size - 1)) {
					BOARD_VALUE_s[i][j] = 99; // 99
				} else if ((i == 1 && j == 1) || (i == 1 && j == B_size - 2) || (i == B_size - 2 && j == 1)
						|| (i == B_size - 2 && j == B_size - 2)) {

					BOARD_VALUE_s[i][j] = -10; // -10
				} else if ((i == 0 && j == 2) ||
						(i == 2 && j == 0) ||
						(i == 0 && j == B_size - 3) ||
						(i == 2 && j == B_size - 1) ||
						(i == B_size - 3 && j == 0) ||
						(i == B_size - 1 && j == 2) ||
						(i == B_size - 3 && j == B_size - 1) ||
						(i == B_size - 1 && j == B_size - 3)) {
					BOARD_VALUE_s[i][j] = 5; // 5
				} else if ((i == 0 && j == 1) ||
						(i == 1 && j == 0) ||
						(i == 0 && j == B_size - 2) ||
						(i == 1 && j == B_size - 1) ||
						(i == B_size - 2 && j == 0) ||
						(i == B_size - 1 && j == 1) ||
						(i == B_size - 2 && j == B_size - 1) ||
						(i == B_size - 1 && j == B_size - 2)) {
					BOARD_VALUE_s[i][j] = -1;
				} else if ((i == B_size / 2 - 1 && j == B_size / 2 - 1) ||
						(i == B_size / 2 && j == B_size / 2) ||
						(i == B_size / 2 - 1 && j == B_size / 2) ||
						(i == B_size / 2 && j == B_size / 2 - 1)) {
					BOARD_VALUE_s[i][j] = 0;
				} else if ((i > 2 && i < B_size - 3 && j == 0) ||
						(i > 2 && i < B_size - 3 && j == B_size - 1) ||
						(j > 2 && j < B_size - 3 && i == 0) ||
						(j > 2 && j < B_size - 3 && i == B_size - 1)) {
					BOARD_VALUE_s[i][j] = 2;
				} else
					BOARD_VALUE_s[i][j] = 1;
			}
		}

	}

	// ---------------------------------------------------------------------------------

	private Reversi() {
		setInitBoard();
		setWeights();
		init();
	}

	public static Reversi getInstance() {
		if (sInstance == null) {
			sInstance = new Reversi();
		}

		return sInstance;
	}


	private void init() {

		Main_Board = new char[B_size][B_size];

		mIsEffectedPiece = new boolean[B_size][B_size];

		mMoveList = new Vector<String>();

		mAIAgent = new Bot();

		//CompTurn = false;
	}

	public char[][] getBoard() {
		return Main_Board;
	}

	public int getGameState() {
		return state;
	}


	public void setGameState(int state) {
		this.state = state;
	}

	public void setIsCompTurn(boolean value) {
		CompTurn = value;
	}


	public boolean getIsCompTurn() {
		return CompTurn;
	}


	public int getWhiteScore() {
		return WhiteScore;
	}


	public int getBlackScore() {
		return BlackScore;
	}

	public boolean isNewPiece(int row, int col) {
		return (NewPieceRow == row && NewPieceCol == col);
	}

	public Vector<String> getMoveList() {
		return mMoveList;
	}


	public void newGame() {


		setInitBoard();
		setWeights();
		init();

		resetBoard();
		resetEffectedPieces();
		BlackTurn = false;
		state = inProcess;
		stateChange();
		getNextMove();

	}


	private void resetBoard() {
		for (int i = 0; i < B_size; ++i)
			for (int j = 0; j < B_size; ++j)
				Main_Board[i][j] = INIT_BOARD[i][j];

		BlackScore = 2;
		WhiteScore = 2;

		NewPieceRow = -1;
		NewPieceCol = -1;

		mMoveList.removeAllElements();
	}

	public void resetEffectedPieces() {
		for (int i = 0; i < B_size; ++i)
			for (int j = 0; j < B_size; ++j)
				mIsEffectedPiece[i][j] = false;
	}

	public void setEffectedPiece(int row, int col) {
		mIsEffectedPiece[row][col] = true;
	}

	public boolean isEffectedPiece(int row, int col) {
		return mIsEffectedPiece[row][col];
	}


	private void getNextMove() {
		if (!CompTurn) {
			char piece = (BlackTurn) ? B_piece : W_piece;
			if ((findValidMove(Main_Board, piece, true)).isEmpty()) {
				char opPiece = (piece == B_piece) ? W_piece : B_piece;
				if ((findValidMove(Main_Board, opPiece, false)).isEmpty()) {
					state = endProcess;
					stateChange();
					return;
				}
				changeTurn();
				getNextMove();
			}
		} else {
			char piece = (BlackTurn) ? B_piece : W_piece;

			// clean suggested pieces
			for (int i = 0; i < B_size; ++i)
				for (int j = 0; j < B_size; ++j)
					if (Main_Board[i][j] == SUGGEST_B_piece || Main_Board[i][j] == SUGGEST_W_piece)
						Main_Board[i][j] = EMPTY_PIECE;

			//board -> temp
			char[][] tempBoard = new char[B_size][B_size];
			for (int i = 0; i < B_size; ++i)
				for (int j = 0; j < B_size; ++j)
					tempBoard[i][j] = Main_Board[i][j];

			// find  move
			MoveCoord move = mAIAgent.findMove(tempBoard, piece);
			if (move != null) {
				effectMove(Main_Board, piece, move.getRow(), move.getCol());
				addToMoveList(piece, move.getRow(), move.getCol());
				NewPieceRow = move.getRow();
				NewPieceCol = move.getCol();
				stateChange();
			}

			changeTurn();
			getNextMove();
		}
	}


	private void addToMoveList(char piece, int row, int col) {
		String str = String.format("%s:\t%s", String.valueOf(piece).toUpperCase(), MoveCoord.encode(row, col));
		mMoveList.add(str);
	}


	private void changeTurn() {
		BlackTurn = !BlackTurn;
		CompTurn = !CompTurn;
	}


	private void calScore() {
		BlackScore = 0;
		WhiteScore = 0;
		for (int i = 0; i < B_size; ++i)
			for (int j = 0; j < B_size; ++j) {
				if (Main_Board[i][j] == B_piece)
					++BlackScore;
				else if (Main_Board[i][j] == W_piece)
					++WhiteScore;
			}
	}


	public static ArrayList<MoveCoord> findValidMove(char[][] board, char piece, boolean isSuggest) {
		char suggestPiece = (piece == B_piece) ? SUGGEST_B_piece : SUGGEST_W_piece;

		ArrayList<MoveCoord> moveList = new ArrayList<MoveCoord>();
		for (int i = 0; i < B_size; ++i)
			for (int j = 0; j < B_size; ++j) {

				if (board[i][j] == SUGGEST_B_piece || board[i][j] == SUGGEST_W_piece)
					board[i][j] = EMPTY_PIECE;

				if (isValidMove(board, piece, i, j)) {
					moveList.add(new MoveCoord(i, j));
					if (isSuggest)
						board[i][j] = suggestPiece;
				}
			}

		return moveList;
	}

	public static boolean isValidMove(char[][] board, char piece, int row, int col) {
		if (board[row][col] != EMPTY_PIECE)
			return false;

		char oppPiece = (piece == B_piece) ? W_piece : B_piece;

		boolean isValid = false;
		for (int i = 0; i < 8; ++i) {
			int curRow = row + MOVE_ROW[i];
			int curCol = col + MOVE_COL[i];
			boolean hasOppPieceBetween = false;
			while (curRow >= 0 && curRow < B_size && curCol >= 0 && curCol < B_size) {

				if (board[curRow][curCol] == oppPiece)
					hasOppPieceBetween = true;
				else if ((board[curRow][curCol] == piece) && hasOppPieceBetween) {
					isValid = true;
					break;
				} else
					break;

				curRow += MOVE_ROW[i];
				curCol += MOVE_COL[i];
			}
			if (isValid)
				break;
		}

		return isValid;
	}

	public static char[][] effectMove(char[][] board, char piece, int row, int col) {
		board[row][col] = piece;

		Reversi.getInstance().resetEffectedPieces();

		for (int i = 0; i < 8; ++i) {
			int curRow = row + MOVE_ROW[i];
			int curCol = col + MOVE_COL[i];
			boolean hasOppPieceBetween = false;
			while (curRow >= 0 && curRow < B_size && curCol >= 0 && curCol < B_size) {
				// if empty square
				if (board[curRow][curCol] == EMPTY_PIECE)
					break;

				if (board[curRow][curCol] != piece)
					hasOppPieceBetween = true;

				if ((board[curRow][curCol] == piece) && hasOppPieceBetween) {
					int effectPieceRow = row + MOVE_ROW[i];
					int effectPieceCol = col + MOVE_COL[i];
					while (effectPieceRow != curRow || effectPieceCol != curCol) {
						Reversi.getInstance().setEffectedPiece(effectPieceRow, effectPieceCol);
						board[effectPieceRow][effectPieceCol] = piece;
						effectPieceRow += MOVE_ROW[i];
						effectPieceCol += MOVE_COL[i];
					}
					break;
				}

				curRow += MOVE_ROW[i];
				curCol += MOVE_COL[i];
			}
		}

		return board;
	}


	public Boolean movePiece(int row, int col) {
		char piece = (BlackTurn) ? B_piece : W_piece;
		char suggestPiece = (BlackTurn) ? SUGGEST_B_piece : SUGGEST_W_piece;
		Boolean success;
		if (success = (Main_Board[row][col] == suggestPiece)) {
			effectMove(Main_Board, piece, row, col);
			NewPieceRow = row;
			NewPieceCol = col;
			addToMoveList(piece, row, col);

			stateChange();

			changeTurn();
			getNextMove();
		}
		return success;
	}

	private void stateChange() {
		calScore();
		ReversiPanel.update();
	}

}

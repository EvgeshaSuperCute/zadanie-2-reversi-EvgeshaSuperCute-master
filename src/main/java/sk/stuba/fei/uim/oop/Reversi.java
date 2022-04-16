
package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.Agent.MoveCoord;

import java.util.ArrayList;
import java.util.Vector;


public class Reversi {
	
	/** The unique instance of this class */
	private static Reversi sInstance;
	
	/** Game State */
	public static final int PLAYING = 0;
	public static final int ENDED = 1;

	/** number of rows */

	public static int sBOARD_SIZE = 8;
	public static int SAVE_BOARD_SIZE = sBOARD_SIZE;
	
	/** piece represents black */
	public static final char sBLACK_PIECE = 'b';
	
	/** piece represents white */
	public static final char sWHITE_PIECE = 'w';
	
	/** susggest piece for black */
	public static final char sSUGGEST_BLACK_PIECE = 'p';
	
	/** susggest piece for white */
	public static final char sSUGGEST_WHITE_PIECE = 'u';
	
	/** empty piece */
	public static final char sEMPTY_PIECE = '-';
	
	/** move offset for row */
	private static final int[] sOFFSET_MOVE_ROW ={-1, -1, -1,  0,  0,  1,  1,  1}; //{-1, -1, -1,  0,  0,  1,  1,  1};
	
	/** move offset for column */
	private static final int[] sOFFSET_MOVE_COL ={-1,  0,  1, -1,  1, -1,  0,  1}; //{-1,  0,  1, -1,  1, -1,  0,  1};
	
	/** board init */
	private static  char[][] sINIT_BOARD;
	/** whether it is black's turn to move */
	private boolean mIsBlackTurn = false;
	
	/** whether it is computer's turn to move */
	private boolean mIsCompTurn = true;
	
	/** the board */
	private char[][] mBoard;
	
	/** score of black piece */
	private int mBlackScore;
	
	/** score of white piece */
	private int mWhiteScore;
	
	/** state of the game */
	private int mState;
	
	/** AI agent */
	private Agent mAIAgent;
	
	/** new piece position */
	private int mNewPieceRow;
	private int mNewPieceCol;
	
	/** whether a piece is changed*/
	private boolean[][] mIsEffectedPiece;
	
	private Vector<String> mMoveList;

	private void setInitBoard(){
		sINIT_BOARD = new char[sBOARD_SIZE][sBOARD_SIZE];
		for (int i = 0; i< sBOARD_SIZE; i++){
			for(int j = 0; j<sBOARD_SIZE; j++){
				if((i == sBOARD_SIZE/2-1 && j == sBOARD_SIZE/2-1) ||
                        (i == sBOARD_SIZE/2 && j == sBOARD_SIZE/2)){
					sINIT_BOARD[i][j] = sBLACK_PIECE;
				}
				else if((i == sBOARD_SIZE/2-1 && j == sBOARD_SIZE/2) ||
                        (i == sBOARD_SIZE/2 && j == sBOARD_SIZE/2-1)){
					sINIT_BOARD[i][j] = sWHITE_PIECE;
				}
				else{
					sINIT_BOARD[i][j] = sEMPTY_PIECE;
				}
			}
		}

	}



	//_________________________________________________________________________________
	public static int[][] sBOARD_VALUEs;

	private void setWeights(){
		sBOARD_VALUEs = new int[sBOARD_SIZE][sBOARD_SIZE];

		for (int i = 0; i<sBOARD_SIZE; i++){
			for (int j = 0; j<sBOARD_SIZE; j++){
				if( (i == 0 && j == 0) || (i == 0 && j == sBOARD_SIZE-1) || (i == sBOARD_SIZE-1 && j == 0) || (i == sBOARD_SIZE-1 && j == sBOARD_SIZE -1))
				{
					sBOARD_VALUEs[i][j] = 99; //99
				}
				else if((i == 1 && j == 1) || (i == 1 && j == sBOARD_SIZE-2) || (i == sBOARD_SIZE-2 && j == 1) || (i == sBOARD_SIZE-2 && j == sBOARD_SIZE -2)){

					sBOARD_VALUEs[i][j] = -10; // -10
				}
				else if((i == 0 && j == 2) ||
						(i == 2 && j == 0) ||
						(i == 0 && j == sBOARD_SIZE-3) ||
						(i == 2 && j == sBOARD_SIZE-1) ||
						(i == sBOARD_SIZE-3 && j == 0) ||
						(i == sBOARD_SIZE-1 && j == 2) ||
						(i == sBOARD_SIZE-3 && j == sBOARD_SIZE-1) ||
						(i == sBOARD_SIZE-1 && j == sBOARD_SIZE-3)
				) {
					sBOARD_VALUEs[i][j] = 5; //5
				}
				else if((i == 0 && j == 1) ||
						(i == 1 && j == 0) ||
						(i == 0 && j == sBOARD_SIZE-2) ||
						(i == 1 && j == sBOARD_SIZE-1) ||
						(i == sBOARD_SIZE-2 && j == 0) ||
						(i == sBOARD_SIZE-1 && j == 1) ||
						(i == sBOARD_SIZE-2 && j == sBOARD_SIZE-1) ||
						(i == sBOARD_SIZE-1 && j == sBOARD_SIZE-2)
				) {
					sBOARD_VALUEs[i][j] = -1;
				}
				else if((i == sBOARD_SIZE/2-1 && j == sBOARD_SIZE/2-1) ||
						(i == sBOARD_SIZE/2 && j == sBOARD_SIZE/2) ||
						(i == sBOARD_SIZE/2-1 && j == sBOARD_SIZE/2) ||
						(i == sBOARD_SIZE/2 && j == sBOARD_SIZE/2-1)
				){
					sBOARD_VALUEs[i][j] = 0;
				}
				else if((i>2 && i<sBOARD_SIZE-3 && j==0)||
						(i>2 && i<sBOARD_SIZE-3 && j==sBOARD_SIZE-1)||
						(j>2 && j<sBOARD_SIZE-3 && i==0)||
						(j>2 && j<sBOARD_SIZE-3 && i==sBOARD_SIZE-1)
				){
					sBOARD_VALUEs[i][j] = 2;
				}
				else
					sBOARD_VALUEs[i][j] = 1;
			}
		}


	}

	//---------------------------------------------------------------------------------

	/** Private constructor */
	private Reversi() {
		setInitBoard();
		setWeights();
		init();
	}
	
	public static Reversi getInstance() {
		if (sInstance == null)
			sInstance = new Reversi();
		
		return sInstance;
	}
	
	/** Initialize the board */
    private void init() {
    	// init board
		mBoard = new char[sBOARD_SIZE][sBOARD_SIZE];
		
		// init effected pieces
		mIsEffectedPiece = new boolean[sBOARD_SIZE][sBOARD_SIZE];
		
		// init move list
		mMoveList = new Vector<String>();
		
		// set up AI agent
		mAIAgent = new NegaScoutAgent();
		
		// computer plays second for default
		mIsCompTurn = false;
	}
    
    public char[][] getBoard() {
    	return mBoard;
    }
    
    /** Gets game state */
    public int getGameState() {
    	return mState;
    }
    
    /** Sets game state */
    public void setGameState(int state) {
    	mState = state;
    }
    
    /** Set whether computer moves first */
    public void setIsCompTurn(boolean value) {
    	mIsCompTurn = value;
    }
    
    /** Get whether computer moves first */
    public boolean getIsCompTurn() {
    	return mIsCompTurn;
    }
    
    /** Get white's score */
    public int getWhiteScore() {
    	return mWhiteScore;
    }
    
    /** Get black's score */
    public int getBlackScore() {
    	return mBlackScore;
    }
    
    public boolean isNewPiece(int row, int col) {
    	return (mNewPieceRow == row && mNewPieceCol == col);
    }
    
    public Vector<String> getMoveList() {
    	return mMoveList;
    }
    
    /** New game */
	public void newGame() {
		// reset the board
		resetBoard();		
		// reset effected pieces
		resetEffectedPieces();
		// white piece starts first
		mIsBlackTurn = false;
		// set state
		mState = PLAYING;


		stateChange();
		//sBOARD_SIZE = SAVE_BOARD_SIZE;
		// get next move
		getNextMove();

	}
    
    /** Reset the board */
	private void resetBoard() {
		for (int i=0; i < sBOARD_SIZE; ++i)
			for (int j=0; j < sBOARD_SIZE; ++j)
				mBoard[i][j] = sINIT_BOARD[i][j];
		
		mBlackScore = 2;
		mWhiteScore = 2;
		
		mNewPieceRow = -1;
		mNewPieceCol = -1;
		
		mMoveList.removeAllElements();
	}
	
	public void resetEffectedPieces() {
		for (int i=0; i < sBOARD_SIZE; ++i)
			for (int j=0; j < sBOARD_SIZE; ++j)
				mIsEffectedPiece[i][j] = false;
	}
	
	public void setEffectedPiece(int row, int col) {
		mIsEffectedPiece[row][col] = true;
	}
	
	public boolean isEffectedPiece(int row, int col) {
		return mIsEffectedPiece[row][col];
	}
	
	/** Get next move */
	private void getNextMove() {
		if (!mIsCompTurn) {
			char piece = (mIsBlackTurn) ? sBLACK_PIECE : sWHITE_PIECE;
			if ((findValidMove(mBoard, piece, true)).isEmpty()) {
				char opPiece = (piece == sBLACK_PIECE) ? sWHITE_PIECE : sBLACK_PIECE;
				if ((findValidMove(mBoard, opPiece, false)).isEmpty())
				{
					mState = ENDED;
					stateChange();	
					return;
				}
				changeTurn();
				getNextMove();
			}	
		}
		else {
			char piece = (mIsBlackTurn) ? sBLACK_PIECE : sWHITE_PIECE;
			
			// clear all suggested pieces
			for (int i=0 ;i < sBOARD_SIZE; ++i)
				for (int j=0; j < sBOARD_SIZE; ++j)
					if (mBoard[i][j] == sSUGGEST_BLACK_PIECE || mBoard[i][j] == sSUGGEST_WHITE_PIECE)
						mBoard[i][j] = sEMPTY_PIECE;
			
			// copy board to temp
			char[][] tempBoard = new char[sBOARD_SIZE][sBOARD_SIZE];
			for (int i=0; i< sBOARD_SIZE; ++i)
				for (int j=0; j < sBOARD_SIZE; ++j)
					tempBoard[i][j] = mBoard[i][j];
			
			// find optimal move
			MoveCoord move = mAIAgent.findMove(tempBoard, piece);
			if (move != null)
			{
				//System.out.println(move.getRow() + " " + move.getCol());
				effectMove(mBoard, piece, move.getRow(), move.getCol());
				addToMoveList(piece, move.getRow(), move.getCol());
				mNewPieceRow = move.getRow();
				mNewPieceCol = move.getCol();
				stateChange();
			}
			
			// next move
			changeTurn();
			getNextMove();
		}
	}
	
	/** add a move to move list */
	private void addToMoveList(char piece, int row, int col) {
		String str = String.format("%s:\t%s", String.valueOf(piece).toUpperCase(), MoveCoord.encode(row, col));
		mMoveList.add(str);
	}
	
	/** change turn of playing */
	private void changeTurn() {
		mIsBlackTurn = !mIsBlackTurn;
		mIsCompTurn = !mIsCompTurn;
	}
	
	/** Calculate score */
	private void calScore() {
		mBlackScore = 0;
		mWhiteScore = 0;
		for (int i = 0; i < sBOARD_SIZE; ++i)
			for (int j = 0; j < sBOARD_SIZE; ++j)
			{
				if (mBoard[i][j] == sBLACK_PIECE)
					++mBlackScore;
				else if (mBoard[i][j] == sWHITE_PIECE)
					++mWhiteScore;
			}
	}
	
	/**
	 * Finds valid moves for specific piece
	 * @param board the board
	 * @param piece the piece need to find move
	 * @param isSuggest true to indicate suggested pieces on the board
	 * @return an array list of moves
	 */
	public static ArrayList<MoveCoord> findValidMove(char[][] board, char piece, boolean isSuggest) {
		char suggestPiece = (piece == sBLACK_PIECE) ? sSUGGEST_BLACK_PIECE : sSUGGEST_WHITE_PIECE;
		
		ArrayList<MoveCoord> moveList = new ArrayList<MoveCoord>();
		for (int i = 0; i < sBOARD_SIZE; ++i)
			for (int j = 0; j < sBOARD_SIZE; ++j) {
				// clean the suggest piece before
				if (board[i][j] == sSUGGEST_BLACK_PIECE || board[i][j] == sSUGGEST_WHITE_PIECE)
					board[i][j] = sEMPTY_PIECE;
				
				if (isValidMove(board,piece, i, j))
				{
					moveList.add(new MoveCoord(i, j));
					
					// if we want suggestion, mark on board
					if (isSuggest)
						board[i][j] = suggestPiece;
				}
			}
		
		return moveList;
	}
	
	/**
	 * Check whether a move is valid
	 * @param board the board
	 * @param piece the piece need to check
	 * @param row row of the move
	 * @param col column of the move
	 * @return true if the move is valid, false otherwise
	 */
	public static boolean isValidMove(char[][] board, char piece, int row, int col) {
		// check whether this square is empty
		if (board[row][col] != sEMPTY_PIECE)
			return false;
		
		char oppPiece = (piece == sBLACK_PIECE) ? sWHITE_PIECE : sBLACK_PIECE;
		
		boolean isValid = false;
		// check 8 directions
		for (int i = 0; i < 8; ++i) {
			int curRow = row + sOFFSET_MOVE_ROW[i];
			int curCol = col + sOFFSET_MOVE_COL[i];
			boolean hasOppPieceBetween = false;
			while (curRow >=0 && curRow < sBOARD_SIZE && curCol >= 0 && curCol < sBOARD_SIZE) {
				
				if (board[curRow][curCol] == oppPiece)
					hasOppPieceBetween = true;
				else if ((board[curRow][curCol] == piece) && hasOppPieceBetween)
				{
					isValid = true;
					break;
				}
				else
					break;
				
				curRow += sOFFSET_MOVE_ROW[i];
				curCol += sOFFSET_MOVE_COL[i];
			}
			if (isValid)
				break;
		}
		
		return isValid;
	}
	
	/**
	 * Effect the move
	 * @param board the board
	 * @param piece the piece of move
	 * @param row row of the move
	 * @param col column of the move
	 * @return the new board after the move is affected
	 */
	public static char[][] effectMove(char[][] board, char piece, int row, int col) {
		board[row][col] = piece;
		
		Reversi.getInstance().resetEffectedPieces();
		
		// check 8 directions
		for (int i = 0; i < 8; ++i) {
			int curRow = row + sOFFSET_MOVE_ROW[i];
			int curCol = col + sOFFSET_MOVE_COL[i];
			boolean hasOppPieceBetween = false;
			while (curRow >=0 && curRow < sBOARD_SIZE && curCol >= 0 && curCol < sBOARD_SIZE) {
				// if empty square, break
				if (board[curRow][curCol] == sEMPTY_PIECE)
					break;
				
				if (board[curRow][curCol] != piece)
					hasOppPieceBetween = true;
				
				if ((board[curRow][curCol] == piece) && hasOppPieceBetween)
				{
					int effectPieceRow = row + sOFFSET_MOVE_ROW[i];
					int effectPieceCol = col + sOFFSET_MOVE_COL[i];
					while (effectPieceRow != curRow || effectPieceCol != curCol)
					{
						Reversi.getInstance().setEffectedPiece(effectPieceRow, effectPieceCol);
						board[effectPieceRow][effectPieceCol] = piece;
						effectPieceRow += sOFFSET_MOVE_ROW[i];
						effectPieceCol += sOFFSET_MOVE_COL[i];
					}
					 
					break;
				}
				
				curRow += sOFFSET_MOVE_ROW[i];
				curCol += sOFFSET_MOVE_COL[i];
			}
		}
		
		return board;
	}
	
	/**
	 * human move piece
	 * @param row row of the move
	 * @param col column of the move
	 */
	public void movePiece(int row, int col) {
		char piece = (mIsBlackTurn) ? sBLACK_PIECE : sWHITE_PIECE;
		char suggestPiece = (mIsBlackTurn) ? sSUGGEST_BLACK_PIECE : sSUGGEST_WHITE_PIECE;
		if (mBoard[row][col] == suggestPiece)
		{
			effectMove(mBoard, piece, row, col);
			mNewPieceRow = row;
			mNewPieceCol = col;
			
			// add to move list
			addToMoveList(piece, row, col);
			// notify the observer
			stateChange();
			
			// change turn
			changeTurn();
			getNextMove();
		}
	}
    
	private void stateChange() {
		calScore();
		ReversiPanel.update();
	}

}

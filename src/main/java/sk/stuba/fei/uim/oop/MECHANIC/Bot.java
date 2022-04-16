
package sk.stuba.fei.uim.oop.MECHANIC;

import sk.stuba.fei.uim.oop.INTERFACE.Agent;

import java.util.ArrayList;

public class Bot implements Agent {


	static final int INFINITY = 1000000;
	private final int mMaxPly = 5;

	@Override
	public MoveCoord findMove(char[][] board, char piece) {
		return BotDecision(board, piece);
	}

	public MoveCoord BotDecision(char[][] board, char piece) {
		MoveScore moveScore = Bot(board, 0, -INFINITY, INFINITY, piece);
		return moveScore.getMove();
	}

	public MoveScore Bot(char[][] board, int ply, int alpha, int beta, char piece) {
		char oppPiece = (piece == Reversi.B_piece) ? Reversi.W_piece : Reversi.B_piece;


		if (ply == mMaxPly) {
			return new MoveScore(null, Evaluation.evolveBoard(board, piece, oppPiece));
		}

		int currentScore;
		int bestScore = -INFINITY;
		MoveCoord bestMove = null;
		int adaptiveBeta = beta;


		ArrayList<MoveCoord> moveList = Evaluation.setMasterMoves(board, piece);
		if (moveList.isEmpty())
			return new MoveScore(null, bestScore);
		bestMove = moveList.get(0);


		for (int i = 0; i < moveList.size(); i++) {
			MoveCoord move = moveList.get(i);
			char[][] newBoard = new char[Reversi.B_size][Reversi.B_size];
			for (int r = 0; r < Reversi.B_size; ++r)
				for (int c = 0; c < Reversi.B_size; ++c)
					newBoard[r][c] = board[r][c];
			Reversi.effectMove(newBoard, piece, move.getRow(), move.getCol());


			MoveScore current = Bot(newBoard, ply + 1, -adaptiveBeta, -Math.max(alpha, bestScore), oppPiece);

			currentScore = -current.getScore();


			if (currentScore > bestScore) {

				if (adaptiveBeta == beta || ply >= (mMaxPly - 2)) {
					bestScore = currentScore;
					bestMove = move;
				} else {
					current = Bot(newBoard, ply + 1, -beta, -currentScore, oppPiece);
					bestScore = -current.getScore();
					bestMove = move;
				}


				if (bestScore >= beta) {
					return new MoveScore(bestMove, bestScore);
				}


				adaptiveBeta = Math.max(alpha, bestScore) + 1;
			}
		}
		return new MoveScore(bestMove, bestScore);
	}
}

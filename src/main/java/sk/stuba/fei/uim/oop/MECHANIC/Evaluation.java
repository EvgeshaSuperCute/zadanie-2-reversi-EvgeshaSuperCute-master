
package sk.stuba.fei.uim.oop.MECHANIC;

import sk.stuba.fei.uim.oop.INTERFACE.Agent;
import sk.stuba.fei.uim.oop.INTERFACE.Agent.MoveCoord;
import sk.stuba.fei.uim.oop.INTERFACE.Agent.MoveScore;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Evaluation {

	public static int evolveBoard(char[][] board, char piece, char oppPiece) {
		int score = 0;
		for (int r = 0; r < Reversi.B_size; ++r) {
			for (int c = 0; c < Reversi.B_size; ++c) {
				if (board[r][c] == piece)
					score += Reversi.BOARD_VALUE_s[r][c];
				else if (board[r][c] == oppPiece)
					score -= Reversi.BOARD_VALUE_s[r][c];
			}
		}
		return score;
	}

	public static ArrayList<Agent.MoveCoord> setMasterMoves(char[][] board, char piece) {
		ArrayList<MoveCoord> moveList = Reversi.findValidMove(board, piece, false);
		PriorityQueue<MoveScore> moveQueue = new PriorityQueue<MoveScore>();

		for (int i = 0; i < moveList.size(); ++i) {
			MoveCoord move = moveList.get(i);
			MoveScore moveScore = new MoveScore(move, Reversi.BOARD_VALUE_s[move.getRow()][move.getCol()]);
			moveQueue.add(moveScore);
		}

		moveList = new ArrayList<MoveCoord>();
		while (!moveQueue.isEmpty()) {
			moveList.add(moveQueue.poll().getMove());
		}

		return moveList;
	}

}

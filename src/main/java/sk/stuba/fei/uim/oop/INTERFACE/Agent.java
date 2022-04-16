

package sk.stuba.fei.uim.oop.INTERFACE;


public interface Agent {


	MoveCoord findMove(char[][] board, char piece);


	public class MoveCoord {
		private int row;

		private int col;


		public MoveCoord(int row, int col) {
			this.row = row;
			this.col = col;
		}


		public int getRow() {
			return this.row;
		}

		public int getCol() {
			return this.col;
		}


		public void setRow(int row) {
			this.row = row;
		}

		public void setCol(int col) {
			this.col = col;
		}



		public static String encode(int row, int col) {
			return ("" + (char) ('A' + col) + (row + 1));
		}
	}
	

	public class MoveScore implements Comparable<MoveScore>{
		private MoveCoord move ;
	    private int score ;
	    
	    public MoveScore(MoveCoord move, int score){
	        this.move = move;
	        this.score = score;
	    }
	    
	    public int getScore(){ 
	    	return score ;
	    }
	    
	    public MoveCoord getMove(){ 
		  	return move ;
	   }

		@Override
		public int compareTo(MoveScore o) {
			if(o.score > this.score)
				return 1;
			else if (o.score < this.score)
				return -1;
			else
				return 0;
		}
	}
}
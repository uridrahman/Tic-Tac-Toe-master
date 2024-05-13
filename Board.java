public class Board {
	private char[] board = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
	private char player;
	private char computer;

	public Board(char player, char computer) {
		this.player = player;
		this.computer = computer;
	}

	public char getPlayer() {
		return this.player;
	}

	public char getComputer() {
		return this.computer;
	}

	public char[] getBoard() {
		return this.board;
	}

	public int getLength() {
		return this.board.length;
	}

	public void reset() {
		for (int i = 0; i < this.board.length; i++) {
			this.board[i] = ' ';
		}
	}

	public void newPiece(char piece, int position) {
		this.board[position] = piece;
	}

	public boolean spotAvailable(int position) {
		return this.board[position] != this.player && this.board[position] != this.computer;
	}

	public boolean isWinner(char piece) {
		for (int i = 0; i < 3; i++) {
			if ((this.board[3 * i] == piece && this.board[3 * i + 1] == piece && this.board[3 * i + 2] == piece)
					|| (this.board[i] == piece && this.board[i + 3] == piece && this.board[i + 6] == piece)) {
				return true;
			}
		}
		if ((this.board[2] == piece && this.board[4] == piece && this.board[6] == piece)
				|| (this.board[0] == piece && this.board[4] == piece && this.board[8] == piece)) {
			return true;
		}
		return false;
	}

	public boolean isDraw() {
		for (int i = 0; i < board.length; i++) {
			if (board[i] == ' ') {
				return false; // If any spot is empty, the game is not a draw
			}
		}
		return true; // If no empty spots are found, the game is a draw
	}

	public Board copy() {
		Board newBoard = new Board(this.player, this.computer);
		for (int i = 0; i < this.board.length; i++) {
			newBoard.board[i] = this.board[i];
		}
		return newBoard;
	}

	public String toString() {
		String boardString = "\n";
		for (int i = 0; i < this.board.length; i++) {
			boardString += " " + this.board[i];
			if (i % 3 == 2 && i != this.board.length - 1) {
				boardString += "\n" + "---|---|---\n";
			} else if (i != this.board.length - 1) {
				boardString += " |";
			}
		}
		return boardString;
	}
}

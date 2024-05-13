public class Opponent {
	// Will return the index of the best move to make
	public int getMove(Board board) {
		Board boardCopy = board.copy();

		// Take the middle if it's not taken
		if (boardCopy.spotAvailable(4)) {
			return 4;
		} else if (boardCopy.getBoard()[4] == board.getPlayer() && boardCopy.spotAvailable(2)) {
			// Return the index of a corner if the middle is taken by the player
			return 2;
		}

		// Return the index of the first winning position for the computer
		int computerIndex = findWinPosition(boardCopy, board.getComputer());
		if (computerIndex != -1) {
			return computerIndex;
		}

		// Return the index of the first winning position for the player
		int playerIndex = findWinPosition(boardCopy, board.getPlayer());
		if (playerIndex != -1) {
			return playerIndex;
		}

		// Return the index of a fork for the computer
		computerIndex = findFork(boardCopy, board.getComputer(), 2);
		if (computerIndex != -1) {
			return computerIndex;
		}

		// Return the index of the best move against the player's fork
		playerIndex = findFork(boardCopy, board.getPlayer(), 2);
		if (playerIndex != -1) {
			// Play two in a row to counter the fork
			if (boardCopy.getBoard()[4] == boardCopy.getPlayer()) {
				return findFork(boardCopy, board.getComputer(), 1);
			} else {
				// Play into the fork position of the player
				return playerIndex;
			}
		}

		// Return the index of opposite corners to the player
		for (int i = 0; i < boardCopy.getLength(); i += 2) {
			if (boardCopy.getBoard()[i] == board.getPlayer() && i != 4 && boardCopy.spotAvailable(8 - i)) {
				return 8 - i;
			}
		}

		// Return the index of the first available corner
		for (int i = 0; i < boardCopy.getLength(); i += 2) {
			if (i != 4 && boardCopy.spotAvailable(i)) {
				return i;
			}
		}

		// Return the index of the first open spot
		for (int i = 0; i < boardCopy.getLength(); i++) {
			if (boardCopy.spotAvailable(i)) {
				return i;
			}
		}

		return -1;
	}

	// Returns the index of the first position that wins the game for a piece
	private int findWinPosition(Board board, char piece) {
		Board boardCopy = board.copy();

		for (int i = 0; i < boardCopy.getLength(); i++) {
			if (boardCopy.spotAvailable(i)) {
				boardCopy.newPiece(piece, i);
				if (boardCopy.isWinner(piece)) {
					return i;
				}
			}
		}
		// No win position found
		return -1;
	}

	// Returns the index of a position that leads to a particular number of possible
	// wins on the next turn (1 or 2 wins)
	private int findFork(Board board, char piece, int wins) {
		Board boardCopy = board.copy();

		for (int i = 0; i < boardCopy.getLength(); i++) {
			Board newBoardCopy = boardCopy.copy();
			if (newBoardCopy.spotAvailable(i)) {
				newBoardCopy.newPiece(piece, i);
				int totalWins = 0;
				for (int j = 0; j < boardCopy.getLength(); j++) {
					Board newBoardCopy2 = newBoardCopy.copy();
					if (newBoardCopy2.spotAvailable(j)) {
						newBoardCopy2.newPiece(piece, j);
						if (newBoardCopy2.isWinner(piece)) {
							totalWins++;
						}
						if (totalWins >= wins) {
							return i;
						}
					}
				}
			}
		}
		return -1;
	}
}

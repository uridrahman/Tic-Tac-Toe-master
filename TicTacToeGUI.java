import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToeGUI extends JFrame {

	private static final long serialVersionUID = -5034718198692604252L;
	private JButton[] boardButtons = new JButton[9];
	private JButton resetButton = new JButton("Reset");
	private JButton manualButton = new JButton("Manual");
	private JButton automaticButton = new JButton("Automatic");
	private JFrame frame = new JFrame("Tic Tac Toe");

	private Board board = new Board('X', 'O');
	private Opponent opponent = new Opponent();

	private char currentPlayer = 'X'; // Keeps track of the current player
	private boolean manualMode = true; // Keeps track of the current mode (manual or automatic)

	public TicTacToeGUI() {
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private void initialise() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel gameBoard = new JPanel(new GridLayout(3, 3));
		JPanel modePanel = new JPanel();

		frame.add(mainPanel);

		gameBoard.setPreferredSize(new Dimension(500, 500));

		mainPanel.add(gameBoard, BorderLayout.NORTH);
		mainPanel.add(modePanel, BorderLayout.CENTER);
		mainPanel.add(resetButton, BorderLayout.SOUTH);

		modePanel.add(manualButton);
		modePanel.add(automaticButton);

		resetButton.addActionListener(new myActionListener());
		manualButton.addActionListener(new modeActionListener());
		automaticButton.addActionListener(new modeActionListener());

		// Initialise all the buttons
		for (int i = 0; i < 9; i++) {
			boardButtons[i] = new JButton();
			boardButtons[i].setBackground(Color.BLACK);
			boardButtons[i].setText("");
			boardButtons[i].setVisible(true);

			gameBoard.add(boardButtons[i]);
			boardButtons[i].addActionListener(new myActionListener());
			boardButtons[i].setFont(new Font("Tahoma", Font.BOLD, 100));
		}
	}

	// Listen for when the buttons are clicked
	private class myActionListener implements ActionListener {
		public void actionPerformed(ActionEvent action) {
			if (action.getSource() == resetButton) {
				resetGame();
			} else {
				for (int i = 0; i < 9; i++) {
					if (action.getSource() == boardButtons[i]) {
						makeMove(i);
						break;
					}
				}
			}
		}
	}

	// Listen for when the mode buttons are clicked
	private class modeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent action) {
			if (action.getSource() == manualButton) {
				manualMode = true;
			} else if (action.getSource() == automaticButton) {
				manualMode = false;
			}
		}
	}

	// Make a move in manual or automatic mode
	private void makeMove(int position) {
		if (manualMode) {
			// Manual mode logic
			if (board.spotAvailable(position)) {
				board.newPiece(currentPlayer, position);
				boardButtons[position].setText(Character.toString(currentPlayer));
				boardButtons[position].setForeground(Color.GREEN);

				// Check for a winner or draw
				if (board.isWinner(currentPlayer)) {
					gameOver(currentPlayer + " wins!");
				} else if (board.isDraw()) {
					gameOver("It's a draw!");
				} else {
					// Switch to the next player
					currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
					frame.setTitle("Tic Tac Toe - Player " + currentPlayer + "'s Turn");
				}
			}
		} else {
			// Automatic mode logic
			if (board.spotAvailable(position)) {
				// Player's turn
				board.newPiece(board.getPlayer(), position);
				boardButtons[position].setText(Character.toString(board.getPlayer()));
				boardButtons[position].setForeground(Color.GREEN);

				// If player has won, end game
				if (board.isWinner(board.getPlayer())) {
					gameOver("Player wins!");
					return;
				}

				// Computer's turn
				int computerMove = opponent.getMove(board);

				// If computer has a legal move
				if (computerMove != -1) {
					board.newPiece(board.getComputer(), computerMove);
					boardButtons[computerMove].setText(Character.toString(board.getComputer()));
					boardButtons[computerMove].setForeground(Color.YELLOW);

					// If computer has won, end game
					if (board.isWinner(board.getComputer())) {
						gameOver("Computer wins!");
					} else if (board.isDraw()) {
						gameOver("It's a draw!");
					} else {
						// Switch to the next player
						currentPlayer = board.getPlayer();
						frame.setTitle("Tic Tac Toe - Player " + currentPlayer + "'s Turn");
					}
				} else {
					// If computer cannot make a move, end game
					gameOver("It's a draw!");
				}
			}
		}
	}

	// Reset the game
	private void resetGame() {
		for (int i = 0; i < 9; i++) {
			boardButtons[i].setText("");
			boardButtons[i].setEnabled(true);
			frame.setTitle("Tic Tac Toe - Player X's Turn");
		}
		board.reset();
		currentPlayer = 'X'; // Reset the current player to X
	}

	// Game over message
	public void gameOver(String message) {
		for (int i = 0; i < 9; i++) {
			boardButtons[i].setEnabled(false);
		}
		JOptionPane.showMessageDialog(frame, message);
	}

	public static void main(String[] args) {
		TicTacToeGUI game = new TicTacToeGUI();
		game.initialise();
	}
}

package connect4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Connect4 extends JFrame {
	
	private JPanel jpMain;
	private JPanel jpScore;
	Connect4Board jpBoard;
	
	private Player currPlayer;
	private Player player1;
	private Player player2;
	
	public Connect4() {
		player1 = new Player("John", "X");
		player2 = new Player("Mary", "O");
		currPlayer = player1;
		
		//Create JPanel
		jpScore = new JPanel();
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());	
		jpBoard = new Connect4Board();
		jpBoard.setLayout(new GridLayout(0,7));

		
		add(jpMain);
		add(jpBoard);
		
		setSize(850,800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		jpBoard.setBackground(Color.orange);
	}
		
	private class Connect4Board extends JPanel implements GameBoardInterface, GamePlayerInterface, ActionListener {
		private JLabel [][] boardLabels;
		private final int ROWS = 6;
		private final int COLS = 7;
		
		private JButton [] boardButtons;
		private final int buttons = 7;
		
		
		private JTextArea currPlayerLabel; 
		
		int nextRow [] = {5, 5, 5, 5, 5, 5, 5};
		
		
		public Connect4Board() {
			setLayout(new GridLayout(ROWS,COLS));
			//Creating labels to be displayed in the board
			boardLabels = new JLabel[ROWS][COLS];
			boardButtons = new JButton[buttons];
			displayBoard();
			createButtons();	
			currPlayerLabel = new JTextArea();
			currPlayerLabel.setEditable(false);
			add(currPlayerLabel);
		}
		
		@Override
		public void displayBoard() {
			//Initializing the game board
			
			for(int row = 0; row < boardLabels.length; row++) {
				for(int col = 0; col < boardLabels[row].length; col++) {
					boardLabels[row][col] = new JLabel();
					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
					boardLabels[row][col].setFont(bigFont);
					//board[row][col].addActionListener(this);
					//boardLabels[row][col].setEnabled(true);
					add(boardLabels[row][col]);
					boardLabels[row][col].setText("");
					
				}
			}
		}
		
		@Override
		public void scoreBoard() {
			//currPlayerLabel = new JLabel();
			//add(currPlayerLabel);
			String cplayer = currPlayer.getName();
			currPlayerLabel.setText("Current Turn: " + currPlayer.getName() + ", " + currPlayer.getPlayerSymbol() + 
					"\nPlayer 1 wins: " + player1.getNumWins() + 
					"\nPlayer 2 wins: " + player2.getNumWins());
		}
		
		@Override
		public void createButtons() {
			//For use when creating the buttons used to play your marker
			for(int button = 0; button < boardButtons.length; button++) {
					boardButtons[button] = new JButton();	
					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 1);
					boardButtons[button].setFont(bigFont);
					boardButtons[button].setText("");
					boardButtons[button].addActionListener(this);
					boardButtons[button].setEnabled(true);
					add(boardButtons[button]);
					boardButtons[button].setBackground(Color.DARK_GRAY);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton)e.getSource();
			for(int i = 0; i < boardButtons.length; i++) {
				if (btnClicked.equals(boardButtons[i])) {
					System.out.println("Button: "+ i);
					int num = nextRow[i];
					boardLabels[num][i].setText(currPlayer.getPlayerSymbol());
					nextRow[i]--;
				}
				if(isWinner()) {
					clearBoard();
					String s = "Winner : " + currPlayer.getName() + "\nWould you like to play again?";
;					int reply = JOptionPane.showConfirmDialog(null, s, "", JOptionPane.YES_NO_OPTION);
						if(reply == JOptionPane.YES_OPTION) {
							clearBoard();
						}
						else {
							System.exit(0);
						}
				}
				
				else if(isFull()) {
					int reply = JOptionPane.showConfirmDialog(null, "Draw" + "\nWould you like to play again?");
						if(reply == JOptionPane.YES_OPTION) {
							clearBoard();
						}
						else {
							System.exit(0);
					}
				}
			}
			takeTurn();
			scoreBoard();
		
		}
			
		@Override
		public void clearBoard() {
			//For use when creating a new game
			for(int row = 0; row < boardLabels.length; row++) {
				for(int col = 0; col < boardLabels[row].length; col++) {
					boardLabels[row][col].setText("");
				}
			}
			for(int i = 0; i < COLS; i++) {
				nextRow[i] = ROWS - 1;
			}
		}
		
		
		@Override
		public boolean isEmpty() {
			for(int row = 0; row < boardLabels.length; row++) {
				for(int col = 0; col < boardLabels[row].length; col++) {
					String symbol = currPlayer.getPlayerSymbol();
					if(boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public boolean isFull() {
			for (int row = 0; row < boardLabels.length; row++) {
				int count = 0;
				for(int col = 0; col < boardLabels[row].length; col++) {
					String board = boardLabels[row][col].getText();
					if (board.equalsIgnoreCase("")){
						return false;
					}
					
				}
			}
			return true;
			
		}

		@Override
		public boolean isWinner() {
			if (isWinnerRow() || isWinnerCol() || isWinnerDiagDown() || isWinnerDiagUp()) {
				currPlayer.addWin();
				return true;
			}
			return false;
		}

		@Override
		public void takeTurn() {
			if (currPlayer.equals(player1)) {
				currPlayer = player2;
			}
			else {
				currPlayer = player1;
			}
		}

		@Override
		public boolean isWinnerRow() {
			String symbol = currPlayer.getPlayerSymbol();
			for(int row = 0; row < boardLabels.length; row++) {
				//Reset on the next row
				int numMatchesInRow = 0;
				for(int col = 0; col < boardLabels[row].length; col++) {
					if(boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)) {
						//We iterate through a row, if there is a match we add 1 to the match variable,
						//if numMatchesInRow = X.. we have connect X, and we have a winner
						numMatchesInRow++;
					}
					else {
						numMatchesInRow = 0;
					}
					if (numMatchesInRow == 4) {
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public boolean isWinnerCol() {
			String symbol = currPlayer.getPlayerSymbol();
			for (int col = 0; col < 7; col++) {
				int numMatchesInCol = 0;
				for(int row = 0; row < 6; row++) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInCol++;
					}
					else {
						numMatchesInCol = 0;
					}
					if (numMatchesInCol == 4) {
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public boolean isWinnerDiagDown() {
			String symbol = currPlayer.getPlayerSymbol();
			//This code checks diagonal going top to bottom, starting at index (0,0)
			for (int rowStart = 0; rowStart < 6; rowStart++) {
				int count = 0;
				int row, col;
				for(row = rowStart, col = 0; row < 6 && col < 7; row++, col++) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						count++;
					}
					else {
						count = 0;
					}
					if(count == 4) {
						return true;
					}
				}
			}
			//Achieves same as above.. but starts at row 1 (1,0)
			for (int colStart = 1; colStart < 6; colStart++) {
				int count = 0;
				int row, col;
				for(row = 0, col = colStart; row < 6 && col < 7; row++, col++) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						count++;
					}
					else {
						count = 0;
					}
					if(count == 4) {
						return true;
					}
				}
			}
			
			/*
			 * 
			 */
			
			//Checks from the right to the left, starting at (0,6)
			for (int rowStart = 0; rowStart < 6; rowStart++) {
				int count = 0;
				int row, col;
				for(row = rowStart, col = 6; row < 6 && col > 1; row++, col--) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						count++;
					}
					else {
						count = 0;
					}
					if(count == 4) {
						return true;
					}
				}
			}
			
			for (int colStart = 5; colStart > 1; colStart--) {
				int count = 0;
				int row, col;
				for(row = 0, col = 5; row < 6 && col > 1; row++, col--) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						count++;
					}
					else {
						count = 0;
					}
					if(count == 4) {
						return true;
					}
				}
			}
			return false;
		}
		
		@Override
		public boolean isWinnerDiagUp() {
			String symbol = currPlayer.getPlayerSymbol();
			//Checking diagonal from the bottom left, to the top right
			for (int rowStart = 5; rowStart > 0; rowStart--) {
				int count = 0;
				int row, col;
				for(row = rowStart, col = 0; row > 1 && col < 6; row--, col++) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						count++;
					}
					else {
						count = 0;
					}
					if(count == 4) {
						return true;
					}
				}
			}
			
			for (int rowStart = 4; rowStart > 0; rowStart--) {
				int count = 0;
				int row, col;
				for(row = rowStart, col = 0; row > 1 && col < 6; row--, col++) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						count++;
					}
					else {
						count = 0;
					}
					if(count == 4) {
						return true;
					}
				}
			}
			
			for (int row = 3; row < ROWS; row++) {
				for (int col = 0; col < COLS - 3; col++) {
					if(boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)
					&& boardLabels[row-1][col+1].getText().trim().equalsIgnoreCase(symbol)
					&& boardLabels[row-2][col+2].getText().trim().equalsIgnoreCase(symbol)
					&& boardLabels[row-3][col+3].getText().trim().equalsIgnoreCase(symbol))
					return true;
				}
			}
			
			//Starts at bottom right
			for (int rowStart = 5; rowStart > 0; rowStart--) {
				int count = 0;
				int row, col;
				for(row = rowStart, col = 5; row > 1 && col < 6; row--, col--) {
					if (boardLabels[row][col].getText().trim().equalsIgnoreCase(symbol)){
						count++;
					}
					else {
						count = 0;
					}
					if(count == 4) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
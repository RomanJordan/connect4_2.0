package connect4;

public interface GameBoardInterface {
	public void displayBoard();
	public void clearBoard();
	
	//Method used for creating the top row of buttons used to place your marker
	//public void createButtons();
	public void scoreBoard();
	public boolean isEmpty();
	public boolean isFull();
	public void createButtons();
}

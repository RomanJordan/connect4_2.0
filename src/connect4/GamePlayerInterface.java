package connect4;

public interface GamePlayerInterface {
	public boolean isWinnerRow();
	public boolean isWinnerCol();
	public boolean isWinnerDiagDown();
	public boolean isWinnerDiagUp();
	public boolean isWinner();
	public void takeTurn();
}

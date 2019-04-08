package connect4;

public class Player implements Comparable<Player>{
	private String playerName;
	private String playerSymbol;
	private int numGames;
	private int numWins;
	private int numLosses;
	private double winRate;
	
	public Player() {
		playerName = "Test";
		playerSymbol = "X";
		numGames = 0;
		numWins = 0;
		numLosses = 0;
		winRate = 0.0;
	}
	
	public Player(String name, String symbol) {
		this();
		playerName = name;
		playerSymbol = symbol;
	}
	
	public void addWin() {
		numGames++;
		numWins++;
	}
	
	public void addLoss() {
		numGames++;
		numLosses++;
	}
	
	/*
	 * Getters
	 */
	
	public int getnNumGames() {
		return numGames;
	}
	
	public int getNumWins() {
		return numWins;
	}
	
	public double winRate() {
		if (numGames != 0) {
			winRate = numWins / numGames;
			return winRate;
		}
		
		return winRate;
	}
	
	public String getName() {
		return playerName;
	}
	
	public String getPlayerSymbol() {
		return playerSymbol;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Player) {
			Player otherPlayer = (Player)o;
			if(this.playerName.equalsIgnoreCase(otherPlayer.playerName)) {
				if(this.playerSymbol == otherPlayer.playerSymbol) {
					if(this.numGames == otherPlayer.numGames) {
						if(this.numLosses == otherPlayer.numLosses) {
							if(this.numWins == otherPlayer.numWins) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int compareTo(Player otherP) {
		if(this.numWins > otherP.numWins) {
			return 1;
		}
		else if(this.numWins < otherP.numWins) {
			return - 1;
		}
		else {
			return 0;
		}
	}
	
	
}

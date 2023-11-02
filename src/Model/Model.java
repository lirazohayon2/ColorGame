package Model;

import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Point2D;

/******************************
 *				-final project-			   	*												
 *	Name:    Liraz Ohayon				*
 *	ID:          315872895				*
 *													*
 *******************************/

public class Model {
	

	private static final String TOP_SCORES_FILE_NAME = "TopPlayerScores.csv";
	private static final int NUMBER_OF_TOP_PLAYER = 10;
	
	private Level level;
	private ScoresHandler scoreHandler;
	
	public Model() {
		scoreHandler = new ScoresHandler(TOP_SCORES_FILE_NAME);
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}
	
	public int getBoardSize() {
		return level.getBoardSize();
	}

	public void changeSquarePressedState(int row, int col) {
		level.changeSquarePressedState(row, col);
	}

	public boolean check4Squares() {
		return level.check4Squares();
	}
	
	public boolean check4NextStep() {
		return level.check4NextStep();
	}

	public int getNumPressed() {
		return level.getCounterPressed();
	}

	public void clearAllPressedSquared() {
		level.clearAllPressedSquared();
	}

	public void paintAllPressedSquared() {
		level.paintAllPressedSquared();		
	}

	public int getCurrentPlayerSocre() {
		return level.getCurrentPlayerSocre();
	}

	public void startNewGame() {
		if (this.level==null)
			this.level = new Level1();
		else
			this.level.newGameBoard();
	}

	public ArrayList<PlayerScore> getTopPlayersScores() {
		ArrayList<PlayerScore> top10PlayerScores = new ArrayList<PlayerScore>();
		try {
			if(scoreHandler.isFileExists())
				top10PlayerScores = scoreHandler.readScores();
		} catch (IOException e) {
			System.err.println("model-ERROR#1: " + e.getMessage());
		}
		return top10PlayerScores;
	}
	
	public void clearTopPlayersScores() {
		try {
			scoreHandler.clear();
		} catch (IOException e) {
			System.err.println("model-ERROR#2: " + e.getMessage());
		}
	}

	public boolean isCurrentPlayerInTop10Scores() {
		return this.isPlayerInTop10Scores(this.getCurrentPlayerSocre());
	}
	
	private boolean isPlayerInTop10Scores(int score) {
		 try {
			ArrayList<PlayerScore> playerScores =   scoreHandler.readScores();
			if(playerScores.size()<NUMBER_OF_TOP_PLAYER)
				return true;
			
			for(int i=0; i< playerScores.size(); i++) {
				if(playerScores.get(i).getScore() < score) {
					return  true;
				}
			}
			return false;
		} catch (IOException e) {
			System.err.println("model-ERROR#3: " + e.getMessage());
			return false;
		}		 
	}

	public void insertNewPlayerToTop10(String playerName) {
		
		if(this.isCurrentPlayerInTop10Scores() == false){
			return; // The player is not in the TOP 10. 
		}
		
		try {
			PlayerScore player = new PlayerScore(playerName, 
					this.getCurrentPlayerSocre(), this.getLevel().getLevelName());
			ArrayList<PlayerScore> playersScores = scoreHandler.readScores();
			ArrayList<PlayerScore> newPlayersScores = insertionSortPlayerScore(playersScores, player);
			scoreHandler.writeScores(newPlayersScores);

		} catch (IOException e) {
			System.err.println("model-ERROR#3: " + e.getMessage());
		}		
	}

	private ArrayList<PlayerScore> insertionSortPlayerScore(ArrayList<PlayerScore> playersScores, PlayerScore player) {
		ArrayList<PlayerScore> newListPlayers = new ArrayList<PlayerScore>();
		int index=0;
		
		//find the index of player in the list
		for(;index< playersScores.size(); index++) 
			if(playersScores.get(index).getScore()<player.getScore()) 
				break;
			else
				newListPlayers.add(playersScores.get(index));
		
		//insert the new player to the list
		newListPlayers.add(player);
		
		//copy the tail of the player scores list to the new list
		for(;index< playersScores.size(); index++) 
			newListPlayers.add(playersScores.get(index));

		//remove extra players from the list
		while(newListPlayers.size()>NUMBER_OF_TOP_PLAYER) {
			newListPlayers.remove(newListPlayers.size()-1);
		}
		return newListPlayers;

	}

	public void resetTopScores() {
		try {
			this.scoreHandler.clear();
		} catch (IOException e) {
			System.err.println("model-ERROR#4: " + e.getMessage());
		}
	}

	public Point2D getHintCell() {
		return this.level.getHintCell();
	}

}

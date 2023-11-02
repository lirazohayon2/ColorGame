package Model;
public class PlayerScore {

	private String name;
	private int score;
	private String levelName; 
	
	public PlayerScore(String name, int score, String levelName) {
		this.name = name;
		this.score = score;
		this.levelName = levelName;
	}

	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getLevelName() {
		return this.levelName;
	}
}


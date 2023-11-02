package Model;

import javafx.geometry.Point2D;

public abstract class Level {

	private Board theBoard;
	private int numColors;
	private int boardSize;
	private String levelName;
	
	public Level(String levelName, int numColors, int boardSize) {
		this.levelName = levelName;
		this.numColors=numColors;
		this.boardSize=boardSize;
		theBoard = new Board(numColors, boardSize);		
	}

	//==============Board Proxy Methods==================
	public Board getTheBoard() {
		return theBoard;
	}

	public int getBoardSize() {
		return theBoard.getSize();
	}

	public void changeSquarePressedState(int row, int col) {
		theBoard.changeSquarePressedState(row, col);
	}

	public boolean check4Squares() {
		return theBoard.check4Squares();
	}

	public boolean check4NextStep() {
		return theBoard.check4NextStep();
	}

	public int getCounterPressed() {
		return theBoard.getCounterPressed();
	}

	public void clearAllPressedSquared() {
		theBoard.clearAllPressedSquared();
	}

	public void paintAllPressedSquared() {
		theBoard.paintAllPressedSquared();
	}

	public int getCurrentPlayerSocre() {
		return theBoard.getScore();
	}
	
	public String getLevelName() {
		return this.levelName;
	}	
	
	public void newGameBoard() {
		theBoard = new Board(this.numColors, this.boardSize);				
	}

	public Point2D getHintCell() {
		return theBoard.getHint();
	}



}

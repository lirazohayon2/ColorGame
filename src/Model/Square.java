package Model;

import javafx.scene.paint.Color;

public class Square {
	
	public final static int SIZE_SQUARE = 30;
	
	private Color color;
	private int row;    				// the row of the square in the matrix
	private int column;			// the column of the square in the column
	private boolean isPressed;
	
	public Square(Color color, int row, int column) {
		this.color = color;
		this.row = row;
		this.column = column;
		this.isPressed = false;
	}

	public Color getColor() {
		return color;
	}

	public static int getSizeSquare() {
		return SIZE_SQUARE;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void changeIsTheButtonPressed() {
		if (isPressed)
			isPressed = false;
		else
			isPressed = true;
	}

	public boolean getIsPressed() {
		return isPressed;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
}

package View;

import Model.Square;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareView {
	private static final int boardPaddingHorizontal = 30;
	private static final int boardPaddingVertical = 20;

	private Square theSquare; 
	private Rectangle myRct;

	public SquareView(Square theSquare) {
		this.theSquare = theSquare;
		myRct =  new Rectangle();
	}

	public void show(Pane drawPane, int levelPadding) {
		
		int xLocation = levelPadding+ boardPaddingHorizontal + theSquare.getColumn()*Square.SIZE_SQUARE;
		int yLocation = boardPaddingVertical + theSquare.getRow()*Square.SIZE_SQUARE;
		
		myRct = new Rectangle(xLocation, yLocation, Square.SIZE_SQUARE, Square.SIZE_SQUARE);	
		myRct.setStroke(Color.BLACK);

		if(theSquare.getIsPressed())
			myRct.setFill(theSquare.getColor().darker());
		else
			myRct.setFill(theSquare.getColor());
		
		drawPane.getChildren().add(myRct);
	}
	
	public  void refreshPressedView() {
		if(theSquare.getIsPressed())
			myRct.setFill(theSquare.getColor().darker());
		else
			myRct.setFill(theSquare.getColor());
	}

	public boolean isMyRct(Object source) {
		if(source instanceof Rectangle) {
			return myRct.equals(source);
		}
		else
			return false;
	}

	public Square getTheSquare() {
		return theSquare;
	}

	public void addClickEvent(EventHandler<MouseEvent> click) {
		myRct.addEventHandler(MouseEvent.MOUSE_CLICKED, click);
	}
}

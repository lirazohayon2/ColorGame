package View;

import Model.Level;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LevelView {

	private BoardView theBoardView;
	
	public LevelView(Level level) {
		theBoardView = new BoardView(level.getTheBoard());
	}

	public void show(Pane drawPane) {
		theBoardView.show(drawPane);
	}

	public int getSquareLocation(Object source) {
		return theBoardView.getSquareLocation(source);
	}

	public void AddClickEventToSquares(EventHandler<MouseEvent> click) {
		theBoardView.AddClickEventToSquares(click);
	}

	public void refreshSquarePressedView(int location) {
		theBoardView.refreshSquarePressedView(location);
	}

	public void refreshAllSquarePressedView() {
		theBoardView.refreshAllSquarePressedView();		
	}
}

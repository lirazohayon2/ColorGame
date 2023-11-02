package View;

import java.util.ArrayList;

import Model.Board;
import Model.Square;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BoardView {
	
	private Board theBoard;
	private ArrayList<SquareView> listSquareView;
	
	public BoardView(Board modelBoard) {
		theBoard = modelBoard;
		listSquareView = new ArrayList<SquareView>();
	}
	
	public void show(Pane drawPane) {
		Square[][] matSquare = theBoard.getMatSquare(); // we get data of Square (mat)
		listSquareView.clear();
		
		int boardLevelPadding = (int) (drawPane.getWidth()/2.25 -  matSquare.length*0.5*Square.SIZE_SQUARE); 
		for (int i = 0; i < matSquare.length; i++) {
			for (int j = 0; j < matSquare[0].length; j++) {
				Square square = matSquare[i][j];
				SquareView squareView = new SquareView(square);
				listSquareView.add(squareView);
				squareView.show(drawPane, boardLevelPadding);
			}
		}

	}

	public int getSquareLocation(Object source) {
		for (int i = 0; i < listSquareView.size(); i++) {
			if(listSquareView.get(i).isMyRct(source)) {
				return i;
			}
		}
		return -1;
	}

	public void AddClickEventToSquares(EventHandler<MouseEvent> click) {
		for(int i = 0;i<listSquareView.size() ;i++) {
			listSquareView.get(i).addClickEvent(click);
		}
	}

	public void refreshSquarePressedView(int location) {
		this.listSquareView.get(location).refreshPressedView();	
	}

	public void refreshAllSquarePressedView() {
		for (int i = 0; i < listSquareView.size(); i++) {
			this.listSquareView.get(i).refreshPressedView();
		}
	}
}

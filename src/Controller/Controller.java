package Controller;

import Model.Level;
import Model.Level1;
import Model.Level2;
import Model.Level3;
import Model.Model;
import View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

/******************************
 *				-final project-			   	*												
 *	Name:    Liraz Ohayon				*
 *	ID:          315872895				*
 *													*
 *******************************/

public class Controller {

	private View view;
	private Model model;

	public Controller(Model m, View v) {
		model = m;
		view = v;

		this.startNewGame();

		// 1. Define EventHandler for "NewGame"
		EventHandler<ActionEvent> clickNewGame = createHandlerNewGame();
		view.AddClickEventToNewGame(clickNewGame);

		// 2. Define EventHandler for Levels
		EventHandler<ActionEvent> clickLevel1 = createHandlerLevel(1);
		view.addClickEvetToLevel(clickLevel1, 1);
		EventHandler<ActionEvent> clickLevel2 = createHandlerLevel(2);
		view.addClickEvetToLevel(clickLevel2, 2);
		EventHandler<ActionEvent> clickLevel3 = createHandlerLevel(3);
		view.addClickEvetToLevel(clickLevel3, 3);

		// 2. Define EventHandler for Hint
		EventHandler<ActionEvent> clickHint = createHandlerHint();
		view.addClickEvetToHint(clickHint);


		// 4. Define EventHandler for "Top Scores"
		EventHandler<ActionEvent> clickTopScores = createHandlerTopScores();
		view.AddClickEventToTopScores(clickTopScores);

	}

	private EventHandler<ActionEvent> createHandlerHint() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				view.showHint(model.getHintCell());
			}
		};
	}

	private EventHandler<ActionEvent> createHandlerTopScores() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showTop10Players();
			}
		};
	}

	private EventHandler<ActionEvent> createHandlerLevel(int levelID) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switch (levelID) {
				case 2:  startNewLevelGame(new Level2()) ;
				break;
				case 3:  startNewLevelGame(new Level3()) ;
				break;
				default: startNewLevelGame(new Level1()) ;
				break;
				}
			}
		};
	}

	private EventHandler<ActionEvent> createHandlerNewGame() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.this.startNewGame();
			}
		};
	}

	protected void checkPlayerMove() {
		if(model.getNumPressed() == 4) {
			if(model.check4Squares()) {
				model.paintAllPressedSquared();
				model.clearAllPressedSquared();
				view.refreshAllSquarePressedView();
				view.updateScore(model.getCurrentPlayerSocre());

				if(model.check4NextStep()==false) 
					this.gameOver();
				
			}
			else
			{
				//BAD 4! , reset presses
				model.clearAllPressedSquared();
				view.refreshAllSquarePressedView();
			}
		}		
	}

	private void gameOver() {
		boolean isInTopScores  = model.isCurrentPlayerInTop10Scores();

		view.gameOver(model.getCurrentPlayerSocre(), isInTopScores);
		if(isInTopScores) {
			String playerName = view.receivePlayerData();

			model.insertNewPlayerToTop10(playerName);
			showTop10Players();
		}

		this.startNewGame();
	}
	private void startNewLevelGame(Level level) {
		model.setLevel(level);
		this.startNewGame();
	}
	private void startNewGame() {
		model.startNewGame();
		view.startNewGame(model.getLevel());

		// Create event handler for square
		EventHandler <MouseEvent> click = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {		
				int location = view.getSquareLocation(event.getSource());

				if(location >=0 ) {
					int row = location / model.getBoardSize();
					int col = location % model.getBoardSize();
					model.changeSquarePressedState(row, col);
					Controller.this.checkPlayerMove();
					view.refreshSquarePressedView(location);		
				}
			}
		};
		view.AddClickEventToSquares(click);
	}



	protected void showTop10Players() {
		view.showTopPlayersScores(model.getTopPlayersScores(), 	new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Reset Scores");
				alert.setHeaderText("Are you sure you want to reset score table?");

				alert.showAndWait().ifPresent(response -> {
					if(response == ButtonType.OK) {
						model.resetTopScores();
					}
				});
			}
		});
	}


}

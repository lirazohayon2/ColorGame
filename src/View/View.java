package View;

import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

import Model.Level;
import Model.PlayerScore;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

/******************************
 *				-final project-			   	*												
 *	Name:    Liraz Ohayon				*
 *	ID:          315872895				*
 *													*
 *******************************/

public class View {
	private Stage primaryStage;
	private Scene scene;
	private BorderPane bp;
	private Button bNewGame, bLevel1, bLevel2, bLevel3, bTopScores, bHint;
	private VBox vbLeft;
	private VBox vbRight;
	private HBox hbBottom;
	private Pane drawPane;
	private LevelView theLevelView;
	
	
	public View(Stage stage) {
		primaryStage = stage;
		
		// BUTTONS: 
		bNewGame = new Button("New Game");
		bLevel1 = new Button("Level 1");
		bLevel2 = new Button("Level 2");
		bLevel3 = new Button("Level 3");
	
		bTopScores = new Button("Top Scores Table");
		bHint = new Button("Hint");


		vbLeft = new VBox();
		vbLeft.getChildren().addAll(bNewGame, bLevel1, bLevel2,  bLevel3);
		vbLeft.setAlignment(Pos.TOP_LEFT);
		vbLeft.setPadding(new Insets(0,0,0,10));
		vbLeft.setSpacing(10);
		
		hbBottom= new HBox();
		hbBottom.getChildren().addAll(bTopScores, bHint);
		hbBottom.setAlignment(Pos.BOTTOM_LEFT);
		hbBottom.setPadding(new Insets(10,10,10,10));
		hbBottom.setSpacing(10);


		// Level and Score
		Text level = new Text("Level _");
		level.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
		Text score = new Text("Score");
		score.setStyle("-fx-font-size: 22;");
		Text points = new Text("0");
		points.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
		vbRight = new VBox();
		vbRight.setPadding(new Insets(0,30,5,15)); // Insets: sets distance from top,right, bottom, left
		vbRight.getChildren().addAll(level, score,points);
		vbRight.setAlignment(Pos.TOP_CENTER);
		
		// Set Title
		Text title = new Text("Colors Game");
		title.setStyle("-fx-font: 40px Tahoma;-fx-fill: linear-gradient(from 0% 0% to 40% 100%, repeat, aqua 30%, red 40%); -fx-stroke: black; -fx-stroke-width: 0.5;");
		HBox topBox = new HBox();
		topBox.setPadding(new Insets(15,0,15,0));
		topBox.getChildren().add(title);
		topBox.setAlignment(Pos.CENTER);
		
		drawPane = new Pane();
		bp = new BorderPane();
		bp.setLeft(vbLeft);
		bp.setRight(vbRight);
		bp.setTop(topBox);
		bp.setCenter(drawPane);
		bp.setBottom(hbBottom);
		
		scene = new Scene(bp,600,450);
		scene.setFill(Color.GRAY);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void update(Level level) {
		theLevelView = new LevelView(level); 
		updateLevelName(level.getLevelName());
		drawPane.getChildren().clear();
		theLevelView.show(drawPane); 
	}

	public int getSquareLocation(Object source) {
		return theLevelView.getSquareLocation(source);
	}

	public void AddClickEventToSquares(EventHandler<MouseEvent> click) {
		theLevelView.AddClickEventToSquares(click);
	}

	public void refreshSquarePressedView(int location) {
		theLevelView.refreshSquarePressedView(location);
	}

	public void refreshAllSquarePressedView() {
		theLevelView.refreshAllSquarePressedView();
	}

	public void updateScore(int score) {
		Text points = new Text(""+score);
		points.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
		vbRight.getChildren().set(2, points);
	}

	public void updateLevelName(String levelName) {
		Text level = new Text(levelName);
		level.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
		vbRight.getChildren().set(0, level);
	}
	
	public void gameOver(int score, boolean isInTopScores) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Over");
		alert.setHeaderText("GAME OVER ! ");
		
		String textStr = "Your Score is: "+score + "\n"; 
		String topPlayerStr =  "Congratulations you are one of the top 10  player."+
		"\nPlease fill in your details in the following window.";
		String SimplePlayerStr = "Unfortunately you are not a top 10 player :/"+
		"\nPlease Try Again :)";
		
		if(isInTopScores)
			textStr+=topPlayerStr;
		else
			textStr+=SimplePlayerStr;
		
		alert.setContentText(textStr);
		alert.showAndWait();		
	}



	public void startNewGame(Level level) {
		this.updateScore(0);		
		this.update(level);
	}

	public void AddClickEventToNewGame(EventHandler<ActionEvent> clickNewGame) {
		bNewGame.addEventHandler(ActionEvent.ACTION, clickNewGame);
	}
	public void AddClickEventToTopScores(EventHandler<ActionEvent> clickTopScores) {
		bTopScores.addEventHandler(ActionEvent.ACTION, clickTopScores);
	}

	public void showTopPlayersScores(ArrayList<PlayerScore> playerScores, EventHandler<ActionEvent> resetTableHandler) {
		 Stage dialog = new Stage(); // new stage
         dialog.initModality(Modality.APPLICATION_MODAL);
         dialog.initOwner(primaryStage);
         new TopPlayersScoresView(dialog, playerScores, resetTableHandler); 
	}
	
	public String receivePlayerData() {
		 Stage dialog = new Stage(); // new stage
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        PlayerNameFormView playerForm = new PlayerNameFormView(dialog); 
        dialog.showAndWait();
        return playerForm.getPlayerName();
	}

	public void addClickEvetToLevel(EventHandler<ActionEvent> clickLevel, int levelID) {
		switch (levelID) {
		case 1:  bLevel1.addEventHandler(ActionEvent.ACTION, clickLevel);
		break;
		case 2:  bLevel2.addEventHandler(ActionEvent.ACTION, clickLevel);
		break;
		case 3:  bLevel3.addEventHandler(ActionEvent.ACTION, clickLevel);
		break;
		default: ;
		break;
		}		
	}

	public void addClickEvetToHint(EventHandler<ActionEvent> clickHint) {
		bHint.addEventHandler(ActionEvent.ACTION, clickHint);
	}

	public void showHint(Point2D hintCell) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("HINT");
		alert.setHeaderText("We are happy to give you a littel help:");
		alert.setContentText("Look on the cell in row " + (int)(hintCell.getX()+1) +
				" and column "+(int)(hintCell.getY()+1)+".\n"+
				"Please note that we start counting the rows/columns from 1.\nGood luck ;)");
		alert.showAndWait();	
	}



}

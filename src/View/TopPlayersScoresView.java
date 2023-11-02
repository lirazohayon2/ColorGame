package View;

import java.util.ArrayList;

import Model.PlayerScore;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TopPlayersScoresView {
	@SuppressWarnings("rawtypes")
	private TableView table ;
	private Scene scene;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TopPlayersScoresView(Stage stage, ArrayList<PlayerScore> playerScores, EventHandler<ActionEvent> resetHandler) {
		table = new TableView();
		table.setMaxHeight(270);
		table.setMaxWidth(240);

		scene = new Scene(new Group());
		stage.setTitle("Top Player Scores");
		stage.setWidth(280);
		stage.setHeight(400);

		final Label label = new Label("TOP PLAYER SCORES");
		label.setFont(new Font("Arial", 20));

		TableColumn playerName = new TableColumn("PlayerName");
		playerName.setCellValueFactory(new PropertyValueFactory<PlayerScore, String>("name"));


		TableColumn scoreCol = new TableColumn("Score");
		scoreCol.setCellValueFactory(new PropertyValueFactory<PlayerScore, Integer>("score"));

		TableColumn levelName = new TableColumn("Level");
		levelName.setCellValueFactory(new PropertyValueFactory<PlayerScore, String>("levelName"));
		
		TableColumn numberCol = createNumberCol();

		table.setItems(createPlayersList(playerScores));
		table.getColumns().addAll(numberCol, playerName, scoreCol, levelName);

		Button bResetScores = createResetButtom(resetHandler);
		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table, bResetScores);
		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}
	
	private Button createResetButtom(EventHandler<ActionEvent> resetHandler) {
		Button bReset = new Button("Reset Table");
		bReset.addEventHandler(ActionEvent.ACTION, 	new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetHandler.handle(event);
				if(scene != null) {
				    Stage stage = (Stage) scene.getWindow();
					stage.close();
				}
			}
		});
		return bReset;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TableColumn createNumberCol() {
		TableColumn numberCol = new TableColumn("#");
		numberCol.setMinWidth(20);
		numberCol.setCellValueFactory(new Callback<CellDataFeatures<PlayerScore, PlayerScore>, ObservableValue<PlayerScore>>() {
			@Override public ObservableValue<PlayerScore> call(CellDataFeatures<PlayerScore, PlayerScore> p) {
				return new ReadOnlyObjectWrapper(p.getValue());
			}
		});

		numberCol.setCellFactory(new Callback<TableColumn<PlayerScore, PlayerScore>, TableCell<PlayerScore, PlayerScore>>() {
			@Override public TableCell<PlayerScore, PlayerScore> call(TableColumn<PlayerScore, PlayerScore> param) {
				return new TableCell<PlayerScore, PlayerScore>() {
					@Override protected void updateItem(PlayerScore item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							setText(this.getTableRow().getIndex()+1+"");
						} else {
							setText("");
						}
					}
				};
			}
		});
		numberCol.setSortable(false);
		return numberCol;
	}

	private ObservableList<PlayerScore> createPlayersList(ArrayList<PlayerScore> top10PlayerScores) {
		return FXCollections.observableList(top10PlayerScores);
	}
}

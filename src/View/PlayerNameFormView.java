package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PlayerNameFormView {

	String playerName = "";
	public PlayerNameFormView(Stage stage) {
		GridPane grid = createDataFormPane();
		addControls(grid);
		Scene scene = new Scene(grid, 300, 275);
		stage.setScene(scene);
	}
	
	 protected void setPlayerName(String text) {
	  	this.playerName=text;
	}
	 
	public String getPlayerName() {
		if (playerName.equals(""))
			return "Anonymos";
		else
			return playerName;
	}


	private GridPane createDataFormPane() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.setHgap(10);
		grid.setVgap(10);


		ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return grid;
	}


	private void addControls(GridPane gridPane) {
		// Add Header
		Label headerLabel = new Label("Player Form");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		gridPane.add(headerLabel, 0,0,2,1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

		// Add Name Label
		Label nameLabel = new Label("Full Name:");
		gridPane.add(nameLabel, 0,1);

		// Add Name Text Field
		TextField nameField = new TextField();
		nameField.setPrefHeight(40);
		nameField.setPrefWidth(50);
		nameField.setMaxWidth(150);
		gridPane.add(nameField, 1,1);

		// Add Submit Button
		Button submitButton = new Button("Submit");
		submitButton.setPrefHeight(40);
		submitButton.setDefaultButton(true);
		submitButton.setPrefWidth(100);
		gridPane.add(submitButton, 0, 4, 2, 1);
		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(nameField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
					return;
				}
				setPlayerName(nameField.getText());
				Stage stage = (Stage)  submitButton.getScene().getWindow();
				stage.close();
			}
		});
	}
	
 

	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}

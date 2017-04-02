package Controller;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import App.MainApp;
import Model.ArtistModel;
import Model.StageModel;

public class StageOverviewController {
	@FXML
	private TableView<StageModel> stagesTable;
	@FXML
	private TableColumn<StageModel, String> stagesColumn;
	@FXML
	private TableColumn<StageModel, String> stageDescriptionColumn;

	@FXML
	private Label stageLabel;
	@FXML
	private Label stageDescriptionLabel;

	public StageOverviewController() {
	}

	private MainApp mainApp;

	@FXML
	private void initialize() {

		stagesColumn.setCellValueFactory(cellData -> cellData.getValue().stageProperty());
		stageDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().stageDescriptionProperty());
		showStageDetails(null);
		stagesTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showStageDetails(newValue));
	}

	private void showStageDetails(StageModel stage) {
		if (stage != null) {
			stageLabel.setText(stage.getStage());
			stageDescriptionLabel.setText(stage.getStageDescription());
		} else {
			stageLabel.setText("There is currently no stage selected");
			stageDescriptionLabel.setText("There is currently no stage selected");
		}
	}

	@FXML
	private void handleEditStage() {
		StageModel selectedStage = stagesTable.getSelectionModel().getSelectedItem();
		if (selectedStage != null) {
			boolean okClicked = mainApp.showStageDialog(selectedStage);
			DatabaseController.EditStage(selectedStage.getId(), selectedStage.getStage(),
					selectedStage.getStageDescription());
			if (okClicked) {
				showStageDetails(selectedStage);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No stage Selected");
			alert.setContentText("Please select a stage in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteStage() {
		int selectedIndex = stagesTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			StageModel selectedStage = stagesTable.getSelectionModel().getSelectedItem();
			DatabaseController.Delete("stages", selectedStage.getId());
			stagesTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No stage selected");
			alert.setContentText("Please select a stage in the table");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewStage() {
		StageModel tempStage = new StageModel();
		boolean okClicked = mainApp.showStageDialog(tempStage);
		if (okClicked) {
			int key = DatabaseController.AddStage(tempStage.getStage(), tempStage.getStageDescription());
			tempStage.setId(key);
			mainApp.getStageData().add(tempStage);
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		stagesTable.setItems(mainApp.getStageData());
	}
}

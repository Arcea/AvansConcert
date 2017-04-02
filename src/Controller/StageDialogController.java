package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.StageModel;

public class StageDialogController {
	@FXML
	private TextField stageField;
	@FXML
	private TextField stageDescriptionField;

	private Stage dialogStage;
	private StageModel stage;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setStage(StageModel stage) {
		this.stage = stage;
		stageField.setText(stage.getStage());
		stageDescriptionField.setText(stage.getStageDescription());
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			stage.setStage(stageField.getText());
			stage.setStageDescription(stageDescriptionField.getText());

			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (stageField.getText() == null || stageField.getText().length() == 0) {
			errorMessage += "No valid artist\n";
		}
		if (stageDescriptionField.getText() == null || stageDescriptionField.getText().length() == 0) {
			errorMessage += "No valid description\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {

			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}

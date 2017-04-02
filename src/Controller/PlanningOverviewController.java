package Controller;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import App.MainApp;
import Model.ArtistModel;
import Model.PlanModel;
import Model.StageModel;

public class PlanningOverviewController {
	@FXML
	private TableView<PlanModel> planTable;
	@FXML
	private TableColumn<PlanModel, String> plannedArtistsColumn;
	@FXML
	private TableColumn<PlanModel, String> plannedStagesColumn;
	@FXML
	private TableColumn<PlanModel, String> plannedStartTimesColumn;
	@FXML
	private TableColumn<PlanModel, String> plannedEndTimesColumn;

	@FXML
	private Label plannedArtistLabel;
	@FXML
	private Label plannedStageLabel;
	@FXML
	private Label plannedStartLabel;
	@FXML
	private Label plannedEndLabel;

	public PlanningOverviewController() {

	}

	private MainApp mainApp;

	@FXML
	private void initialize() {

		plannedArtistsColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
		plannedStagesColumn.setCellValueFactory(cellData -> cellData.getValue().stageProperty());
		plannedStartTimesColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
		plannedEndTimesColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
		showPlanDetails(null);
		planTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPlanDetails(newValue));
	}

	private void showPlanDetails(PlanModel plan) {
		if (plan != null) {
			plannedArtistLabel.setText(plan.getArtist());
			plannedStageLabel.setText(plan.getStage());
			plannedStartLabel.setText(plan.getStartTime());
			plannedEndLabel.setText(plan.getEndTime());
		} else {
			plannedArtistLabel.setText("There is currently no event selected");
			plannedStageLabel.setText("There is currently no event selected");
			plannedStartLabel.setText("There is currently no event selected");
			plannedEndLabel.setText("There is currently no event selected");
		}
	}

	@FXML
	private void handleDeletePlan() {
		int selectedIndex = planTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			PlanModel selectedPlan = planTable.getSelectionModel().getSelectedItem();
			System.out.println(selectedPlan.getId());
			DatabaseController.Delete("planning", selectedPlan.getId());
			planTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No planning selected");
			alert.setContentText("Please select a planning in the table");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewPlan() {
		PlanModel tempPlan = new PlanModel();
		boolean okClicked = mainApp.showPlanDialog(tempPlan);
		if (okClicked) {
			int key = DatabaseController.AddPlan(tempPlan.getArtistId(), tempPlan.getStageId(), tempPlan.getStartTime(),
					tempPlan.getEndTime());
			tempPlan.setId(key);
			tempPlan.setStartTime(tempPlan.getStartTime() + ":00");
			tempPlan.setEndTime(tempPlan.getEndTime() + ":00");
			mainApp.getPlanData().add(tempPlan);
		}
	}

	@FXML
	private void handleEditPlan() {
		PlanModel selectedPlan = planTable.getSelectionModel().getSelectedItem();
		if (selectedPlan != null) {
			boolean okClicked = mainApp.showPlanDialog(selectedPlan);
			DatabaseController.EditPlan(selectedPlan.getId(), selectedPlan.getArtistId(), selectedPlan.getStageId(),
					selectedPlan.getStartTime(), selectedPlan.getEndTime());
			if (okClicked) {
				selectedPlan.setStartTime(selectedPlan.getStartTime() + ":00");
				selectedPlan.setEndTime(selectedPlan.getEndTime() + ":00");
				showPlanDetails(selectedPlan);
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

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		planTable.setItems(mainApp.getPlanData());
	}
}

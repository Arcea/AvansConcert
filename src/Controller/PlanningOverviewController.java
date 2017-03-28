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
import Model.PlanModel;

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
	
	public PlanningOverviewController(){
		
	}
	
	
    // Reference to the main application.
    private MainApp mainApp;

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	plannedArtistsColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        plannedStagesColumn.setCellValueFactory(cellData -> cellData.getValue().stageProperty());
        //plannedStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTime());
       // plannedEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().stageProperty());
        showPlanDetails(null);
        planTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPlanDetails(newValue));
    }
    
    private void showPlanDetails(PlanModel plan){
    	if(plan != null){
    		plannedArtistLabel.setText(plan.getArtist());
    		plannedStageLabel.setText(plan.getStage());
    		plannedStartLabel.setText("");
    		plannedEndLabel.setText("");
    	}
    	else{
    		plannedArtistLabel.setText("There is currently no event selected");
    		plannedStageLabel.setText("There is currently no event selected");
    		plannedStartLabel.setText("There is currently no event selected");
    		plannedEndLabel.setText("There is currently no event selected");
    	}
    }
    @FXML
    private void handleDeletePlan(){
    	int selectedIndex = planTable.getSelectionModel().getSelectedIndex(); 	
    	if(selectedIndex >= 0){
    		PlanModel selectedPlan =  planTable.getSelectionModel().getSelectedItem();
    		DatabaseController.Delete("planning" ,selectedPlan.getId());
    		planTable.getItems().remove(selectedIndex);
    	}else{
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.initOwner(mainApp.getPrimaryStage());
    		alert.setTitle("No selection");
    		alert.setHeaderText("No planning selected");
    		alert.setContentText("Please select a planning in the table");
    		alert.showAndWait();
    	}
    }
    
	  public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        planTable.setItems(mainApp.getPlanData());
	    }
}

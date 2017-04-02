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

public class ArtistOverviewController {
	@FXML
	private TableView<ArtistModel> concertTable;
	@FXML
	private TableColumn<ArtistModel, String> artistColumn;
	@FXML
	private TableColumn<ArtistModel, String> descriptionColumn;

	@FXML
	private Label artistLabel;
	@FXML
	private Label descriptionLabel;

	private MainApp mainApp;

	public ArtistOverviewController() {
	}

	@FXML
	private void initialize() {
		artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		showArtistDetails(null);
		concertTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showArtistDetails(newValue));
	}

	private void showArtistDetails(ArtistModel artist) {
		if (artist != null) {
			artistLabel.setText(artist.getArtist());
			descriptionLabel.setText(artist.getDescription());
		} else {
			artistLabel.setText("There is currently no artist selected");
			descriptionLabel.setText("There is currently no artist selected");
		}
	}

	@FXML
	private void handleNewArtist() {
		ArtistModel tempArtist = new ArtistModel();
		boolean okClicked = mainApp.showArtistDialog(tempArtist);
		if (okClicked) {
			int key = DatabaseController.AddArtist(tempArtist.getArtist(), tempArtist.getDescription());
			tempArtist.setId(key);
			mainApp.getArtistData().add(tempArtist);
		}
	}

	@FXML
	private void handleEditArtist() {
		ArtistModel selectedArtist = concertTable.getSelectionModel().getSelectedItem();
		if (selectedArtist != null) {
			boolean okClicked = mainApp.showArtistDialog(selectedArtist);
			DatabaseController.EditArtist(selectedArtist.getId(), selectedArtist.getArtist(),
					selectedArtist.getDescription());
			if (okClicked) {
				showArtistDetails(selectedArtist);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Artist Selected");
			alert.setContentText("Please select an artist in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteArtist() {
		int selectedIndex = concertTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			ArtistModel selectedArtist = concertTable.getSelectionModel().getSelectedItem();
			DatabaseController.Delete("artists", selectedArtist.getId());
			concertTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No artist selected");
			alert.setContentText("Please select an artist in the table");

			alert.showAndWait();
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		concertTable.setItems(mainApp.getArtistData());
	}
}

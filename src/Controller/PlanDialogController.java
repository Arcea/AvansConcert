package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import Model.ArtistModel;
import Model.PlanModel;

public class PlanDialogController {
	@FXML
	private ComboBox artistDrop;
	@FXML
	private ComboBox stageDrop;
	@FXML
	private ComboBox startTimePickerHours;
	@FXML
	private ComboBox endTimePickerHours;
	@FXML
	private ComboBox startTimePickerMinutes;
	@FXML
	private ComboBox endTimePickerMinutes;

	private Stage dialogStage;
	private PlanModel plan;
	private boolean okClicked = false;

	private ArrayList<Integer> artistIds = new ArrayList<Integer>();
	private ArrayList<Integer> stageIds = new ArrayList<Integer>();

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPlan(PlanModel plan) {
		this.plan = plan;
		ResultSet rsArtists = DatabaseController.getArtists();
		ResultSet rsStages = DatabaseController.getStages();
		int artistIndex = 0;
		int stageIndex = 0;
		try {
			int artistCount = 0;
			while (rsArtists.next()) {
				this.artistDrop.getItems().add(rsArtists.getString("artist"));
				artistIds.add(rsArtists.getInt("id"));
				if (rsArtists.getInt("id") == plan.getArtistId()) {
					artistIndex = artistCount;
					System.out.println(artistIndex);
				}
				++artistCount;
			}
			int stageCount = 0;
			while (rsStages.next()) {
				this.stageDrop.getItems().add(rsStages.getString("stage"));
				stageIds.add(rsStages.getInt("id"));
				if (rsStages.getInt("id") == plan.getStageId()) {
					stageIndex = stageCount;
				}
				++stageCount;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		int[] hours = new int[25];
		for (int i = 0; i < 24; i++) {
			hours[i] = i;
		}

		int[] minutes = new int[60];
		for (int i = 0; i < 60; i++) {
			minutes[i] = i;
		}

		for (int i = 0; i < 24; i++) {
			this.startTimePickerHours.getItems().add(hours[i]);
			this.endTimePickerHours.getItems().add(hours[i]);
		}

		for (int i = 0; i < 60; i++) {
			this.startTimePickerMinutes.getItems().add(minutes[i]);
			this.endTimePickerMinutes.getItems().add(minutes[i]);
		}
		if (plan.getId() == 0) {
			this.startTimePickerHours.getSelectionModel().select(0);
			this.endTimePickerHours.getSelectionModel().select(0);
			this.startTimePickerMinutes.getSelectionModel().select(0);
			this.endTimePickerMinutes.getSelectionModel().select(0);
			this.artistDrop.getSelectionModel().select(0);
			this.stageDrop.getSelectionModel().select(0);
			System.out.println(this.artistDrop.getSelectionModel().getSelectedItem());
		} else {

			this.startTimePickerHours.getSelectionModel().select(0);
			this.endTimePickerHours.getSelectionModel().select(0);
			this.startTimePickerMinutes.getSelectionModel().select(0);
			this.endTimePickerMinutes.getSelectionModel().select(0);
			this.artistDrop.getSelectionModel().select(artistIndex);
			this.stageDrop.getSelectionModel().select(stageIndex);

		}
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		try {
			boolean noError = true;
			ResultSet rs = DatabaseController.DBConnect(null);
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			String startHours = Integer.toString((int) startTimePickerHours.getSelectionModel().getSelectedItem());
			String startMinutes = Integer.toString((int) startTimePickerMinutes.getSelectionModel().getSelectedItem());

			String endHours = Integer.toString((int) endTimePickerHours.getSelectionModel().getSelectedItem());
			String endMinutes = Integer.toString((int) endTimePickerMinutes.getSelectionModel().getSelectedItem());

			Time startTime = new Time(formatter.parse(startHours + ':' + startMinutes).getTime());
			Time endTime = new Time(formatter.parse(endHours + ':' + endMinutes).getTime());
			String startTimeString = formatter.format(startTime);
			String endTimeString = formatter.format(endTime);
			try {
				while (rs.next()) {
					if (startTime.after(rs.getTime("planning.start_time"))
							&& endTime.before(rs.getTime("planning.end_time"))
							|| startTime.before(rs.getTime("planning.start_time"))
									&& endTime.after(rs.getTime("planning.end_time"))) {
						Alert alert = new Alert(AlertType.ERROR);
						noError = false;
						alert.initOwner(dialogStage);
						alert.setTitle("Invalid Fields");
						alert.setHeaderText("Please correct invalid fields");
						alert.setContentText("Cannot plan times because those times are already taken");
						alert.showAndWait();

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (noError == true) {
				plan.setStage((String) stageDrop.getSelectionModel().getSelectedItem());
				plan.setArtist((String) artistDrop.getSelectionModel().getSelectedItem());

				plan.setStartTime(startTimeString);
				plan.setEndTime(endTimeString);
				plan.setArtistId(artistIds.get(artistDrop.getSelectionModel().getSelectedIndex()));
				plan.setStageId(stageIds.get(stageDrop.getSelectionModel().getSelectedIndex()));
				okClicked = true;
				dialogStage.close();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}

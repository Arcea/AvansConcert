package Model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlanModel {
	private int id;
	private int artist_id;
	private int stage_id;
	private final StringProperty artist;
	private final StringProperty stage;
	private final SimpleStringProperty start_time;
	private final SimpleStringProperty end_time;

	public PlanModel() {
		this(0, 0, 0, null, null, null, null);
	}

	public PlanModel(int id, int artist_id, int stage_id, String artist, String stage, String start_time,
			String end_time) {
		this.id = id;
		this.artist_id = artist_id;
		this.stage_id = stage_id;
		this.artist = new SimpleStringProperty(artist);
		this.stage = new SimpleStringProperty(stage);
		this.start_time = new SimpleStringProperty(start_time);
		this.end_time = new SimpleStringProperty(end_time);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArtist() {
		return artist.get();
	}

	public void setArtist(String artist) {
		this.artist.set(artist);
	}

	public void setArtistId(int artist_id) {
		this.artist_id = artist_id;
	}

	public int getArtistId() {
		return this.artist_id;
	}

	public StringProperty artistProperty() {
		return artist;
	}

	public String getStage() {
		return stage.get();
	}

	public void setStage(String stage) {
		this.stage.set(stage);
	}

	public void setStageId(int stage_id) {
		this.stage_id = stage_id;
	}

	public int getStageId() {
		return this.stage_id;
	}

	public StringProperty stageProperty() {
		return stage;
	}

	public String getStartTime() {
		return start_time.get();
	}

	public void setStartTime(String start_time) {
		this.start_time.set(start_time);
	}

	public StringProperty startTimeProperty() {
		return start_time;
	}

	public String getEndTime() {
		return end_time.get();
	}

	public void setEndTime(String end_time) {
		this.end_time.set(end_time);
	}

	public StringProperty endTimeProperty() {
		return end_time;
	}
}

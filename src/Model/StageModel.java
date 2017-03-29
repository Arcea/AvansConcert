package Model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StageModel {
	private final StringProperty stage;
	private final StringProperty stageDescription;
	private int id;

	public StageModel() {
		this(null, null, 0);
	}

	public StageModel(String stage, String stageDescription, int id) {
		this.stage = new SimpleStringProperty(stage);
		this.stageDescription = new SimpleStringProperty(stageDescription);
		this.id = id;
	}

	public String getStage() {
		return stage.get();
	}

	public void setStage(String stage) {
		this.stage.set(stage);
	}

	public StringProperty stageProperty() {
		return stage;
	}

	public String getStageDescription() {
		return stageDescription.get();
	}

	public void setStageDescription(String stageDescription) {
		this.stageDescription.set(stageDescription);
	}

	public StringProperty stageDescriptionProperty() {
		return stageDescription;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

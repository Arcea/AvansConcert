package Model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ArtistModel {
	private final StringProperty artist;
	private final StringProperty description;
	private int id;

	public ArtistModel() {
		this(null, null, 0);
	}

	public ArtistModel(String artist, String description, int id) {
		this.artist = new SimpleStringProperty(artist);
		this.description = new SimpleStringProperty(description);
		this.id = id;
	}

	public String getArtist() {
		return artist.get();
	}

	public void setArtist(String artist) {
		this.artist.set(artist);
	}

	public StringProperty artistProperty() {
		return artist;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

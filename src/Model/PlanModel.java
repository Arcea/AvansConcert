package Model;

import java.sql.Date;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PlanModel {
	private int id;
	private final StringProperty artist;
    private final StringProperty stage;
	private Date start_time;
	private Date end_time;

	
	public PlanModel(){
		this(0, null, null, 0, 0);
	}

	//check date stuff later
	public PlanModel(int id, String artist, String stage, int start_time, int end_time) {
		System.out.println(artist);
		this.id = id;
		this.artist = new SimpleStringProperty(artist);
		this.stage = new SimpleStringProperty(stage);
		this.start_time = new Date(start_time);
		this.end_time = new Date(end_time);
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getArtist(){
		return artist.get();
	}
	
	public void setArtist(String artist){
		System.out.println(artist);
		this.artist.set(artist);
	}
	
	 public StringProperty artistProperty() {
	    return artist;
	 }
	
	public String getStage(){
		return stage.get();
	}
	
	public void setStage(String stage){
		this.stage.set(stage);
	}
	
	 public StringProperty stageProperty() {
	        return stage;
	 }
	
	public Date getStartTime(){
		return start_time;
	}
	
	public void setStartTime(Date start_time){
		this.start_time = start_time;
	}
	
	public Date getEndTime(){
		return end_time;
	}
	
	public void setEndTime(Date end_time){
		this.end_time = end_time;
	}
}

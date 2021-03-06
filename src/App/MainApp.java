package App;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import Model.ArtistModel;
import Model.PlanModel;
import Model.StageModel;
import Controller.ArtistDialogController;
import Controller.ArtistOverviewController;
import Controller.DatabaseController;
import Controller.PlanDialogController;
import Controller.PlanningOverviewController;
import Controller.StageDialogController;
import Controller.StageOverviewController;

public class MainApp extends Application {

	private ObservableList<ArtistModel> artistData = FXCollections.observableArrayList();
	private ObservableList<StageModel> stageData = FXCollections.observableArrayList();
	private ObservableList<PlanModel> planData = FXCollections.observableArrayList();

	public MainApp() {

	}

	public ObservableList<ArtistModel> getArtistData() {
		return artistData;
	}

	public ObservableList<StageModel> getStageData() {
		return stageData;
	}

	public ObservableList<PlanModel> getPlanData() {
		return planData;
	}

	private static Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Concertplanner");
		initRootLayout();

		showArtistOverview();
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../View/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout, 800, 525);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showArtistOverview() {
		try {
			ResultSet rs = DatabaseController.DBConnect("artists");
			try {
				while (rs.next()) {
					artistData
							.add(new ArtistModel(rs.getString("artist"), rs.getString("description"), rs.getInt("id")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../View/ArtistOverview.fxml"));
			AnchorPane artistOverview = (AnchorPane) loader.load();

			if (rootLayout == null) {
				FXMLLoader loader1 = new FXMLLoader();
				loader1.setLocation(MainApp.class.getResource("../View/RootLayout.fxml"));
				rootLayout = (BorderPane) loader1.load();
				Scene scene = new Scene(rootLayout, 800, 525);
				this.primaryStage.setScene(scene);
				this.primaryStage.show();

			}

			rootLayout.setCenter(artistOverview);
			ArtistOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showStageOverview() {
		try {
			ResultSet rs = DatabaseController.DBConnect("stages");
			try {
				while (rs.next()) {
					stageData.add(new StageModel(rs.getString("stage"), rs.getString("description"), rs.getInt("id")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(MainApp.class.getResource("../View/RootLayout.fxml"));
			rootLayout = (BorderPane) loader1.load();
			this.primaryStage = getPrimaryStage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../View/StageOverview.fxml"));
			AnchorPane stageOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(stageOverview);
			Scene scene = new Scene(rootLayout, 800, 525);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			StageOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPlanOverview() {
		try {
			ResultSet rs = DatabaseController.DBConnect(null);
			try {
				while (rs.next()) {
					planData.add(new PlanModel(rs.getInt("id"), rs.getInt("artists.id"), rs.getInt("stages.id"),
							rs.getString("artists.artist"), rs.getString("stages.stage"),
							rs.getString("planning.start_time"), rs.getString("planning.end_time")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(MainApp.class.getResource("../View/RootLayout.fxml"));
			rootLayout = (BorderPane) loader1.load();
			this.primaryStage = getPrimaryStage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../View/PlanningOverview.fxml"));
			AnchorPane planningOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(planningOverview);
			Scene scene = new Scene(rootLayout, 800, 525);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			PlanningOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public boolean showArtistDialog(ArtistModel artist) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../View/ArtistDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Artist Dialog");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ArtistDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setArtist(artist);

			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showStageDialog(StageModel stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../View/StageDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Stage Dialog");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			StageDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setStage(stage);

			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showPlanDialog(PlanModel plan) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../View/PlanDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogPlan = new Stage();
			dialogPlan.setTitle("Plan Dialog");
			dialogPlan.initModality(Modality.WINDOW_MODAL);
			dialogPlan.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogPlan.setScene(scene);

			PlanDialogController controller = loader.getController();
			controller.setDialogStage(dialogPlan);
			controller.setPlan(plan);

			dialogPlan.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
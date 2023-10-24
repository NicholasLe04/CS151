package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/main/mainScene.fxml"));
			Parent root = loader.load();
			// open new window
			primaryStage.setTitle("Stomp");
			primaryStage.setScene(new Scene(root, 1200, 675));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

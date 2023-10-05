package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// db stuff
			db.SQLConnector connector = new db.SQLConnector();
			Connection db = connector.getConnection();
			
			// javafx stuff
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;

import entries.ProjectManager;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// just for testing. get rid of later and only use classes in entries
			db.SQLConnector db = new db.SQLConnector();
			Connection conn = db.getConnection();
			
			entries.ProjectManager pm = new entries.ProjectManager(conn);
			
			// javafx stuff TODO
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

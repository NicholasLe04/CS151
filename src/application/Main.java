package application;
	
import java.sql.Connection;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// just for testing. get rid of later
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

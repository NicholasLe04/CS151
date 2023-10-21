package application.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
	
	public void createProject() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/main/newProjectDialog.fxml"));
			Parent root = loader.load();
			
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 800, 500));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getProjects() {
		// TODO: i actually have no idea how to use this. in js u would run this right after load and populate with that but idk
	}
}

package application.controller;

import java.io.IOException;
import java.sql.Connection;
import db.SQLConnector;
import entries.Project;
import entries.ProjectDAO;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.*;

public class MainController {
	
	private Connection conn;
	
	@FXML public Button newProjectButton;
	@FXML public GridPane projectGrid;
	
	@FXML
	public void initialize() {
		conn = new SQLConnector().getConnection();
	}
	
	public void showCreateProjectDialog() {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/main/newProjectDialog.fxml"));
			Parent root = loader.load();
			// open new window
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 800, 500));
			stage.setUserData(newProjectButton);
			stage.show();
			// disable button
			newProjectButton.setDisable(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showProjects() {
		ProjectDAO projectDAO = new ProjectDAO(conn);
		try {
			for (Project project : projectDAO.getProjects()) {
				FXMLLoader projectLoader = new FXMLLoader(getClass().getResource("/application/fxml/project/projectCard.fxml"));
				Parent projectNode = projectLoader.load();
				
				// TODO: modify the projectNode here
				projectGrid.getChildren().add(projectLoader.load());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

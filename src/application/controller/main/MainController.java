package application.controller.main;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import application.controller.project.ProjectController;
import db.SQLConnector;
import entries.Project;
import entries.ProjectDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainController {
	
	private Connection conn;
	private ProjectDAO projectDAO;
	
	@FXML public Button newProjectButton;
	@FXML public GridPane projectGrid;
	
	@FXML
	public void initialize() {
		conn = new SQLConnector().getConnection();
		projectDAO = new ProjectDAO(conn);
		updateProjects();
	}
	
	public void showCreateProjectDialog() {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/main/newProjectDialog.fxml"));
			Parent root = loader.load();
			// open new window
			Stage stage = new Stage();
			Scene scene = new Scene(root, 800, 500);
			stage.setScene(scene);
			// pass this Controller instance, so dialog can change things in the main window
			stage.setUserData(this);
			stage.show();			
			// disable button
			newProjectButton.setDisable(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateProjects() {
		ArrayList<Project> projects = projectDAO.getProjects();
		
		try {
			projectGrid.getChildren().clear();
			for (int i = 0; i < projects.size(); i++) {
				// load project fxml
				FXMLLoader projectLoader = new FXMLLoader(getClass().getResource("/application/fxml/project/projectCard.fxml"));
				Parent projectNode = projectLoader.load();
				// modify the projectNode
				ProjectController controller = (ProjectController) projectNode.getUserData();
				controller.setTitle(projects.get(i).getName());
				controller.setDate(projects.get(i).getDate());
				controller.setDesc(projects.get(i).getDesc());
				// this passes the MainController to the projectCard
				projectNode.setUserData(this);
				// add project
				projectGrid.add(projectNode, i % 4, i / 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setCreateButtonState(boolean state) {
		newProjectButton.setDisable(!state);
	}
}

package application.controller.main;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import application.controller.project.ProjectController;
import db.SQLConnector;
import entries.Project;
import entries.ProjectDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainController {
	
	private Connection conn;
	private ProjectDAO projectDAO;
	
	@FXML private Button createProjectButton;
	@FXML private FlowPane projectGrid;
	@FXML private TextField searchBar;
	
	@FXML
	public void initialize() {
		conn = SQLConnector.getInstance().getConnection();
		projectDAO = new ProjectDAO(conn);
		updateProjects("");
		searchBar.textProperty().addListener((observable, oldValue, newValue)-> {
			updateProjects(newValue);
		});
	}
	
	public void showCreateProjectDialog() {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/main/createProjectDialog.fxml"));
			Parent root = loader.load();
			// open new window
			Stage stage = new Stage();
			Scene scene = new Scene(root, 800, 500);
			stage.setScene(scene);
			stage.setOnCloseRequest(e -> setButtonState(true));
			stage.setUserData(this);
			stage.show();			
			// disable button
			setButtonState(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO: add highlight on projects with that name
	public void updateProjects(String searchText) {
		ArrayList<Project> projects = projectDAO.searchProjects(searchText);
		
		try {
			projectGrid.getChildren().clear();
			for (Project project : projects) {
				// load project fxml
				FXMLLoader projectLoader = new FXMLLoader(getClass().getResource("/application/fxml/project/projectCard.fxml"));
				Parent projectNode = projectLoader.load();
				// modify the projectNode
				ProjectController controller = (ProjectController) projectNode.getUserData();
				controller.setTitle(project.getTitle());
				controller.setDate(project.getDate());
				controller.setDesc(project.getDesc());
				// get tickets too
				controller.updateTickets();
				// add project
				projectGrid.getChildren().add(projectNode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setButtonState(boolean state) {
		createProjectButton.setDisable(!state);
	}
}

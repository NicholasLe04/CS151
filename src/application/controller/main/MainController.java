package application.controller.main;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import application.controller.project.ProjectController;
import db.SQLConnector;
import entries.Project;
import entries.ProjectDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {
	
	private Connection conn;
	private ProjectDAO projectDAO;
	
	@FXML private Button createProjectButton;
	@FXML private FlowPane projectGrid;
	@FXML private TextField searchBar;
	@FXML private TextField ticketBar;
	
	private String lastSearchText;
	private String searchText;
	private String ticketSearchText;
	private String lastTicketSearchText;
	
	@FXML
	public void initialize() {
		conn = SQLConnector.getConnection();
		projectDAO = new ProjectDAO(conn);
		updateTickets("");
		updateProjects("");
		
		// i would add an async ui update here, like in javascript, but javafx doesnt allow it for some reason. i hate this framework.
		// this was supposed to be throttling but it became debouncing. i have no idea why or how
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            searchText = searchBar.getText();
            if (!searchText.equals(lastSearchText)) {
                updateProjects();	
                lastSearchText = searchText;
            }
            
            ticketSearchText = ticketBar.getText();
            if (!ticketSearchText.equals(lastTicketSearchText)) {
                updateTickets();	
                lastTicketSearchText = ticketSearchText;
            }
            
        }));
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            timeline.stop();
            timeline.play();
        });
		
		ticketBar.textProperty().addListener((observable, oldValue, newValue) -> {
            timeline.stop();
            timeline.play();
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

	// search projects with searchbar text
	public void updateProjects() {
		if (searchText == null) updateProjects("");
		else updateProjects(searchText);
	}

	// search projects with custom text
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
	
	// search tickets with searchbar text
		public void updateTickets() {
			if (ticketSearchText == null) updateTickets("");
			else updateTickets(ticketSearchText);
		}

	// search tickets with custom text
		public void updateTickets(String ticketSearchText) {
			ArrayList<Project> projects = projectDAO.searchTickets(ticketSearchText);
			
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

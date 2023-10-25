package application.controller.project;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import application.controller.main.MainController;
import db.SQLConnector;
import entries.Project;
import entries.ProjectDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProjectController {
	
	private Connection conn;

	@FXML public Label title;
	@FXML public Label date;
	@FXML public Label desc;
	
	@FXML
	public void initialize() {
		conn = new SQLConnector().getConnection();
	}

    public void setName(String name) {
        this.title.setText(name);
    }
    
    public void setDate(LocalDate date) {
        this.date.setText("Started on " + date.toString());
    }
    
    public void setDesc(String desc) {
    	this.desc.setText(desc);
    }
    
    public void deleteProject() {
    	ProjectDAO projectDAO = new ProjectDAO(conn);
    	projectDAO.deleteProject(new Project(title.getText(), LocalDate.parse(date.getText().substring(11)), desc.getText()));
    	
    	// update projectGrid
    	// gets the MainController instance (passed when the projectCard was created). this is ass but idk how to pass props in jfx
    	MainController mainController = (MainController) title.getParent().getParent().getParent().getUserData();
    	// JavaScript's setTimeout but its ass
    	mainController.updateProjects();
        
    }
}

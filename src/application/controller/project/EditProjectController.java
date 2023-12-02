package application.controller.project;

import java.sql.Connection;
import java.time.LocalDate;

import application.controller.main.MainController;
import db.SQLConnector;
import entries.ProjectDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditProjectController {

    private Connection conn;

	@FXML private Label titleLabel;
    @FXML private TextField titleField;
	@FXML private DatePicker dateField;
	@FXML private TextArea descField;

	@FXML private Label titleError;
	@FXML private Label dateError;
	@FXML private Label descError;

	@FXML private Button cancelButton;
    @FXML private Button editButton;
    
    ProjectController projectController;
    MainController mainController;
    ProjectDAO projectDAO;

    @FXML
	public void initialize() {
		conn = SQLConnector.getConnection();
		projectDAO = new ProjectDAO(conn);
	}
    
    public void populateFields(Stage stage) {
		projectController = (ProjectController) stage.getUserData();
		mainController = (MainController) projectController.getProjectRoot().getParent().getParent().getParent().getParent().getParent().getParent().getUserData();
		
		titleLabel.setText("Edit Project: " + projectController.getTitle());
		titleField.setText(projectController.getTitle());
		dateField.setValue(projectController.getDate());	
		descField.setText(projectController.getDesc());
    }

    public void closeDialog() {
    	Stage stage = (Stage) editButton.getScene().getWindow();
    	projectController.setButtonState(true);						// enable newProjectButton
		mainController.updateProjects();							// update projectGrid
		stage.close();												// close window
	}

    public void editProject() {
    	// get fields
		String title = titleField.getText();
		LocalDate date = dateField.getValue();
		String desc = descField.getText();
		// validation
		if (title.isEmpty()) titleError.setText("Project Title is required.");
		else titleError.setText("");
		if (date == null)dateError.setText("Date is required.");
		else dateError.setText("");
		// if everything is good, add the project
		if (!title.isEmpty() && date != null) {
			
			projectDAO.edit(projectController.getTitle(), title, date, desc);
			
			closeDialog();															// close window
		}
    }
}
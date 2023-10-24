package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.time.LocalDate;

import db.SQLConnector;
import entries.Project;
import entries.ProjectDAO;


public class CreateProjectController {

    private Connection conn;

    @FXML public TextField titleField;
	@FXML public DatePicker dateField;
	@FXML public TextArea descField;

	@FXML public Label titleError;
	@FXML public Label dateError;
	@FXML public Label descError;

	@FXML public Button cancelButton;
    @FXML public Button createButton;

    @FXML
	public void initialize() {
		conn = new SQLConnector().getConnection();
	}

    public void closeDialog() {


    	// get this stage
		Stage stage = (Stage) createButton.getScene().getWindow();
		// get newProjectButton that was sent from MainController. jfx does not have props so idk what to do.
		Button newProjectButton = (Button) stage.getUserData();
		// enable newProjectButton
		newProjectButton.setDisable(false);
		// close window
		stage.close();
	}

    public void createProject() {
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
			ProjectDAO projectDAO = new ProjectDAO(conn);
			projectDAO.createProject(new Project(title, date, desc));
			// get this stage
			Stage stage = (Stage) createButton.getScene().getWindow();
			// get newProjectButton that was sent from MainController. jfx does not have props so idk what to do.
			Button newProjectButton = (Button) stage.getUserData();
			// enable newProjectButton
			newProjectButton.setDisable(false);
			// close window
			stage.close();
		}
    }
}
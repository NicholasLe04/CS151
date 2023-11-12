package application.controller.main;

import java.sql.Connection;
import java.time.LocalDate;

import db.SQLConnector;
import entries.ProjectDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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
		conn = SQLConnector.getInstance().getConnection();
		dateField.setValue(LocalDate.now());
	}

    public void closeDialog() {
		Stage stage = (Stage) createButton.getScene().getWindow();				// get this stage
		MainController mainController = (MainController) stage.getUserData();	// get instance of MainController
		mainController.updateProjects("");							// update projectGrid
		mainController.setButtonState(true);						// enable newProjectButton
		stage.close();															// close window
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
			
			ProjectDAO projectDAO = new ProjectDAO(conn);							// add project
			projectDAO.createProject(title, date, desc);
			
			closeDialog();															// close window
		}
    }
}
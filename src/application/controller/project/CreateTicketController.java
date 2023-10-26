package application.controller.project;

import java.sql.Connection;
import java.time.LocalDate;

import db.SQLConnector;
import entries.ProjectDAO;
import entries.TicketDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTicketController {
	
	private Connection conn;
	
	@FXML public TextField titleField;
	@FXML public TextArea descField;

	@FXML public Label titleError;
	@FXML public Label descError;

	@FXML public Button cancelButton;
    @FXML public Button createButton;
	
	public void initialize() {
		conn = SQLConnector.getInstance().getConnection();
	}
	
	public void closeDialog() {
		Stage stage = (Stage) createButton.getScene().getWindow();				// get this stage
		ProjectController projectController = (ProjectController) stage.getUserData();	// get instance of MainController
		projectController.updateTickets();								// update ticketList
		projectController.setButtonState(true);						// enable newProjectButton
		stage.close();	
	}
	
	public void createTicket() {
		// get fields
		String title = titleField.getText();
		String desc = descField.getText();
		// validation
		if (title.isEmpty()) titleError.setText("Project Title is required.");
		else titleError.setText("");
		// if everything is good, add the project
		if (!title.isEmpty()) {
			
			Stage stage = (Stage) createButton.getScene().getWindow();				// get this stage
			ProjectController projectController = (ProjectController) stage.getUserData();	// get instance of MainController
			
			TicketDAO ticketDAO = new TicketDAO(conn);							// add project
			ticketDAO.createTicket(title, desc, projectController.getTitle());
			
			closeDialog();															// close window
		}
	}
	
}

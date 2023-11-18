package application.controller.project;

import java.sql.Connection;

import db.SQLConnector;
import entries.TicketDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTicketController {
	
	private Connection conn;
	TicketDAO ticketDAO;
	
	@FXML public TextField titleField;
	@FXML public TextArea descField;

	@FXML public Label titleError;
	@FXML public Label descError;

	@FXML public Button cancelButton;
    @FXML public Button createButton;
	
	public void initialize() {
		conn = SQLConnector.getConnection();
		ticketDAO = new TicketDAO(conn);
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
		// if everything is good, add the project
		else {
			titleError.setText("");
		
			Stage stage = (Stage) createButton.getScene().getWindow();				// get this stage
			ProjectController projectController = (ProjectController) stage.getUserData();	// get instance of MainController
			ticketDAO.createTicket(title, desc, projectController.getTitle());		// add project
			
			closeDialog();	
		}														// close window
		
	}
	
}

package application.controller.ticket;

import java.sql.Connection;

import application.controller.project.ProjectController;
import db.SQLConnector;
import entries.TicketDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditTicketController {

    private Connection conn;

	@FXML private Label titleLabel;
    @FXML private TextField titleField;
	@FXML private TextArea descField;

	@FXML private Label titleError;
	@FXML private Label descError;

	@FXML private Button cancelButton;
    @FXML private Button editButton;
    
    TicketController ticketController;
    ProjectController projectController;
    TicketDAO ticketDAO;

    @FXML
	public void initialize() {
		conn = SQLConnector.getConnection();
		ticketDAO = new TicketDAO(conn);
	}
    
    public void populateFields(Stage stage) {
		ticketController = (TicketController) stage.getUserData();
		projectController = (ProjectController) ticketController.getTicketRoot().getParent().getParent().getUserData();
		
		titleLabel.setText("Edit " + ticketController.getTitle());
		titleField.setText(ticketController.getTitle());
		descField.setText(ticketController.getDesc());
    }

    public void closeDialog() {
    	Stage stage = (Stage) editButton.getScene().getWindow();
    	ticketController.setButtonState(true);						// enable newTicketButton
		projectController.updateTickets();							// update ticketsGrid
		stage.close();												// close window
	}

    public void editTicket() {
    	// get fields
		String title = titleField.getText();
		String desc = descField.getText();
		// validation
		if (title.isEmpty()) titleError.setText("Ticket Title is required.");
		else {
			
			ticketDAO.edit(ticketController.getId(), title, desc);
			
			closeDialog();															// close window
		}
    }
}
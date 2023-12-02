package application.controller.comment;

import java.sql.Connection;

import application.controller.ticket.TicketController;
import db.SQLConnector;
import entries.CommentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class EditCommentController {

    private Connection conn;

	@FXML private TextArea bodyField;

	@FXML private Label bodyError;

	@FXML private Button cancelButton;
    @FXML private Button editButton;
    
    CommentController commentController;
    TicketController ticketController;
    CommentDAO commentDAO;

    @FXML
	public void initialize() {
		conn = SQLConnector.getConnection();
		commentDAO = new CommentDAO(conn);
	}
    
    public void populateFields(Stage stage) {
		commentController = (CommentController) stage.getUserData();
		ticketController = (TicketController) commentController.getCommentRoot().getParent().getParent().getUserData();
		bodyField.setText(commentController.getBody());
    }

    public void closeDialog() {
    	Stage stage = (Stage) editButton.getScene().getWindow();
    	commentController.setButtonState(true);						// enable newCommentButton
		ticketController.updateComments();							// update commentGrid
		stage.close();												// close window
	}

    public void editComment() {
    	// get fields
		String body = bodyField.getText();
		// validation
		if (bodyField.getText().isEmpty()) bodyError.setText("Comment Body is required.");
		else {
			
			commentDAO.edit(commentController.getId(), body);
			
			closeDialog();															// close window
		}
    }
}
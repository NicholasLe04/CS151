package application.controller.ticket;

import java.sql.Connection;

import application.controller.project.ProjectController;
import db.SQLConnector;
import entries.CommentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CreateCommentController {
	
	private Connection conn;
	private CommentDAO commentDAO;
	
	@FXML private TextArea bodyField;
	@FXML private Label bodyError;
	
	@FXML private Button cancelButton;
	@FXML private Button createButton;

    @FXML
    public void initialize() {
        conn = SQLConnector.getInstance().getConnection();
        commentDAO = new CommentDAO(conn);
    }

    public void closeBox() {
    	TicketController ticketController = (TicketController) cancelButton.getParent().getParent().getParent().getUserData();
        ticketController.updateComments();
        ticketController.setButtonState(true);
        ticketController.getTicketRoot().getChildren().remove(cancelButton.getParent().getParent());
    }

    public void createComment() {
    	// get fields
    	String body = bodyField.getText();
    	// validation
    	if (body.isEmpty()) bodyError.setText("Comment Body is required.");
    	// if everything is good, add the project
    	else {
    		bodyError.setText("");
	    	TicketController ticketController = (TicketController) createButton.getParent().getParent().getParent().getUserData();
	    	commentDAO.createComment(body, ticketController.getId());
	    	
	        closeBox();
        }
    }
}
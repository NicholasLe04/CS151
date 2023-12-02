package application.controller.comment;

import java.sql.Connection;
import java.time.LocalDateTime;

import application.controller.ticket.TicketController;
import db.SQLConnector;
import entries.CommentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CommentController {
	
	private Connection conn;
	private CommentDAO commentDAO;
	
	private int id;
	@FXML private Label body;
	@FXML private Label timestamp;
	@FXML private VBox commentRoot;
	
	@FXML private Button editCommentButton;
	@FXML private Button deleteCommentButton;
	
	@FXML
	public void initialize() {
		conn = SQLConnector.getConnection();
		commentDAO = new CommentDAO(conn);
	}
		
	public void setId(int id) {
		this.id = id;
	}
	
	public void setBody(String body) {
		this.body.setText(body);
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp.setText(timestamp.toLocalDate() + " " + timestamp.toLocalTime());
	}
	
	public VBox getCommentRoot() {
		return commentRoot;
	}
	
	public void deleteComment() {
		// add confirm dialog
		commentDAO.deleteComment(id);
		// update commentList
		// ticket controller to update commentList
		TicketController controller = (TicketController) body.getParent().getParent().getParent().getParent().getUserData();
		controller.updateComments();
	}
	
	public void showEditCommentBox() {
		System.out.println("edit " + id);
	}
	
	public void setButtonState(boolean state) {
		editCommentButton.setDisable(!state);
		deleteCommentButton.setDisable(!state);
	}
}

package application.controller.comment;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;

import application.controller.ticket.TicketController;
import db.SQLConnector;
import entries.CommentDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

	public int getId() {
		return id;
	}
		
	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body.getText();
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
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/comment/editCommentDialog.fxml"));
			Parent root = loader.load();
			// open new window
			Stage stage = new Stage();
			Scene scene = new Scene(root, 800, 500);
			stage.setScene(scene);
			stage.setOnCloseRequest(e -> setButtonState(true));
			stage.setUserData(this);
			((EditCommentController) loader.getController()).populateFields(stage);
			stage.show();			
			// disable button
			setButtonState(false);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	public void setButtonState(boolean state) {
		editCommentButton.setDisable(!state);
		deleteCommentButton.setDisable(!state);
		TicketController controller = (TicketController) body.getParent().getParent().getParent().getParent().getUserData();
		controller.setButtonState(state);
	}
}

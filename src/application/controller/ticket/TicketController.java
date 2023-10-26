package application.controller.ticket;

import java.sql.Connection;
import java.util.ArrayList;

import application.controller.comment.CommentController;
import db.SQLConnector;
import entries.Comment;
import entries.CommentDAO;
import entries.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicketController {
	
	private Connection conn;
	private CommentDAO commentDAO;
	
	private int id;
	@FXML private Label title;
	@FXML private Label desc;
	@FXML private VBox commentList;
	@FXML private VBox ticketRoot;

	@FXML private Button createCommentButton;
	@FXML private Button editTicketButton;
	@FXML private Button deleteTicketButton;
	
	@FXML
	public void initialize() {
		conn = SQLConnector.getInstance().getConnection();
		commentDAO = new CommentDAO(conn);
		updateComments();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public void setDesc(String desc) {
    	this.desc.setText(desc);
    }

	public void showCreateCommentBox() {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/ticket/createCommentBox.fxml"));
			Parent root = loader.load();
			// pass this Controller instance, so dialog can change things in the ticket
			root.setUserData(this);
			ticketRoot.getChildren().add(root);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public VBox getTicketRoot() {
		return ticketRoot; 
	}

	public void showEditTicketBox() {
		System.out.println("edit ticket");
	}
	
	public void updateComments() {
		ArrayList<Comment> comments = commentDAO.getComments(id);
		
		try {
			commentList.getChildren().clear();
			for (Comment comment : comments) {
				// load comment fxml
				FXMLLoader commentLoader = new FXMLLoader(getClass().getResource("/application/fxml/comment/commentCard.fxml"));
				Parent commentNode = commentLoader.load();
				// modify the ticketNode
				CommentController controller = (CommentController) commentNode.getUserData();
				controller.setId(comment.getId());
				controller.setBody(comment.getBody());
				controller.setTimestamp(comment.getTimestamp());
				// add comment
				commentList.getChildren().add(commentNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setButtonState(boolean state) {
		createCommentButton.setDisable(!state);
    	editTicketButton.setDisable(!state);
    	deleteTicketButton.setDisable(!state);
	}
}

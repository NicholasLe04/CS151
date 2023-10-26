package application.controller.ticket;

import java.sql.Connection;

import db.SQLConnector;
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
	private TicketDAO ticketDAO;
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
		ticketDAO = new TicketDAO(conn);
		commentDAO = new CommentDAO(conn);
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

	public void showEditCommentBox() {
		System.out.println("edit comment");
	}
	
	public void updateComments() {
		System.out.println("comments updated");
	}
	
	public void setButtonState(boolean state) {
		createCommentButton.setDisable(!state);
    	editTicketButton.setDisable(!state);
    	deleteTicketButton.setDisable(!state);
	}
}

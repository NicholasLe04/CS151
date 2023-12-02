package application.controller.ticket;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import application.controller.comment.CommentController;
import application.controller.project.ProjectController;
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
	private TicketDAO ticketDAO;
	
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
		conn = SQLConnector.getConnection();
		commentDAO = new CommentDAO(conn);
		ticketDAO = new TicketDAO(conn);
		updateComments();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title.getText();
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public String getDesc() {
		return desc.getText();
	}
	
	public void setDesc(String desc) {
    	this.desc.setText(desc);
    }

	public VBox getTicketRoot() {
		return ticketRoot; 
	}
	
	public void showCreateCommentBox() {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/ticket/createCommentBox.fxml"));
			Parent root = loader.load();
			ticketRoot.getChildren().add(root);
			setButtonState(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteTicket() {
		// add confirm dialog
		ticketDAO.deleteTicket(id);
		// update commentList
		ProjectController controller = (ProjectController) commentList.getParent().getParent().getParent().getUserData();
		controller.updateTickets();
	}

	public void showEditTicketDialog() {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/ticket/editTicketDialog.fxml"));
			Parent root = loader.load();
			// open new window
			Stage stage = new Stage();
			Scene scene = new Scene(root, 800, 500);
			stage.setScene(scene);
			stage.setOnCloseRequest(e -> setButtonState(true));
			stage.setUserData(this);
			((EditTicketController) loader.getController()).populateFields(stage);
			stage.show();			
			// disable button
			setButtonState(false);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	// this reloads the comment list, like in react!
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setButtonState(boolean state) {
		createCommentButton.setDisable(!state);
    	editTicketButton.setDisable(!state);
    	deleteTicketButton.setDisable(!state);
		ProjectController projectController = (ProjectController) ticketRoot.getParent().getParent().getUserData();
		projectController.setButtonState(state);
	}
}

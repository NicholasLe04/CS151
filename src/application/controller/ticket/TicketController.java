package application.controller.ticket;

import java.sql.Connection;

import db.SQLConnector;
import entries.CommentDAO;
import entries.TicketDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TicketController {
	
	private Connection conn;
	private TicketDAO ticketDAO;
	private CommentDAO commentDAO;
	
	private int id;
	@FXML private Label title;
	@FXML private Label desc;
	@FXML private VBox commentList;
	
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
}

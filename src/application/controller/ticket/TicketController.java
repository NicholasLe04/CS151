package application.controller.ticket;

import java.sql.Connection;

import db.SQLConnector;
import entries.CommentDAO;
import entries.TicketDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketController {
	
	private Connection conn;
	private TicketDAO ticketDAO;
	private CommentDAO commentDAO;
	
	@FXML public Label title;
	@FXML public Label desc;
	
	@FXML
	public void initialize() {
		conn = new SQLConnector().getConnection();
		ticketDAO = new TicketDAO(conn);
		commentDAO = new CommentDAO(conn);
	}
	
	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public void setDesc(String desc) {
    	this.desc.setText(desc);
    }
}

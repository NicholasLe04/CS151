package application.controller.project;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import application.controller.main.MainController;
import application.controller.ticket.TicketController;
import db.SQLConnector;
import entries.Project;
import entries.ProjectDAO;
import entries.Ticket;
import entries.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProjectController {
	
	private Connection conn;
	private ProjectDAO projectDAO;
	private TicketDAO ticketDAO;

	@FXML public Label title;
	@FXML public Label date;
	@FXML public Label desc;
	@FXML public VBox ticketList;
	
	@FXML
	public void initialize() {
		conn = new SQLConnector().getConnection();
		projectDAO = new ProjectDAO(conn);
		ticketDAO = new TicketDAO(conn);
	}

	/**
	 * 
	 * @param String name
	 */
    public void setTitle(String title) {
        this.title.setText(title);
    }
    
    public void setDate(LocalDate date) {
        this.date.setText("Started on " + date.toString());
    }
    
    public void setDesc(String desc) {
    	this.desc.setText(desc);
    }
    
    public void deleteProject() {
    	// add confirmation dialog
    	projectDAO.deleteProject(new Project(title.getText(), LocalDate.parse(date.getText().substring(11)), desc.getText()));
    	
    	// update projectGrid
    	// gets the MainController instance (passed when the projectCard was created). this is ass but idk how to pass props in jfx
    	MainController mainController = (MainController) title.getParent().getParent().getParent().getUserData();
    	// JavaScript's setTimeout but its ass
    	mainController.updateProjects();
        
    }
    
    public void showEditProjectDialog() {
    	System.out.println("edit " + title.getText());
    }
    
    public void updateTickets() {
    	ArrayList<Ticket> tickets = ticketDAO.getTickets(new Project(title.getText(), LocalDate.parse(date.getText().substring(11)), desc.getText()));
    	
    	try {
	    	for (int i = 0; i < tickets.size(); i++) {
	    		// load ticket fxml
	    		FXMLLoader ticketLoader = new FXMLLoader(getClass().getResource("/application/fxml/ticket/ticketCard.fxml"));
	    		Parent ticketNode = ticketLoader.load();
	    		// modify the ticketNode
	    		TicketController controller = (TicketController) ticketNode.getUserData();
	    		// this passes the ProjectController to the ticketList
	    		ticketNode.setUserData(this);
	    		// add ticket
	    		ticketList.getChildren().add(ticketNode);
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}

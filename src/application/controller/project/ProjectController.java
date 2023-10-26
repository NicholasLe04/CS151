package application.controller.project;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import application.controller.main.MainController;
import application.controller.ticket.TicketController;
import db.SQLConnector;
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

	@FXML private Label title;
	@FXML private Label date;
	@FXML private Label desc;
	@FXML private VBox ticketList;
	
	@FXML
	public void initialize() {
		conn = SQLConnector.getInstance().getConnection();
		projectDAO = new ProjectDAO(conn);
		ticketDAO = new TicketDAO(conn);
		updateTickets();
	}

	public String getTitle() {
		return title.getText();
	}
	
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
    	projectDAO.deleteProject(title.getText());
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
    	ArrayList<Ticket> tickets = ticketDAO.getTickets(title.getText());
    	
    	try {
	    	for (int i = 0; i < tickets.size(); i++) {
	    		// load ticket fxml
	    		FXMLLoader ticketLoader = new FXMLLoader(getClass().getResource("/application/fxml/ticket/ticketCard.fxml"));
	    		Parent ticketNode = ticketLoader.load();
	    		// modify the ticketNode
	    		TicketController controller = (TicketController) ticketNode.getUserData();
	    		controller.setId(tickets.get(i).getId());
	    		controller.setTitle(tickets.get(i).getTitle());
	    		controller.setDesc(tickets.get(i).getDesc());
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

package application.controller.project;

import java.io.IOException;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectController {
	
	private Connection conn;
	private ProjectDAO projectDAO;
	private TicketDAO ticketDAO;

	@FXML private Label title;
	@FXML private Label date;
	@FXML private Label desc;
	
	@FXML private Button createTicketButton;
	@FXML private Button editProjectButton;
	@FXML private Button deleteProjectButton;
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
    	mainController.updateProjects();
        
    }

    public void showEditProjectDialog() {
    	System.out.println("edit " + title.getText());
    }
    
    public void showCreateTicketDialog() {
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/project/createTicketDialog.fxml"));
			Parent root = loader.load();
			// open new window
			Stage stage = new Stage();
			Scene scene = new Scene(root, 800, 500);
			stage.setScene(scene);
			// pass this Controller instance, so dialog can change things in the project
			stage.setUserData(this);
			stage.setOnCloseRequest(e -> setButtonState(true));
			stage.show();			
			// disable button
			setButtonState(false);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateTickets() {
    	ArrayList<Ticket> tickets = ticketDAO.getTickets(title.getText());
    	
    	try {
    		ticketList.getChildren().clear();
	    	for (int i = 0; i < tickets.size(); i++) {
	    		// load ticket fxml
	    		FXMLLoader ticketLoader = new FXMLLoader(getClass().getResource("/application/fxml/ticket/ticketCard.fxml"));
	    		Parent ticketNode = ticketLoader.load();
	    		// modify the ticketNode
	    		TicketController controller = (TicketController) ticketNode.getUserData();
	    		controller.setId(tickets.get(i).getId());
	    		controller.setTitle(tickets.get(i).getTitle());
	    		controller.setDesc(tickets.get(i).getDesc());
	    		// get comments too 
	    		controller.updateComments();
	    		// this passes the ProjectController to the ticketList
	    		ticketNode.setUserData(this);
	    		// add ticket
	    		ticketList.getChildren().add(ticketNode);
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void setButtonState(boolean state) {
    	createTicketButton.setDisable(!state);
    	editProjectButton.setDisable(!state);
    	deleteProjectButton.setDisable(!state);
    }
}

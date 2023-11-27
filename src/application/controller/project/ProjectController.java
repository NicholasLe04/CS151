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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProjectController {
	
	private Connection conn;
	private ProjectDAO projectDAO;
	private TicketDAO ticketDAO;

	@FXML private Label title;
	@FXML private Label date;
	@FXML private Label desc;
	@FXML private VBox projectRoot;
	@FXML private TextField searchBar;
	
	@FXML private Button createTicketButton;
	@FXML private Button editProjectButton;
	@FXML private Button deleteProjectButton;
	@FXML private VBox ticketList;
	
	private String searchText;
	
	@FXML
	public void initialize() {
		conn = SQLConnector.getConnection();
		projectDAO = new ProjectDAO(conn);
		ticketDAO = new TicketDAO(conn);
		updateTickets();
	}

	public String getTitle() {
		return title.getText();
	}
	
	public LocalDate getDate() {
		return LocalDate.parse(date.getText().substring(11));
	}
	
	public String getDesc() {
		return desc.getText();
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
    
    public VBox getProjectRoot() {
    	return projectRoot;
    }
    
    public void deleteProject() {
    	// add confirmation dialog
    	projectDAO.deleteProject(title.getText());
    	// update projectGrid
    	// gets the MainController instance (passed when the projectCard was created). this is ass but idk how to pass props in jfx
    	MainController controller = (MainController) ticketList.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getUserData();
    	controller.updateProjects();
        
    }

    public void showEditProjectDialog() {
    	try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/project/editProjectDialog.fxml"));
			Parent root = loader.load();
			// open new window
			Stage stage = new Stage();
			Scene scene = new Scene(root, 800, 500);
			stage.setScene(scene);
			stage.setOnCloseRequest(e -> setButtonState(true));
			stage.setUserData(this);
			((EditProjectController) loader.getController()).populateFields(stage);
			stage.show();			
			// disable button
			setButtonState(false);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
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
			stage.setOnCloseRequest(e -> setButtonState(true));
			stage.setUserData(this);
			stage.show();			
			// disable button
			setButtonState(false);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateTickets() {
    	if (searchText == null) updateTickets("");
    	else updateTickets(searchText);
    }
    
    // this reloads the project grid, like in react!
    public void updateTickets(String search) {
    	ArrayList<Ticket> tickets = ticketDAO.getTickets(title.getText(), search);
    	
    	try {
    		ticketList.getChildren().clear();
	    	for (Ticket ticket : tickets) {
	    		// load ticket fxml
	    		FXMLLoader ticketLoader = new FXMLLoader(getClass().getResource("/application/fxml/ticket/ticketCard.fxml"));
	    		Parent ticketNode = ticketLoader.load();
	    		// modify the ticketNode
	    		TicketController controller = (TicketController) ticketNode.getUserData();
	    		controller.setId(ticket.getId());
	    		controller.setTitle(ticket.getTitle());
	    		controller.setDesc(ticket.getDesc());
	    		// get comments too 
	    		controller.updateComments();
	    		// add ticket
	    		ticketList.getChildren().add(ticketNode);
	    	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void setButtonState(boolean state) {
    	createTicketButton.setDisable(!state);
    	editProjectButton.setDisable(!state);
    	deleteProjectButton.setDisable(!state);
    }
}

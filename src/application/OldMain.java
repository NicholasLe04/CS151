package application;
	
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entries.Project;
import entries.ProjectDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OldMain extends Application {
	
	db.SQLConnector db = new db.SQLConnector();
	Connection conn = db.getConnection();
	ProjectDAO projectDAO = new ProjectDAO(conn); // TODO: CHANGE
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Main layout for the entire scene
	        BorderPane root = new BorderPane();

	        // Create a title label for "Projects" and style it
	        Label titleLabel = new Label("Projects");
	        titleLabel.getStyleClass().add("title-label");

	        // Create a button with a plus icon inside a circle
	        Button newProject = new Button("+");
	        newProject.getStyleClass().addAll("circle-button", "plus-button");
	        newProject.setOnAction(e -> newProjectDialog(root));

	        // Create an HBox to hold the title label and center it
	        HBox titleBox = new HBox(titleLabel);
	        titleBox.setAlignment(Pos.TOP_LEFT);
	        
	        // Create a VBox to hold the button and align it to the bottom-right
	        VBox buttonBox = new VBox(newProject);
	        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

	        // Apply margins to the title and button
	        BorderPane.setMargin(titleBox, new Insets(50, 50, 50, 50)); // Top, Right, Bottom, Left
	        BorderPane.setMargin(buttonBox, new Insets(50, 50, 50, 50)); // Top, Right, Bottom, Left

	        // Create a GridPane to hold the stored projects
	        GridPane projectGrid = createProjectGrid();

	        // Set the title label at the top-center and button at bottom-right
	        root.setTop(titleBox);
	        root.setBottom(buttonBox);
	        root.setCenter(projectGrid);	

	        //Set Background Color
	        root.setStyle("-fx-background-color: #333;");
	        
	        Scene scene = new Scene(root, 1200, 675);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	        primaryStage.setTitle("Project Management App");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private GridPane createProjectGrid() {
		GridPane projectGrid = new GridPane();
        projectGrid.setHgap(10);
        projectGrid.setVgap(10);
        
        // Fetch projects from DB
        ArrayList<Project> projects = projectDAO.getProjects();
        
        // Loop through projects and display them in a tile
        for (int index = 0; index < projects.size(); index++) {
        	StackPane tile = new StackPane();
        	VBox container = new VBox(10); 
        	container.setMinSize(200, 200);
        	container.setMaxSize(200, 200);
        	
        	Label title = new Label(projects.get(index).getName());
        	
        	Text date = new Text("Started on " + projects.get(index).getDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        	date.getStyleClass().add("project-date");
        	
        	Text description = new Text(projects.get(index).getDesc());
        	description.setWrappingWidth(200);
        	
        	container.getChildren().addAll(title, date, description);
        	tile.getChildren().add(container);
        	tile.getStyleClass().add("project-tile");
        	
        	projectGrid.add(tile, (index % 4), (index / 4));
	        projectGrid.setAlignment(Pos.CENTER);
        }
        
        return projectGrid;
	}
	
	// Fetches and displays stored projects
	private void updateProjectGrid(BorderPane root) {
		root.setCenter(createProjectGrid());
	}
	
	private void newProjectDialog(BorderPane root) {
	    Stage dialogStage = new Stage();
	    dialogStage.initModality(Modality.APPLICATION_MODAL);
	    dialogStage.setTitle("New Project");

	    // Create labels and input fields for project details
	    Label titleLabel = new Label("Project Title:");
	    Label dateLabel = new Label("Date:");
	    Label descriptionLabel = new Label("Description:");

	    TextField titleField = new TextField();

	    // Create an HBox to hold the date-related UI elements
	    HBox dateBox = new HBox(10);
	    DatePicker datePicker = new DatePicker();
	    datePicker.setValue(LocalDate.now());
	    dateBox.getChildren().add(datePicker);

	    TextArea descriptionArea = new TextArea();

	    // Create buttons
	    Button cancelButton = new Button("Cancel");
	    Button createButton = new Button("Create");

	    // Create layout for the dialog
	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setAlignment(Pos.CENTER);

	    // Set the background color for the entire dialog directly in Java code
	    grid.setStyle("-fx-background-color: #333;");

	    // Arrange the elements with labels above their respective fields
	    grid.add(titleLabel, 0, 0);
	    grid.add(titleField, 0, 1);

	    grid.add(dateLabel, 0, 2);
	    grid.add(dateBox, 0, 3);

	    grid.add(descriptionLabel, 0, 4);
	    grid.add(descriptionArea, 0, 5);

	    // Create an HBox to hold the buttons and align them to the right
	    HBox buttonBox = new HBox(10);
	    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
	    buttonBox.getChildren().addAll(cancelButton, createButton);

	    // Add the buttonBox to the grid
	    grid.add(buttonBox, 0, 6);

	    Scene dialogScene = new Scene(grid, 800, 450);

	    // Set the action for the "Cancel" button to close the dialog
	    cancelButton.setOnAction(e -> dialogStage.close());

	    // Set the action for the "Create" button with validation
	    createButton.setOnAction(e -> {
	        String projectName = titleField.getText();
	        LocalDate selectedDate = datePicker.getValue();
	        String projectDescription = descriptionArea.getText();

	        // Initialize an error message
	        String errorMessage = "";

	        // Validate required fields (name and date)
	        if (projectName.isEmpty()) {
	            errorMessage += "Project Name is required.\n";
	            titleField.setStyle("-fx-border-color: red;");
	        } else {
	            titleField.setStyle(null); // Remove the red border if valid
	        }

	        // Add the Project to the ProjectManager
	        projectDAO.createProject(new Project(projectName, selectedDate, projectDescription));
	        updateProjectGrid(root);

	        // Close the dialog
	        dialogStage.close();

	        // Display error message in a dialog
	        if (!errorMessage.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Validation Error");
	            alert.setHeaderText("Please correct the following errors:");
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	        }
	    });

	    dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    dialogStage.setScene(dialogScene);
	    dialogStage.showAndWait();
	}

}

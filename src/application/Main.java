package application;
	
import java.sql.Connection;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

import entries.ProjectManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
	
	db.SQLConnector db = new db.SQLConnector();
	Connection conn = db.getConnection();
	ProjectManager projectManager = new ProjectManager(conn);
	
	public static void main(String[] args) {
		launch(args);
	}
	
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
	        newProject.setOnAction(e -> newProjectDialog());

	        // Create an HBox to hold the title label and center it
	        HBox titleBox = new HBox(titleLabel);
	        titleBox.setAlignment(Pos.TOP_LEFT);

	        // Create a VBox to hold the button and align it to the bottom-right
	        VBox buttonBox = new VBox(newProject);
	        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

	        // Apply margins to the title and button
	        BorderPane.setMargin(titleBox, new Insets(50, 50, 50, 50)); // Top, Right, Bottom, Left
	        BorderPane.setMargin(buttonBox, new Insets(50, 50, 50, 50)); // Top, Right, Bottom, Left

	        // Set the title label at the top-center and button at bottom-right
	        root.setTop(titleBox);
	        root.setBottom(buttonBox);

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
	
	private void newProjectDialog() {
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
	    ComboBox<String> monthComboBox = new ComboBox<>();
	    monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July",
	            "August", "September", "October", "November", "December");
	    ComboBox<String> dayComboBox = new ComboBox<>();
	    dayComboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
	            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
	            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
	    TextField yearField = new TextField();
	    dateBox.getChildren().addAll(monthComboBox, dayComboBox, yearField);

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
	        String selectedMonth = monthComboBox.getValue();
	        String selectedDay = dayComboBox.getValue();
	        String selectedYear = yearField.getText();
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

	        if (selectedMonth == null || selectedDay == null || selectedYear.isEmpty()) {
	            errorMessage += "Month, Day, and Year fields are required.\n";
	            monthComboBox.setStyle("-fx-border-color: red;");
	            dayComboBox.setStyle("-fx-border-color: red;");
	            yearField.setStyle("-fx-border-color: red;");
	        } else {
	            // Check if the year is a valid integer
	            try {
	                int year = Integer.parseInt(selectedYear);
	                yearField.setStyle(null); // Remove the red border if valid

	                // Create a LocalDate object from the selected date components
	                LocalDate projectDate = LocalDate.of(year, Month.valueOf(selectedMonth.toUpperCase()), Integer.parseInt(selectedDay));

	                // Add the Project to the ProjectManager
	                projectManager.createProject(projectName, projectDate, projectDescription);

	                // Close the dialog
	                dialogStage.close();
	            } catch (NumberFormatException | DateTimeException ex) {
	                errorMessage += "Year must be a valid integer, and date must be valid.\n";
	                yearField.setStyle("-fx-border-color: red;");
	            }

	            monthComboBox.setStyle(null); // Remove the red border if valid
	            dayComboBox.setStyle(null); // Remove the red border if valid
	        }

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

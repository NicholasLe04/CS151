package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;

import db.SQLConnector;


public class CreateProjectController {

    private Connection conn;

    @FXML public TextField title;
	@FXML public DatePicker date;
	@FXML public TextArea desc;
	@FXML public Button cancelButton;
    @FXML public Button createButton;

    @FXML
	public void initialize() {
		conn = new SQLConnector().getConnection();
	}

    public void closeDialog() {
    	try {
    		Stage stage = (Stage) cancelButton.getScene().getWindow();
    		// enable newProjectButton
			Button newProjectButton = (Button) stage.getUserData();
			newProjectButton.setDisable(false);
			// close window
		    stage.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}

    public void createProject() {
    	try {
    		Stage stage = (Stage) cancelButton.getScene().getWindow();
    		// enable newProjectButton
			Button newProjectButton = (Button) stage.getUserData();
			newProjectButton.setDisable(false);
			// close window
		    stage.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
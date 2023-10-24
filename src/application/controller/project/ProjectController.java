package application.controller.project;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProjectController {

	@FXML public Label title;
	@FXML public Label date;
	@FXML public Label desc;

    public void setName(String name) {
        this.title.setText(name);
    }
    
    public void setDate(LocalDate date) {
        this.date.setText("Started on " + date.toString());
    }
    
    public void setDesc(String desc) {
    	this.desc.setText(desc);
    }
}

package application;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

import db.SQLConnector;
import entries.CommentDAO;
import entries.ProjectDAO;
import entries.TicketDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		// TODO: REMOVE THIS. THIS IS JUST FOR CODE VER 0.5
//		Connection conn = SQLConnector.getConnection();
//		ProjectDAO projectDAO = new ProjectDAO(conn);
//		TicketDAO ticketDAO = new TicketDAO(conn);
//		CommentDAO commentDAO = new CommentDAO(conn);
//		projectDAO.createProject("odin-recipies", LocalDate.now(), "basic HTML website from the odin project! https://github.com/initialkermit/odin-recipes");
//		ticketDAO.createTicket("its too ugly", "the website doesnt have any css its too ugly. \npssst... identical tickets and comments may be added everytime the app is started. this has to do with how were prepopulating our projects, and is not a bug present during normal app use.", "odin-recipies");
//		commentDAO.createComment("i need to add <stylesheet />", 1);
//		commentDAO.createComment("i need to push new css", 1);
//		ticketDAO.createTicket("nothing happens when you click", "i suck at javascript", "odin-recipies");
//		commentDAO.createComment("i need to add <script />", 2);
//		commentDAO.createComment("i do not know javascript", 2);
//		
//		projectDAO.createProject("kanji-classifier", LocalDate.now(), "OCR webapp that classifies 3000+ Japanese Kanji. https://github.com/al3xbro/kanji-classifier");
//		ticketDAO.createTicket("model is too slow", "the CNN takes forever to classify things under load. \npssst... try adding/deleting your own projects, tickets, and comments. everything is fully functional, except for editing!", "kanji-classifier");
//		commentDAO.createComment("i need to prune the model", 3);
//		commentDAO.createComment("idk how to", 3);
//		ticketDAO.createTicket("security concerns with endpoint", "i fear my home network may be hacked", "kanji-classifier");
//		commentDAO.createComment("the linkedin guy said it was okay", 4);
//		commentDAO.createComment("i may move to aws", 4);
		
		try {
			// load fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/fxml/main/mainScene.fxml"));
			Parent root = loader.load();
			// open new window
			primaryStage.setTitle("Stomp");
			primaryStage.setScene(new Scene(root, 1200, 675));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

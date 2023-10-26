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
		// TODO: REMOVE THIS. THIS IS JUST FOR CODE VER 0.4
		Connection conn = SQLConnector.getInstance().getConnection();
		ProjectDAO projectDAO = new ProjectDAO(conn);
		TicketDAO ticketDAO = new TicketDAO(conn);
		CommentDAO commentDAO = new CommentDAO(conn);
		projectDAO.createProject("kanji-classifier", LocalDate.now(), "OCR webapp that classifies 3000+ Japanese Kanji. https://github.com/al3xbro/kanji-classifier");
		projectDAO.createProject("blockfish", LocalDate.now(), "stockfish but bad. https://github.com/NicholasLe04/blockfish");
		projectDAO.createProject("odin-recipies", LocalDate.now(), "basic HTML website from the odin project! https://github.com/initialkermit/odin-recipes");
		ticketDAO.createTicket("its too ugly", "the website doesnt have any css its too ugly", "odin-recipies");
		ticketDAO.createTicket("model is too slow", "the CNN takes forever to classify things under load", "kanji-classifier");
		commentDAO.createComment("try hosting the model on AWS", 2);
		commentDAO.createComment("need to compress model as well", 2);
		ticketDAO.createTicket("security concerns with endpoint", "i fear my home network may be hacked", "kanji-classifier");
		
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

package db;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnector {
	
	private static Connection conn;

	/**
	 * Establish connection with SQLite DB. To be run only once on application startup.
	 */
	private SQLConnector() {
		// get directory of jar file
		String directory = "";
		try {
			directory = SQLConnector.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            directory = directory.substring(1, directory.lastIndexOf("/"));
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
		
		// create directory if not exist
		try {
			Files.createDirectories(Paths.get(directory + "/data"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// attempt create/connect database 
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + directory + "/data/data.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conn = conn;
		
		// attempt create all tables if not exist
		try {
			Statement statement = conn.createStatement();
			statement.execute("PRAGMA foreign_keys = ON");
			statement.execute(
				"CREATE TABLE IF NOT EXISTS project(" + 
					"project_title TEXT UNIQUE NOT NULL PRIMARY KEY, " +
					"start_date TEXT NOT NULL, " +
					"desc TEXT" +
				")"
			);
			statement.execute(
				"CREATE TABLE IF NOT EXISTS ticket(" +
					"ticket_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"ticket_title TEXT NOT NULL, " +
					"desc TEXT, " +
					"project_title TEXT NOT NULL, " +
					"FOREIGN KEY(project_title) REFERENCES project(project_title) ON DELETE CASCADE ON UPDATE CASCADE" +
				")"
			);
			statement.execute(
				"CREATE TABLE IF NOT EXISTS comment(" +
					"comment_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"created_at TEXT NOT NULL, " +
					"comment_body TEXT NOT NULL, " +
					"ticket_id INTEGER NOT NULL, " +
					"FOREIGN KEY(ticket_id) REFERENCES ticket(ticket_id) ON DELETE CASCADE ON UPDATE CASCADE" +
				")"
			);
			statement.execute(
				"CREATE INDEX IF NOT EXISTS project_title_idx " +
					"ON project(project_title)"
			);
			statement.execute(
				"CREATE INDEX IF NOT EXISTS ticket_id_idx " +
					"ON ticket(ticket_id)"
			);
			statement.execute(
				"CREATE INDEX IF NOT EXISTS comment_id_idx " +
					"ON comment(comment_id)"
			);
			statement.execute(
				"CREATE INDEX IF NOT EXISTS ticket_title_idx " +
					"ON ticket(ticket_title)"
			);
			statement.execute(
				"CREATE INDEX IF NOT EXISTS ticket_project_title_idx " +
					"ON ticket(project_title)"
			);
			statement.execute(
				"CREATE INDEX IF NOT EXISTS comment_ticket_id_idx " +
					"ON comment(ticket_id)"
			);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return db Connection object.
	 * @return Connection object
	 */
	public static Connection getConnection() {
		if (conn == null) new SQLConnector();
		return conn;
	}
}

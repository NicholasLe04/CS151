package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.net.URISyntaxException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SQLConnector {
	
	private Connection conn;

	/**
	 * Establish connection with SQLite DB. To be run only once on application startup.
	 */
	public SQLConnector() {
		
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
			if (conn != null) System.out.println("Database connected successfully!");
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
					"name TEXT UNIQUE NOT NULL PRIMARY KEY, " +
					"start_date TEXT NOT NULL, " +
					"desc TEXT" +
				")"
			);
			statement.execute(
				"CREATE TABLE IF NOT EXISTS ticket(" +
					"tid INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY, " +
					"name TEXT NOT NULL, " +
					"desc TEXT, " +
					"p_name TEXT NOT NULL, " +
					"FOREIGN KEY(p_name) REFERENCES project(name) ON DELETE CASCADE" +
				")"
			);
			statement.execute(
				"CREATE TABLE IF NOT EXISTS comment(" +
					"cid INT AUTO_INCREMENT UNIQUE NOT NULL PRIMARY KEY, " +
					"created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
					"body TEXT NOT NULL, " +
					"tid INT NOT NULL, " +
					"FOREIGN KEY(tid) REFERENCES ticket(tid) ON DELETE CASCADE" +
				")"
			);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return db Connection object.
	 * @return Connection object
	 */
	public Connection getConnection() {
		return conn;
	}
}

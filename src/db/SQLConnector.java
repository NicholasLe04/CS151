package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
		
		// TODO: attempt create all tables if not exist
		
		this.conn = conn;
	}
	
	/**
	 * Return db Connection object.
	 * @return Connection object
	 */
	public Connection getConnection() {
		return conn;
	}
}

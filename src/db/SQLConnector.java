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
	 * Establish connection with SQLite DB. Only supposed to be run once on object creation.
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
		
		try {
			Files.createDirectories(Paths.get(directory + "/data"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// attempt create database 
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + directory + "/data/data.db");
			if (conn != null) System.out.println("Database connected successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.conn = conn;
	}
	
	/**
	 * Return db Connection object.
	 * @return db Connection object
	 */
	public Connection getConnection() {
		return conn;
	}
}

package entries;

import java.util.ArrayList;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ProjectManager {
	
	private Connection conn;
	
	private ArrayList<Project> projects;
	
	/**
	 * Instantiate a ProjectManager object that contains all persistent Project objects.
	 * @param sql.Connection conn
	 */
	public ProjectManager(Connection conn) {
		this.projects = new ArrayList<>();
		this.conn = conn;
		
		try {
			// queries for all projects
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM project"
			);
			// places everything in ArrayList projects
			while (res.next()) {
				String name = res.getString(1);
				String startDate = res.getString(2);
				String desc = res.getString(3);
				projects.add(new Project(name, LocalDate.parse(startDate), desc, conn));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Create a persistent project.
	 * @param String name
	 * @param LocalDate date
	 * @param String desc
	 */
	public void createProject(String name, LocalDate date, String desc) {
		
		// insert project into database
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"INSERT INTO project " +
				"VALUES('" + name + "', '" + date.toString() + "', '" + desc + "')"
			);			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		// add project to ArrayList in memory
		projects.add(new Project(name, date, desc, conn));
	}
	
	/**
	 * Delete a project with a specific name.
	 * @param String name
	 */
	public void deleteProject(String name) {
		// remove project from database
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM project " +
				"WHERE project_name=" + name
			);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		// remove project from ArrayList. i know this implementation sucks and is O(2n),
		// but in my defense it should still be negligible.
		// runtime should be better with HashMap, but that makes everything harder
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).getName().equals(name)) {
				projects.remove(i);
			}
		}
	}
	
	/**
	 * Get all Project objects.
	 * @return ArrayList of all Project objects
	 */
	public ArrayList<Project> getProjects() {
		return projects;
	}
}

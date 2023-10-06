package entries;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

public class ProjectManager {
	
	Connection conn;
	
	ArrayList<Project> projects;
	
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
				Project project = new Project(name, LocalDate.parse(startDate), desc, conn);
				projects.add(project);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(projects);
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
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
	
	public void deleteProject(String name) {
		// TODO: remove project from database and ArrayList
	}
}

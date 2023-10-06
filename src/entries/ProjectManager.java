package entries;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectManager {
	
	Connection conn;
	
	ArrayList<Project> projects;
	
	public ProjectManager(Connection conn) {
		this.conn = conn;
		// TODO: SQL SELECT all projects, populate ArrayList, pass conn to all of them
		
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	public void createProject(String name, LocalDate date, String desc) {
		// TODO: SQL INSERT
		// TODO: create new project object, put it ArrayList
		
	}
	
	public void deleteProject(String name) {
		// TODO: call ticket.delete on every ticket in that project
		// TODO: remove project from database and ArrayList
		// planning to recursively remove from databases
	}
}

package entries;

import java.sql.Connection;
import java.util.ArrayList;

public class ProjectManager {
	
	Connection conn;
	
	ArrayList<Project> projects;
	
	public ProjectManager() {
		// TODO: SQL SELECT all projects, populate ArrayList, pass conn to all of them
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	public void createProject() {
		// TODO: SQL INSERT
		// TODO: create new project object, put it ArrayList
	}
	
	public void deleteProject() {
		// TODO: call ticket.delete on every ticket in that project
		// TODO: remove project from database and ArrayList
		// planning to recursively remove from databases
	}
}

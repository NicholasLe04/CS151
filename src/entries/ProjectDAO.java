package entries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectDAO {
	
	Connection conn;

	public ProjectDAO(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<Project> getProjects() {
		ArrayList<Project> toReturn = new ArrayList<>();
		try {
			// queries for all projects
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM project"
			);
			// places everything in ArrayList projects
			while (res.next()) {
				String projectName = res.getString(1);
				String projectStartDate = res.getString(2);
				String projectDesc = res.getString(3);
				toReturn.add(new Project(projectName, LocalDate.parse(projectStartDate), projectDesc));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return toReturn;
	}
	
	public void createProject(Project project) {
		// add project to db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"INSERT INTO project " +
				"VALUES('" + project.getName() + "', '" + project.getDate().toString() + "', '" + project.getDesc() + "')"
			);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void edit(Project project, String newName, LocalDate newDate, String newDesc) {
		// update ticket in db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE project " +
				"SET project_name='" + newName + "', start_date='" + newDate + "', desc='" + newDesc + "' " +
				"WHERE project_name='" + project.getName() + "'"
			);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// update Ticket object
		project.setName(newName);
		project.setDate(newDate);
		project.setDesc(newDesc);
	}

	public void deleteProject(Project project) {
		// remove project from db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM project " +
				"WHERE project_name='" + project.getName() + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

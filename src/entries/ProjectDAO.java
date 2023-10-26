package entries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectDAO {
	
	Connection conn;

	/**
	 * Create ProjectDAO object using Connection conn.
	 * @param Connection conn
	 */
	public ProjectDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Get persistent Projects
	 * @return ArrayList<Project> projects
	 */
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
				String projectTitle = res.getString(1);
				String projectStartDate = res.getString(2);
				String projectDesc = res.getString(3);
				toReturn.add(new Project(projectTitle, LocalDate.parse(projectStartDate), projectDesc));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return toReturn;
	}
	
	/**
	 * Persist a Project.
	 * @param Project toPersist
	 */
	public void createProject(String projectTitle, LocalDate projectDate, String projectDesc) {
		// add project to db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"INSERT INTO project " +
				"VALUES('" + projectTitle + "', '" + projectDate.toString() + "', '" + projectDesc + "')"
			);
		} catch(SQLException e) {
			// happens when projectTitle already exists 
			// REMOVED SO NO ERRORS ARE THROWN CODE VER 0.4
			// e.printStackTrace();
		}
	}

	/**
	 * Edit a Project.
	 * @param Project project
	 * @param String newName
	 * @param LocalDate newDate
	 * @param String newDesc
	 */
	public void edit(String oldName, String newName, LocalDate newDate, String newDesc) {
		// update ticket in db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE project " +
				"SET project_title='" + newName + "', start_date='" + newDate + "', desc='" + newDesc + "' " +
				"WHERE project_title='" + oldName + "'"
			);
		} catch(SQLException e) {
			// happens when oldName doesn't exist
			e.printStackTrace();
		}
	}

	/**
	 * Delete a Project.
	 * @param project
	 */
	public void deleteProject(String projectTitle) {
		// remove project from db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM project " +
				"WHERE project_title='" + projectTitle + "'"
			);
		} catch (SQLException e) {
			// happens when projectTitle doesn't exist
			e.printStackTrace();
		}
	}
}

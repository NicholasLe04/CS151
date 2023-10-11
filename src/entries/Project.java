package entries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Project {
	
	Connection conn;

	private String name;
	private LocalDate date;
	private String desc;
	private ArrayList<Ticket> tickets;
	
	/**
	 * Create Project object in memory
	 * @param String name
	 * @param LocalDate date
	 * @param String desc
	 * @param sql.Connection conn
	 */
	public Project(String name, LocalDate date, String desc, Connection conn) {
		this.tickets = new ArrayList<>();
		this.name = name;
		this.date = date;
		this.desc = desc;
		this.conn = conn;
		try {
			// queries for all tickets
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM ticket " + 
				"WHERE project_name='" + name + "'"
			);
			// places everything in ArrayList tickets
			while (res.next()) {
				int ticketId = res.getInt(1);
				String ticketName = res.getString(2);
				String ticketDesc = res.getString(3);
				tickets.add(new Ticket(ticketId, ticketName, ticketDesc, conn));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Edit this project.
	 * @param String name
	 * @param LocalDate date
	 * @param String desc
	 */
	public void edit(String name, LocalDate date, String desc) {
		// query
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE project " +
				"SET project_name='" + name + "', start_date='" + date + "', desc='" + desc + "' " +
				"WHERE project_name='" + this.name + "'"
			);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// modify object
		this.name = name;
		this.date = date;
		this.desc = desc;
	}
	
	
	/**
	 * Get name of Project.
	 * @return String name of Project
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get start date of Project.
	 * @return LocalDate start date of Project
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Get description of Project.
	 * @return String description of Project
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Get all tickets associated with this project
	 * @return ArrayList all tickets
	 */
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}
	
	/**
	 * Create a ticket associated with this project.
	 */
	public void createTicket(String ticketName, String ticketDesc) {
		// TODO: TEST THIS
		// add ticket to db
		int ticketId = 0;
		try {
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT last_insert_rowid() FROM ticket");
			statement.executeUpdate(
				"INSERT INTO ticket(ticket_name, desc, project_name) " +
				"VALUES('" + ticketName + "', '" + ticketDesc + "', '" + name + "')"
			);
			ticketId = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// add ticket to memory
		tickets.add(new Ticket(ticketId, ticketName, ticketDesc, conn));
	}
	
	/**
	 * Delete a ticket associated with this project.
	 */
	public void deleteTicket(Ticket toDelete) {
		// TODO: TEST THIS
		// remove from db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM ticket " +
				"WHERE ticket_id='" + toDelete.getId() + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// remove from memory
		tickets.remove(toDelete);
	}
	
	// TODO: REMOVE THIS IS FOR TESTING ONLY
	@Override
	public String toString() {
		return name + " " + date + " " + desc;
	}
}
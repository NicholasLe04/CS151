package entries;

import java.time.LocalDate;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Project {
	
	Connection conn;
	
	private int id;
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
		// TODO: SQL SELECT all tickets associated with this project, populate tickets ArrayList, pass conn to each of them
		try {
			// queries for all tickets
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM ticket " + 
				"WHERE project_name=" + name
			);
			// places everything in ArrayList tickets
			while (res.next()) {
				int ticket_id = res.getInt(1);
				String ticket_name = res.getString(2);
				String ticket_desc = res.getString(3);
				tickets.add(new Ticket(ticket_id, ticket_name, ticket_desc, conn));
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
			statement.executeQuery(
				"UPDATE project " +
				"SET project_name=" + name + ", start_date=" + date + ", desc=" + desc + " " +
				"WHERE project_name=" + name
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
	public void createTicket(String name, String desc) {
		// TODO: SQL INSERT
		// TODO: create new ticket object, passing conn
	}
	
	/**
	 * Delete a ticket associated with this project.
	 */
	public void deleteTicket(String name) {
		// TODO: call comment.delete on every comment in that ticket
		// TODO: remove ticket from database and ArrayList
		// planning to recursively remove from databases
	}
	
	// TODO: REMOVE THIS IS FOR TESTING ONLY
	@Override
	public String toString() {
		return name + " " + date + " " + desc;
	}
}
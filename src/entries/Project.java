package entries;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

public class Project {
	
	Connection conn;
	
	String name;
	LocalDate date;
	String desc;
	ArrayList<Ticket> tickets;
	
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
	}
	
	/**
	 * Edit this project.
	 * @param String name
	 * @param LocalDate date
	 * @param String desc
	 */
	public void edit(String name, LocalDate date, String desc) {
		this.name = name;
		this.date = date;
		this.desc = desc;
		// TODO: SQL UPDATE 
	}
	
	/**
	 * Return all tickets associated with this project
	 * @return All tickets
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
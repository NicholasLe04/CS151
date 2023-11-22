package entries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TicketDAO {
	
	Connection conn;

	/**
	 * Create TicketDAO object using Connection conn.
	 * @param conn
	 */
	public TicketDAO(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<Ticket> getTickets(String projectTitle, String searchText) {
		ArrayList<Ticket> tickets = new ArrayList<>();
		try {
			// queries for all tickets
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM ticket " + 
				"WHERE (project_title='" + projectTitle + "'" +
				" AND ticket_title LIKE '%" + searchText + "%')"
			);
			// places everything in ArrayList tickets
			while (res.next()) {
				int ticketId = res.getInt(1);
				String ticketTitle = res.getString(2);
				String ticketDesc = res.getString(3);
				tickets.add(new Ticket(ticketId, ticketTitle, ticketDesc));
			}
		} catch(SQLException e) {
			// should never happen
			e.printStackTrace();
		}
		return tickets;
	}

	/**
	 * Persist a Ticket.
	 * @param Ticket toPersist
	 */
	public void createTicket(String ticketTitle, String ticketDesc, String projectTitle) {
		// add ticket to db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"INSERT INTO ticket(ticket_title, desc, project_title) " +
				"VALUES('" + ticketTitle + "', '" + ticketDesc + "', '" + projectTitle + "')"
			);
		} catch (SQLException e) {
			// happens when projectTitle doesn't exist
			e.printStackTrace();
		}
	}

	/**
	 * Edit a ticket.
	 * @param ticket
	 * @param newName
	 * @param newDesc
	 */
	public void edit(int ticketId, String newName, String newDesc) {
		// update ticket in db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE ticket " +
				"SET ticket_title='" + newName + "', desc='" + newDesc + "' " +
				"WHERE ticket_id='" + ticketId + "'"
			);
		} catch(SQLException e) {
			// happens when ticketId doesn't eixst
			e.printStackTrace();
		}
	}

	/**
	 * Delete a ticket.
	 * @param Ticket toDelete
	 */
	public void deleteTicket(int ticketId) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM ticket " +
				"WHERE ticket_id='" + ticketId + "'"
			);
		} catch (SQLException e) {
			// happens when ticketId doesn't exist
			e.printStackTrace();
		}
	}
}

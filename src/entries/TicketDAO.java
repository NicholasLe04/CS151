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

	public ArrayList<Ticket> getTickets(Project project) {
		ArrayList<Ticket> tickets = new ArrayList<>();
		try {
			// queries for all tickets
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM ticket " + 
				"WHERE project_name='" + project.getName() + "'"
			);
			// places everything in ArrayList tickets
			while (res.next()) {
				int ticketId = res.getInt(1);
				String ticketName = res.getString(2);
				String ticketDesc = res.getString(3);
				tickets.add(new Ticket(ticketId, ticketName, ticketDesc, project));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}

	/**
	 * Persist a Ticket.
	 * @param Ticket toPersist
	 */
	public void createTicket(Ticket ticket) {
		try {
			Statement statement = conn.createStatement();
			// update Ticket object id property
			ticket.setId(statement.executeQuery("SELECT last_insert_rowid() FROM ticket").getInt(1));
			// add ticket to db
			statement.executeUpdate(
				"INSERT INTO ticket(ticket_name, desc, project_name) " +
				"VALUES('" + ticket.getName() + "', '" + ticket.getDesc() + "', '" + ticket.getProject().getName() + "')"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edit a ticket.
	 * @param ticket
	 * @param newName
	 * @param newDesc
	 */
	public void edit(Ticket ticket, String newName, String newDesc) {
		// update ticket in db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE ticket " +
				"SET ticket_name='" + newName + "', desc='" + newDesc + "' " +
				"WHERE ticket_id='" + ticket.getId() + "'"
			);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// update Ticket object
		ticket.setName(newName);
		ticket.setDesc(newDesc);
	}

	/**
	 * Delete a ticket.
	 * @param Ticket toDelete
	 */
	public void deleteTicket(Ticket ticket) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM ticket " +
				"WHERE ticket_id='" + ticket.getId() + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

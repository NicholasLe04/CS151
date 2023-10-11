package entries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ticket {
	
	Connection conn;
	
	private int id;
	private String name;
	private String desc;
	private ArrayList<Comment> comments;
	
	public Ticket(int id, String name, String desc, Connection conn) {
		this.comments = new ArrayList<>();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.conn = conn;
		try {
			// queries for all comments
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM comment " + 
				"WHERE ticket_id='" + name + "'"
			);
			// places everything in ArrayList comments
			while (res.next()) {
				int commentId = res.getInt(1);
				LocalDateTime timestamp = LocalDateTime.parse(res.getString(2));
				String commentBody = res.getString(3);
				comments.add(new Comment(commentId, commentBody, timestamp, conn));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void edit(String name, String desc) {
		// query
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE ticket " +
				"SET ticket_name='" + name + "', desc='" + desc + "' " +
				"WHERE ticket_id='" + this.id + "'"
			);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// modify object
		this.name = name;
		this.desc = desc;
	}
	
	/**
	 * Get id of Ticket.
	 * @return int id of Ticket
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get name of Ticket.
	 * @return String name of Ticket
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get description of Ticket.
	 * @return String description of Ticket
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Get all comments associated with this Ticket
	 * @return ArrayList all comments
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	/**
	 * Create a comment associated with this project.
	 */
	public void createComment(String commentContent) {
		int commentId = 0;
		try {
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT last_insert_rowid() FROM comment");
			statement.executeUpdate(
				"INSERT INTO comment(comment_body, ticket_id) " +
				"VALUES('" + commentContent + "', '" + id + "')"
			);
			commentId = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// add ticket to memory
		comments.add(new Comment(commentId, commentContent, LocalDateTime.now(), conn));
	}
	
	/**
	 * Delete a comment associated with this project.
	 */
	public void deleteComment(Comment toDelete) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM comment " +
				"WHERE comment_id='" + toDelete.getId() + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// remove from memory
		comments.remove(toDelete);
	}
}
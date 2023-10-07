package entries;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Comment {
	
	Connection conn;
	
	private int id;
	private String body;
	
	/**
	 * Create Comment object in memory
	 * @param id
	 * @param body
	 * @param conn
	 */
	public Comment(int id, String body, Connection conn) {
		this.id = id;
		this.body = body;
		this.conn = conn;
	}
	
	/**
	 * Edit this ticket.
	 * @param String body
	 */
	public void edit(String body) {
		// edit comment in db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE comment " +
				"SET comment_body=" + body + " " +
				"WHERE comment_id=" + id
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// modify object
		this.body = body;
	}
	
	/**
	 * Get id of Comment.
	 * @return int id of Comment
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get body of Comment
	 * @return String body of Comment
	 */
	public String getBody() {
		return body;
	}
}
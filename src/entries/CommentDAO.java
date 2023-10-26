package entries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommentDAO {
	
	Connection conn;
	
	/**
	 * Create CommentDAO object using Connection conn.
	 * @param Connection conn
	 */
	public CommentDAO(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Get persistent Comments belonging to ticket.
	 * @param Ticket ticket
	 * @return ArrayList<Comment> comments
	 */
	public ArrayList<Comment> getComments(int ticketId) {
		ArrayList<Comment> comments = new ArrayList<>();
		try {
			// queries for all comments
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM comment " + 
				"WHERE ticket_id='" + ticketId + "'"
			);
			// places everything in ArrayList comments
			while (res.next()) {
				int commentId = res.getInt(1);
				LocalDateTime timestamp = LocalDateTime.parse(res.getString(2));
				String commentBody = res.getString(3);
				comments.add(new Comment(commentId, commentBody, timestamp));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}

	/**
	 * Persist a Comment.
	 * @param Comment toPersist
	 */
	public void createComment(String commentBody, int ticketId) {
		// add comment to db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"INSERT INTO comment(created_at, comment_body, ticket_id) " +
				"VALUES('" + LocalDateTime.now() + "', '" + commentBody + "', '" + ticketId + "')"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Edit a Comment.
	 * @param Comment toEdit
	 * @param String newBody 
	 */
	public void edit(int commentId, String newBody) {
		// update comment in db
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE comment " +
				"SET comment_body='" + newBody + "' " +
				"WHERE comment_id='" + commentId + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a comment.
	 * @param Comment toDelete
	 */
	public void deleteComment(int commentId) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM comment " +
				"WHERE comment_id='" + commentId + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

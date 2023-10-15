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
	public ArrayList<Comment> getComments(Ticket ticket) {
		ArrayList<Comment> comments = new ArrayList<>();
		try {
			// queries for all comments
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery(
				"SELECT * " +
				"FROM comment " + 
				"WHERE ticket_id='" + ticket.getName() + "'"
			);
			// places everything in ArrayList comments
			while (res.next()) {
				int commentId = res.getInt(1);
				LocalDateTime timestamp = LocalDateTime.parse(res.getString(2));
				String commentBody = res.getString(3);
				comments.add(new Comment(commentId, commentBody, timestamp, ticket));
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
	public void createComment(Comment comment) {
		try {
			Statement statement = conn.createStatement();
			// update Comment object id property
			comment.setId(statement.executeQuery("SELECT last_insert_rowid() FROM comment").getInt(1));
			// add comment to db
			statement.executeUpdate(
				"INSERT INTO comment(comment_body, ticket_id) " +
				"VALUES('" + comment.getBody() + "', '" + comment.getTicket().getId() + "')"
			);
			// update Comment object timestamp property
			statement.executeUpdate(
				"SELECT created_at " +
				"FROM comment " +
				"WHERE comment_id=" + comment.getId()
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
	public void edit(Comment comment, String newBody) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"UPDATE comment " +
				"SET comment_body='" + newBody + "' " +
				"WHERE comment_id='" + comment.getId() + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// update Comment object
		comment.setBody(newBody);
	}
	
	/**
	 * Delete a comment.
	 * @param Comment toDelete
	 */
	public void deleteComment(Comment comment) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
				"DELETE " +
				"FROM comment " +
				"WHERE comment_id='" + comment.getId() + "'"
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

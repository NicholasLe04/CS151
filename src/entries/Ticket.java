package entries;

import java.sql.Connection;
import java.util.ArrayList;

public class Ticket {
	
	Connection conn;
	
	String name;
	String desc;
	ArrayList<Comment> comments;
	
	public Ticket(String name, String desc, Connection conn) {
		this.comments = new ArrayList<>();
		this.name = name;
		this.desc = desc;
		this.conn = conn;
		// TODO: SQL SELECT all comments associated with this ticket, populate comments ArrayList, pass conn to each of them
	}
	
	public void edit(String name, String desc) {
		this.name = name;
		this.desc = desc;
		// TODO: SQL UPDATE 
	}
	
	
	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	/**
	 * Create a comment associated with this project.
	 */
	public void createComment(String name, String content) {
		// TODO: SQL INSERT
		// TODO: create new comment object, passing conn
	}
	
	/**
	 * Delete a comment associated with this project.
	 */
	public void deleteComment(String name) {
		// TODO: remove comment from database and ArrayList
	}
}
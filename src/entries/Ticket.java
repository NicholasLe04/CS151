package entries;

import java.sql.Connection;
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
		// TODO: SQL SELECT all comments associated with this ticket, populate comments ArrayList, pass conn to each of them
	}
	
	public void edit(String name, String desc) {
		this.name = name;
		this.desc = desc;
		// TODO: SQL UPDATE 
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
package entries;

import java.sql.Connection;

public class Comment {
	
	Connection conn;
	
	private int id;
	private String body;
	
	public Comment(int id, String body, Connection conn) {
		this.id = id;
		this.body = body;
		this.conn = conn;
	}
	
	public void edit(String body) {
		this.body = body;
		// TODO: SQL UPDATE 
	}
	
	/**
	 * Get body of Comment
	 * @return String body of Comment
	 */
	public String getBody() {
		return body;
	}
}
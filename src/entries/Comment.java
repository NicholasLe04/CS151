package entries;

import java.sql.Connection;

public class Comment {
	
	Connection conn;
	
	String name;
	String body;
	
	public Comment(String name, String body, Connection conn) {
		this.name = name;
		this.body = body;
		this.conn = conn;
	}
	
	public void edit(String name, String body) {
		this.name = name;
		this.body = body;
		// TODO: SQL UPDATE 
	}
}
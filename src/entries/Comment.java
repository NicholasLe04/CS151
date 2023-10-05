package entries;

import java.sql.Connection;

public class Comment {
	
	Connection conn;
	
	String name;
	String content;
	
	public Comment(String name, String content, Connection conn) {
		this.name = name;
		this.content = content;
		this.conn = conn;
	}
	
	public void edit(String name, String desc) {
		this.name = name;
		this.content = desc;
		// TODO: SQL UPDATE 
	}
}
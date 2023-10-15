package entries;

import java.time.LocalDateTime;

public class Comment {
		
	private int id;
	private String body;
	private LocalDateTime timestamp;
	private Ticket ticket;

	public Comment(int id, String body, LocalDateTime timestamp, Ticket ticket) {
		this.id = id;
		this.body = body;
		this.timestamp = timestamp;
		this.ticket = ticket;
	}
	
	public Comment(String body, Ticket ticket) {
		this.body = body;
		this.ticket = ticket;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
}
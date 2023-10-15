package entries;

import java.time.LocalDateTime;

public class Comment {
		
	private int id;
	private String body;
	private LocalDateTime timestamp;
	private Ticket ticket;

	/**
	 * Create Comment already in db.
	 * @param int id
	 * @param String body
	 * @param Local DateTime timestamp
	 * @param Ticket parentTicket
	 */
	protected Comment(int id, String body, LocalDateTime timestamp, Ticket ticket) {
		this.id = id;
		this.body = body;
		this.timestamp = timestamp;
		this.ticket = ticket;
	}
	
	/**
	 * Create Comment not in db. Must call createComment(this) before using.
	 * @param body
	 * @param ticket
	 */
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
	
	/**
	 * Get parent Ticket.
	 * @return Ticket parentTicket
	 */
	public Ticket getTicket() {
		return ticket;
	}
}
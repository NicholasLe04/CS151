package entries;

public class Ticket {	
	
	private int id;
	private String name;
	private String desc;
	
	/**
	 * Create Ticket already in db.
	 * @param int id
	 * @param String name
	 * @param String desc
	 * @param Project parentProject
	 */
	protected Ticket(int id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	
	/**
	 * Create Ticket not in db. Must call createTicket(this) before using.
	 * @param name
	 * @param desc
	 * @param Project parentProject
	 */
	public Ticket(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
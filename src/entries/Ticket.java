package entries;

public class Ticket {	
	
	private int id;
	private String title;
	private String desc;
	
	/**
	 * Create Ticket already in db.
	 * @param int id
	 * @param String name
	 * @param String desc
	 * @param Project parentProject
	 */
	protected Ticket(int id, String title, String desc) {
		this.id = id;
		this.title = title;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String name) {
		this.title = name;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
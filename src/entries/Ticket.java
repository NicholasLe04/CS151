package entries;

public class Ticket {	
	
	private int id;
	private String name;
	private String desc;
	private Project project;
	
	public Ticket(int id, String name, String desc, Project project) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.project = project;
	}
	
	public Ticket(String name, String desc, Project project) {
		this.name = name;
		this.desc = desc;
		this.project = project;
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
	
	public Project getProject() {
		return project;
	}
}
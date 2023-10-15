package entries;

import java.time.LocalDate;

public class Project {
	
	private String name;
	private LocalDate date;
	private String desc;

	/**
	 * Create project.
	 * @param String name
	 * @param LocalDate date
	 * @param String desc
	 */
	public Project(String name, LocalDate date, String desc) {
		this.name = name;
		this.date = date;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
package entries;

import java.time.LocalDate;

public class Project {
	
	private String title;
	private LocalDate date;
	private String desc;

	/**
	 * Create project.
	 * @param String title
	 * @param LocalDate date
	 * @param String desc
	 */
	protected Project(String title, LocalDate date, String desc) {
		this.title = title;
		this.date = date;
		this.desc = desc;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
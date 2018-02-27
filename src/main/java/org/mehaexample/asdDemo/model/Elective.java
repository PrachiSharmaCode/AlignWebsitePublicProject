package org.mehaexample.asdDemo.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Elective {
	private int id;
	// make neuId from string to student 
	private String neuId;
	private String title;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNeuId() {
		return neuId;
	}

	public void setNeuId(String neuId) {
		this.neuId = neuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

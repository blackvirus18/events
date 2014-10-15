package com.events.model;

public class Event {
	private String id;
	private String name;
	private String startDate;
	private String imageSrc;
	private String description;
	private String link;
	private String validTill;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getValidTill() {
		return validTill;
	}
	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", startDate="
				+ startDate + ", imageSrc=" + imageSrc + ", description="
				+ description + ", link=" + link + ", validTill=" + validTill
				+ "]";
	}
	
}

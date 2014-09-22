package com.events.model;

import java.util.List;

public class Club {
	private String id;
	private String name;
	private String coverPhoto;
	private String website;
	private String facebookPage;
	private List<Event> events;
	private List<Category> categories;
	private Location location;
	private String contactNumber;
	private String description;
	private String profilePic;	
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
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFacebookPage() {
		return facebookPage;
	}
	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	@Override
	public String toString() {
		return "Club [id=" + id + ", name=" + name + ", coverPhoto="
				+ coverPhoto + ", website=" + website + ", facebookPage="
				+ facebookPage + ", events=" + events + ", categories="
				+ categories + ", location=" + location + ", contactNumber="
				+ contactNumber + ", description=" + description
				+ ", profilePic=" + profilePic + "]";
	}
	
}

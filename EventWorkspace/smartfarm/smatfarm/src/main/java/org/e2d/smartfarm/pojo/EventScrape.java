package org.e2d.smartfarm.pojo;

public class EventScrape {
	private String eventDate; // http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html
	private String title;
	private String location;
	private String description;
	private String eventTime; // http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String details) {
		this.description = details;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

}

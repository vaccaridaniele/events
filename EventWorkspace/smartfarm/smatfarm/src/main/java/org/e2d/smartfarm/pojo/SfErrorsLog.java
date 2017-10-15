package org.e2d.smartfarm.pojo;
// Generated 8-ago-2016 10.54.53 by Hibernate Tools 4.3.4.Final

import java.util.Date;

/**
 * SfErrorsLog generated by hbm2java
 */
public class SfErrorsLog implements java.io.Serializable {

	private Long id;
	private String json;
	private String description;
	private String email;
	private Date date;
	private Short httpStatus;
	private Integer notified;

	public SfErrorsLog() {
	}

	public SfErrorsLog(Integer notified) {
		this.notified = notified;
	}

	public SfErrorsLog(String json, String description, String email, Date date, Short httpStatus, Integer notified) {
		this.json = json;
		this.description = description;
		this.email = email;
		this.date = date;
		this.httpStatus = httpStatus;
		this.notified = notified;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJson() {
		return this.json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Short getHttpStatus() {
		return this.httpStatus;
	}

	public void setHttpStatus(Short httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Integer getNotified() {
		return this.notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

}
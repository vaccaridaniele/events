package org.e2d.smartfarm.pojo;
// Generated 8-ago-2016 10.54.53 by Hibernate Tools 4.3.4.Final

import java.util.Date;

/**
 * SfUsers generated by hbm2java
 */
public class SfUsers implements java.io.Serializable {

	private Long id;
	private String name;
	private String surname;
	private String LName;
	private String LPsw;
	private String remarks;
	private String email;
	private String cuser;
	private Date cdate;
	private String muser;
	private Date mdate;

	public SfUsers() {
	}

	public SfUsers(String LName, String LPsw, String cuser, Date cdate) {
		this.LName = LName;
		this.LPsw = LPsw;
		this.cuser = cuser;
		this.cdate = cdate;
	}

	public SfUsers(String name, String surname, String LName, String LPsw, String remarks, String email, String cuser,
			Date cdate, String muser, Date mdate) {
		this.name = name;
		this.surname = surname;
		this.LName = LName;
		this.LPsw = LPsw;
		this.remarks = remarks;
		this.email = email;
		this.cuser = cuser;
		this.cdate = cdate;
		this.muser = muser;
		this.mdate = mdate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLName() {
		return this.LName;
	}

	public void setLName(String LName) {
		this.LName = LName;
	}

	public String getLPsw() {
		return this.LPsw;
	}

	public void setLPsw(String LPsw) {
		this.LPsw = LPsw;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getMuser() {
		return this.muser;
	}

	public void setMuser(String muser) {
		this.muser = muser;
	}

	public Date getMdate() {
		return this.mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

}

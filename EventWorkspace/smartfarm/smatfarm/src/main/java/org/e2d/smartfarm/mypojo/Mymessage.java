package org.e2d.smartfarm.mypojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mymessage {

	private int			code;
	private String	message;

	public Mymessage() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int errorCode) {
		this.code = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String documentation) {
		this.message = documentation;
	}

}

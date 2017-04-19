package model;

public class MailVo {
	String mail = null;
	/**
	 * 岗位
	 */
	String position = null;
	public MailVo(String mail, String position) {
		super();
		this.position = position;
		this.mail = mail;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
}

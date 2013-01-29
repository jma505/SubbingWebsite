package org.jmanderson.subbing.dataobjects;

import org.jmanderson.subbing.forms.UserForm;
import org.jmanderson.subbing.hibernate.Users;

/**
 * This is the dataobject for Users for use in the Administration utility.  We don't need
 * as much information there as in the standard User object ...
 */
public class SmallUser {

	private String username;
	private String nickname;
	private String fullname;
	private String oldusername;
	private String lastlogin;
	private int status;
	private String comments;
	
	public SmallUser() {
	}
	
	public SmallUser(UserForm form) {
		this.username = form.getUsername();
		this.nickname = form.getNickname();
		this.fullname = form.getFullname();
		this.oldusername = form.getOldusername();
		lastlogin = "unknown";
	}
	
	public boolean isUsernameChanged() {
		if (username.equals(oldusername)) {
			return false;
		}
		return true;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public String getOldusername() {
		return oldusername;
	}
	
	public String getLastlogin() {
		return lastlogin;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getComments() {
		return comments;
	}
	
	public String getStatusDisplay() {
		switch(status) {
		case Users.ACTIVE:
			return "Active";
		case Users.INACTIVE:
			return "Inactive";
		case Users.LOCKED:
			return "Locked";
		default:
			return "UNKNOWN";
		}
	}
	
	public String getXML() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<user><username>");
		sb.append(username).append("</username>");
		sb.append("<nickname>").append(nickname).append("</nickname>");
		sb.append("<fullname>").append(fullname).append("</fullname>");
		sb.append("<lastlogin>").append(lastlogin).append("</lastlogin>");
		sb.append("<status>").append(getStatusDisplay()).append("</status>");
		sb.append("<comments>").append(comments).append("</comments>");
		sb.append("</user>");
		
		return sb.toString();
	}
	
}

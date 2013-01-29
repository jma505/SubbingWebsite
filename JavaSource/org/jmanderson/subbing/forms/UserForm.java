package org.jmanderson.subbing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for an Email address.
 */
public class UserForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2004941215138966278L;
	private int id;
	private String username;
	private String nickname;
	private String fullname;
	private String oldusername;
	
	public UserForm() {
		id = 0;
		oldusername = "";
	}

	public void reset() {
		username = "";
		nickname = "";
		fullname = "";
		id = 0;
		oldusername = "";
	}

	public void setUsername(String username) {
		if (oldusername.length() == 0) {
			oldusername = username;
		}
		this.username = username;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setOldusername(String oldusername) {
		this.oldusername = oldusername;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setExistingUser() {
		id = 1;	
	}
	
	public boolean isNewUser() {
		return (id == 0 ? true : false);
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
	
	public int getId() {
		return id;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (username == null || username.equals("")) {
			errors.add("username", new ActionError("error.username.required"));
		}
		if (nickname == null || nickname.equals("")) {
			errors.add("shortname", new ActionError("error.shortname.required"));
		}
		
		return errors;
		
	}

}

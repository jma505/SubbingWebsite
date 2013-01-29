package org.jmanderson.subbing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for an Email address.
 */
public class EmailForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634939645964046250L;
	private String email;
	private boolean preferred;
	private String id;

	public EmailForm() {
	}

	public void reset() {
		email = "";
		preferred = false;
		id = "0";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public boolean getPreferred() {
		return preferred;
	}
	
	public String getId() {
		return id;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (email == null || email.length() < 1) {
			errors.add("email", new ActionError("error.email.required"));
		}
		return errors;
		
	}

}

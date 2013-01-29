package org.jmanderson.subbing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.jmanderson.subbing.hibernate.Users;

/**
 * Form bean for a password change.
 */
public class PasswordForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6579714426832113096L;
	private String oldPassword;
	private String newPassword1;
	private String newPassword2;

	public PasswordForm() {
	}

	public void reset() {
		oldPassword = "";
		newPassword1 = "";
		newPassword2 = "";
	}

	public void setOldPassword(String password) {
		oldPassword = password;
	}
	
	public void setNewPassword1(String password) {
		newPassword1 = password;
	}
	
	public void setNewPassword2(String password) {
		newPassword2 = password;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public String getNewPassword1() {
		return newPassword1;
	}
	
	public String getNewPassword2() {
		return newPassword2;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		
		ActionErrors errors = super.validate(mapping, request);

		if (!newPassword1.equals(newPassword2)) {
			errors.add("newpwd", new ActionError("error.newpwd.mismatch"));
		}
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null || !user.passwordMatches(oldPassword)) {
			errors.add("oldpwd", new ActionError("error.oldpwd.mismatch"));
		}
		
		return errors;
		
	}

}

package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataUpdater;
import org.jmanderson.subbing.forms.UserForm;
import org.jmanderson.subbing.hibernate.Users;

public class AddUserAction extends org.apache.struts.action.Action {

	// Global Forwards
	public static final String GLOBAL_FORWARD_home = "home";
	public static final String GLOBAL_FORWARD_login = "login";
	public static final String GLOBAL_FORWARD_logout = "logout";
	public static final String GLOBAL_FORWARD_organist_info = "organist_info";

	// Local Forwards
	public static final String FORWARD_success = "success";
	static final String FORWARD_errors = "errors";

	public AddUserAction() {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}

		UserForm userForm = (UserForm) form;
		ActionErrors errors = userForm.validate(mapping, request);
		if (errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(FORWARD_errors);
		}
		
		if (userForm.isNewUser() ) {
			DataUpdater.addUser(userForm);
		} else {
			DataUpdater.updateUser(userForm);
		}
		userForm.reset();
		
		return mapping.findForward(FORWARD_success);

	}

}
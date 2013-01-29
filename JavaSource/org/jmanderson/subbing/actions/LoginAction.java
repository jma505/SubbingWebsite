package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.DataUpdater;
import org.jmanderson.subbing.ValidateUser;
import org.jmanderson.subbing.hibernate.Users;

/*
 * Processes a login.
 * 
 * <br>11/11/03 Initial development.
 */
public class LoginAction extends org.apache.struts.action.Action {

	// Global Forwards
	public static final String GLOBAL_FORWARD_login = "login";

	// Local Forwards
	public static final String FORWARD_success = "success";

	public LoginAction() {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (isCancelled(request)) {
			return mapping.findForward(FORWARD_success);
		}

		ActionErrors errors = new ActionErrors();

		try {
			validateUser(errors, form, request);
		} catch (Exception e) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.app"));
		}

		// Forward any errors back to the original form
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return (new ActionForward(mapping.getInput()));
		}

		// Update the lastlogin field in the database
		DataUpdater.updateLastLogin((String) PropertyUtils
				.getSimpleProperty(form, "username"));

		return mapping.findForward(FORWARD_success);
	}

	/**
	 * Retrieves the DataSource and validates the username and password.
	 * 
	 * @param errors
	 *            ActionErrors object
	 * @param form
	 *            ActionForm (DynaActionForm)
	 * @param session
	 *            HttpSessions to store User
	 * @return DataSource for use in other ways
	 * @throws Exception
	 */
	private void validateUser(ActionErrors errors, ActionForm form,
			HttpServletRequest request) throws Exception {
		Users user = null;

		String username;
		String password;
		username = (String) PropertyUtils.getSimpleProperty(form, "username");
		password = (String) PropertyUtils.getSimpleProperty(form, "password");
		
		user = ValidateUser.validate(username);
		if (user == null || !user.passwordMatches(password)) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.password.mismatch"));
			return;
		}

		// Put the UserBean into the session scope for future use
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		servlet.log("User '" + user.getUsername() + "' logged on in session "
				+ session.getId());
		session.setAttribute("organist", DataPreparer.getOrganist(user
				.getUsername()));
		return;
	}

}
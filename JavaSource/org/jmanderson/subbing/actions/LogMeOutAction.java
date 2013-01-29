package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.hibernate.Users;

/*
 * 
 * Logs off a user (if a user is logged in) and forwards to the
 * Home display.
 * 
 * <br>11/11/03 Initial development.
 */
public class LogMeOutAction extends org.apache.struts.action.Action {

	// Global Forwards
	public static final String GLOBAL_FORWARD_login = "login";
	public static final String GLOBAL_FORWARD_home = "home";
	public static final String GLOBAL_FORWARD_logout = "logout";

	// Local Forwards
	public static final String FORWARD_success = "success";

	public LogMeOutAction() {
	}

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user != null) {
			servlet.log("User " + user.getUsername() + " logged out");
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("organist");
		}
		return mapping.findForward(FORWARD_success);
	}

}
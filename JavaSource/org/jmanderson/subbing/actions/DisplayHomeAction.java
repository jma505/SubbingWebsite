package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.hibernate.Users;

/*
 * Processes a request to display the home page.  If the user is logged in,
 * the "my schedule" page displays.  Otherwise, the normal schedules page
 * displays.
 * 
 * <br>11/11/03 Initial development
 */
public class DisplayHomeAction extends org.apache.struts.action.Action {

	// Global Forwards
	// Local Forwards
	public static final String FORWARD_success = "success";
//	public static final String FORWARD_loggedIn = "loggedIn";

	public DisplayHomeAction() {
	}

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

//		Users user = (Users) request.getSession().getAttribute("user");
//		if (user != null) {
//			return mapping.findForward(FORWARD_loggedIn);
//		}
//		String availability = DataPreparer.getAvailabilityAsXML();
//		request.setAttribute("availability", availability);
		return mapping.findForward(FORWARD_success);
	}

}
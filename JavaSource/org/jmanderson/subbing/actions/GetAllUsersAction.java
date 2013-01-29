package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.forms.UserForm;
import org.jmanderson.subbing.hibernate.Users;

public class GetAllUsersAction extends org.apache.struts.action.Action {

	// Global Forwards
	public static final String GLOBAL_FORWARD_home = "home";
	public static final String GLOBAL_FORWARD_login = "login";
	public static final String GLOBAL_FORWARD_logout = "logout";
	public static final String GLOBAL_FORWARD_organist_info = "organist_info";
	public static final String GLOBAL_FORWARD_updateBasicInfo = "updateBasicInfo";

	// Local Forwards
	public static final String FORWARD_success = "success";

	public GetAllUsersAction() {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}

		String users = DataPreparer.getAllUsersAsXML();
		request.setAttribute("users", users);
		
		UserForm uform = (UserForm) form;
		setFormFromRequest(uform, request);
		
		return mapping.findForward(FORWARD_success);

	}
	
	private void setFormFromRequest(UserForm form, HttpServletRequest request) {
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String fullname = request.getParameter("fullname");
		
		form.reset();
		if (username != null && username.length() > 0) {
			form.setUsername(username);
			form.setNickname(nickname);
			form.setFullname(fullname);
			form.setExistingUser();
		}
	}

}
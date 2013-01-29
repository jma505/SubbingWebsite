package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.forms.LocationForm;
import org.jmanderson.subbing.hibernate.Users;

/**
 * 
 * 7/29/04 - Updated to support change from int to String for LocationForm attribute "id"
 */
public class GetAllLocationsAction extends org.apache.struts.action.Action {

	// Global Forwards
	public static final String GLOBAL_FORWARD_home = "home";
	public static final String GLOBAL_FORWARD_login = "login";
	public static final String GLOBAL_FORWARD_logout = "logout";
	public static final String GLOBAL_FORWARD_organist_info = "organist_info";
	public static final String GLOBAL_FORWARD_updateBasicInfo = "updateBasicInfo";

	// Local Forwards
	public static final String FORWARD_success = "success";

	public GetAllLocationsAction() {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}

		LocationForm locForm = (LocationForm) form;
		setFormFromRequest(locForm, request);
		String locations = DataPreparer.getLocationsAsXML(user.getUsername());
		request.setAttribute("locations", locations);
		return mapping.findForward(FORWARD_success);
	}

	private void setFormFromRequest(LocationForm form, HttpServletRequest request) {
		String na = request.getParameter("na");
		String ad = request.getParameter("ad");
		String ci = request.getParameter("ci");
		String st = request.getParameter("st");
		String zi = request.getParameter("zi");
		String no = request.getParameter("no");
		String id = request.getParameter("id");
		String nu = request.getParameter("nu");

		form.reset();
		if (na != null && na.length() > 0) {
			form.setId(id);
			form.setName(na);
			form.setAddress(ad);
			form.setCity(ci);
			form.setState(st);
			form.setZip(zi);
			form.setNotes(no);
			form.setUsernotes(nu);
		}
	}

}
package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.forms.EmailForm;
import org.jmanderson.subbing.hibernate.Organists;
import org.jmanderson.subbing.hibernate.Users;

public class GetAllEmailsAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 

    // Local Forwards
    public static final String FORWARD_success = "success"; 

    
    public GetAllEmailsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}
        
		EmailForm emailForm = (EmailForm) form;
		setFormFromRequest(emailForm, request);
		Organists organist = (Organists) request.getSession().getAttribute("organist");
		request.setAttribute("organist", organist.getXml());
		return mapping.findForward(FORWARD_success);
    }

	private void setFormFromRequest(EmailForm form, HttpServletRequest request) {
		String em = request.getParameter("em");
		String pr = request.getParameter("pr");
		String id = request.getParameter("id");
		
		form.reset();
		if (em != null && em.length() > 0) {
			form.setId(id);
			form.setEmail(em);
			form.setPreferred(new Boolean(pr).booleanValue());
		}
	}

}
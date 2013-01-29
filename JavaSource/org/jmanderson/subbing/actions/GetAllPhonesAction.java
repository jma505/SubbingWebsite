package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.forms.PhoneForm;
import org.jmanderson.subbing.hibernate.Organists;
import org.jmanderson.subbing.hibernate.Users;

public class GetAllPhonesAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 

    // Local Forwards
    public static final String FORWARD_success = "success"; 

    
    public GetAllPhonesAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
        	return mapping.findForward(GLOBAL_FORWARD_home);
        }
        
        PhoneForm phoneForm = (PhoneForm) form;
        setFormFromRequest(phoneForm, request);
		Organists organist = (Organists) request.getSession().getAttribute("organist");
		request.setAttribute("organist", organist.getXml());
		return mapping.findForward(FORWARD_success);
    }

	private void setFormFromRequest(PhoneForm form, HttpServletRequest request) {
		String ac = request.getParameter("ac");
		String ph = request.getParameter("ph");
		String ex = request.getParameter("ex");
		String ty = request.getParameter("ty");
		String id = request.getParameter("id");
		
		form.reset();
		if (ph != null && ph.length() > 0) {
			form.setId(id);
			form.setPhone(ph);
			if (ac != null) {
				form.setAreacode(ac);
			}
			if (ex != null) {
				form.setExtension(ex);
			}
			if (ty != null) {
				form.setType(ty);
			}
		}
	}
}
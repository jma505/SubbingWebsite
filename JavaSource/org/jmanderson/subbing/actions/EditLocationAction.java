package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.forms.LocationForm;
import org.jmanderson.subbing.hibernate.Location;
import org.jmanderson.subbing.hibernate.Users;

public class EditLocationAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 
    public static final String GLOBAL_FORWARD_updateBasicInfo = "updateBasicInfo"; 

    // Local Forwards
    public static final String FORWARD_success = "success"; 
    static final String FORWARD_errors = "errors";
    
    public EditLocationAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}

		LocationForm locForm = (LocationForm) form;
		ActionErrors errors = locForm.validate(mapping, request);
		if (errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(FORWARD_errors);
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
       Location location = DataPreparer.getLocation(id, user.getUsername());
       location.setLocationForm(locForm);
       
       return mapping.findForward(FORWARD_success);
    }

}
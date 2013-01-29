package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataUpdater;
import org.jmanderson.subbing.hibernate.Users;

public class DeleteDateAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 
    public static final String GLOBAL_FORWARD_updateBasicInfo = "updateBasicInfo"; 

    // Local Forwards
    public static final String FORWARD_success = "success"; 

    
    public DeleteDateAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}

        String dateId = (String) request.getParameter("id");
        try {
        	int id = Integer.parseInt(dateId);
        	DataUpdater.deleteSchedule(id);
        }
        catch (NumberFormatException e) {
        	// ignore ... just go back to the original screen
        }
        return mapping.findForward(FORWARD_success);
    }

}
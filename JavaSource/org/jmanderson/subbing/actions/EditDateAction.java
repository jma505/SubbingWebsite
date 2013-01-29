package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.hibernate.Users;

public class EditDateAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 
    public static final String GLOBAL_FORWARD_updateBasicInfo = "updateBasicInfo"; 

    // Local Forwards
    public static final String FORWARD_success = "success"; 

    
    public EditDateAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}

    	String monthYear = (String) request.getParameter("month");
    	int day = Integer.parseInt((String) request.getParameter("day"));
    	String month = monthYear.substring(0, monthYear.indexOf(" "));
    	int year = Integer.parseInt(monthYear.substring(monthYear.length()-4));
    	System.out.println("YEAR = " + year);
    	System.out.println("MONTH = " + month);
    	System.out.println("DAY = " + day);
        return mapping.findForward(FORWARD_success);
    }

}
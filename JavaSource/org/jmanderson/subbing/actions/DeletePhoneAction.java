package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.DataUpdater;
import org.jmanderson.subbing.hibernate.Organists;
import org.jmanderson.subbing.hibernate.Users;

public class DeletePhoneAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 

    // Local Forwards
    public static final String FORWARD_success = "success"; 

    
    public DeletePhoneAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
        	return mapping.findForward(GLOBAL_FORWARD_home);
        }
        
        String id = request.getParameter("id");
        
        DataUpdater.deletePhone(id);
        
        Organists organist = DataPreparer.getOrganist(user.getUsername());
        request.getSession().setAttribute("organist", organist);
        
        return mapping.findForward(FORWARD_success);
    }

}
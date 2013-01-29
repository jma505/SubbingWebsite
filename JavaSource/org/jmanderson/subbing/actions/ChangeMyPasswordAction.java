package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataUpdater;
import org.jmanderson.subbing.forms.PasswordForm;
import org.jmanderson.subbing.hibernate.Users;

public class ChangeMyPasswordAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 
    public static final String GLOBAL_FORWARD_changePassword = "changePassword"; 

    // Local Forwards

    
    public ChangeMyPasswordAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (isCancelled(request)) {
    		return mapping.findForward(GLOBAL_FORWARD_home);
    	}
    	
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
        	return mapping.findForward(GLOBAL_FORWARD_home);
        }
        
        DataUpdater.updatePassword(user.getUsername(), (PasswordForm) form);
        user.setPassword(((PasswordForm)form).getNewPassword1());
        
        return mapping.findForward(GLOBAL_FORWARD_home);
    }

}
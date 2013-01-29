package org.jmanderson.subbing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.hibernate.Users;

public class GetMyPiecesAction extends org.apache.struts.action.Action {
    
    // Global Forwards
    public static final String GLOBAL_FORWARD_home = "home"; 
    public static final String GLOBAL_FORWARD_login = "login"; 
    public static final String GLOBAL_FORWARD_logout = "logout"; 
    public static final String GLOBAL_FORWARD_organist_info = "organist_info"; 

    // Local Forwards
    public static final String FORWARD_success = "success"; 

    
    public GetMyPiecesAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
        	return mapping.findForward(GLOBAL_FORWARD_home);
        }
        
        String pieces = DataPreparer.getMyPiecesAsXml(user.getUsername());
        
        request.setAttribute("pieces", pieces);
        
        return mapping.findForward(FORWARD_success);
    }

}
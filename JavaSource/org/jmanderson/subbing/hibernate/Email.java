/*
 * Created Wed Sep 07 17:25:39 EDT 2005 by MyEclipse Hibernate Tool.
 */ 
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;

import org.jmanderson.subbing.forms.EmailForm;

/**
 * A class that represents a row in the 'email' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Email
    extends AbstractEmail
    implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1546215155325537766L;

	/**
     * Simple constructor of Email instances.
     */
    public Email()
    {
    }

    /* Add customized code below */
    
    public String toString() {
    	return getEmail() + (getPreferred().booleanValue() ? " [Preferred]" : "");
    }
    
    public void loadFromEmailForm(EmailForm form, Organists organist) {
    	if (!form.getId().equals("0")) {
    		setId(new Integer(form.getId()));
    	}
    	setEmail(form.getEmail());
    	setPreferred(new Boolean(form.getPreferred()));
    	setOrganists(organist);
    }

}

/*
 * Created Wed Sep 07 17:36:59 EDT 2005 by MyEclipse Hibernate Tool.
 */ 
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;
import org.jmanderson.subbing.EncodeDecode;
import org.jmanderson.subbing.forms.PhoneForm;

/**
 * A class that represents a row in the 'phone' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Phone
    extends AbstractPhone
    implements Serializable, Lifecycle
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5146281942745888676L;
	private static final Integer ZERO = new Integer(0);
	private static final String NO_PHONE = "000-0000";

	/**
     * Simple constructor of Phone instances.
     */
    public Phone()
    {
    }

    /* Add customized code below */
    
    public void loadFromPhoneForm(PhoneForm form, Organists organist) {
    	if (!form.getId().equals("0")) {
    		setId(new Integer(form.getId()));
    	}
    	if (form.getAreacode().length() > 0) {
    		setAreacode(new Integer(form.getAreacode()));
    	}
    	else {
    		setAreacode(ZERO);
    	}
    	if (form.getPhone().length() > 0) {
    		setPhone(new Integer(form.getPhone()));
    	}
    	else {
    		setPhone(ZERO);
    	}
    	if (form.getExtension().length() > 0) {
    		setExtension(new Integer(form.getExtension()));
    	}
    	else {
    		setExtension(ZERO);
    	}
    	setType(form.getType());
    	setOrganists(organist);
    }
    
    public String getType() {
    	String type = super.getType();
    	if (type == null) {
    		type = "";
    	}
    	return type;
    }
    
    public String getPhoneAsString() {
    	String p = getPhone().toString();

		return p.substring(0, 3) + "-" + p.substring(3, 7);
    }

    public String toString() {
		StringBuffer sb = new StringBuffer();

		int areaCode = getAreacode().intValue();
		if (areaCode > 0) {
			sb.append("(").append(areaCode).append(") ");
		}
		
		sb.append(getPhoneAsString());
		
		int extension = getExtension().intValue();
		if (extension > 0) {
			sb.append(" x").append(extension);
		}
		
		String type = this.getType();
		if (type != null && type.length() > 0) {
			sb.append(" [").append(type).append("]");
		}
		
		return sb.toString();
	}

	public boolean onDelete(Session arg0) throws CallbackException {
		
		return Lifecycle.NO_VETO;
	}

	public void onLoad(Session arg0, Serializable arg1) {
		
		setType(EncodeDecode.decode(getType()));
	}

	public boolean onSave(Session arg0) throws CallbackException {
		
		setType(EncodeDecode.encode(getType()));
		
		return Lifecycle.NO_VETO;
	}

	public boolean onUpdate(Session arg0) throws CallbackException {
		
		return onSave(arg0);
	}
}

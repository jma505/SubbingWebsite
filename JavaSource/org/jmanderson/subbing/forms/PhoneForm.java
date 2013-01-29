package org.jmanderson.subbing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for an Email address.
 */
public class PhoneForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1748164765683099747L;
	private String areacode;
	private String phone;
	private String extension;
	private String type;
	private String id;

	public PhoneForm() {
		id = "0";
	}

	public void reset() {
		areacode = "";
		phone = "";
		extension = "";
		type = "";
		id = "0";
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public String getAreacode() {
		return areacode;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public String getType() {
		return type;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (areacode != null && areacode.length() > 0) {
			if (areacode.length() != 3) {
				errors.add("areacode", new ActionError("error.areacode.length"));
			}
			else {
				try {
					Integer.parseInt(areacode);
				}
				catch (NumberFormatException e) {
					errors.add("areacode", new ActionError("error.areacode.type"));
				}
			}
		}
		
		if (phone == null || phone.length() != 7) {
			errors.add("phone", new ActionError("error.phone.length"));
		}
		else {
			try {
				int i = Integer.parseInt(phone);
				if (i < 1000000 || i > 9999999) {
					errors.add("phone", new ActionError("error.phone.length"));
				}
			}
			catch (NumberFormatException e) {
				errors.add("phone", new ActionError("error.phone.length"));
			}
		}
		
		if (extension != null && extension.length() > 0) {
			try {
				Integer.parseInt(extension);
			}
			catch (NumberFormatException e) {
				errors.add("extension", new ActionError("error.extension.type"));
			}
		}

		return errors;
		
	}

}

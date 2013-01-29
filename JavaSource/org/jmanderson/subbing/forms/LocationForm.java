package org.jmanderson.subbing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form for adding or editing a Location.
 * 
 * 7/29/04 - Changed id attribute from int to String to correct errors in the HTML form.
 */
public class LocationForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -413926022970693114L;
	private String id;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String notes;
	private String usernotes;

	public LocationForm() {
		id = "0";
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	public void setAddress(String address) {
		if (address != null) {
			this.address = address;
		}
	}

	public void setCity(String city) {
		if (city != null) {
			this.city = city;
		}
	}

	public void setState(String state) {
		if (state != null) {
			this.state = state.toUpperCase();
		}
	}

	public void setZip(String zip) {
		if (zip != null) {
			this.zip = zip;
		}
	}
	
	public void setNotes(String notes) {
		if (notes != null) {
			this.notes = notes;
		}
	}
	
	public void setUsernotes(String usernotes) {
		if (usernotes != null) {
			this.usernotes = usernotes;
		}
	}

	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getNotes() {
		return notes;
	}
	public String getUsernotes() {
		return usernotes;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (name == null || name.length() < 1) {
			errors.add("name", new ActionError("error.name.required"));
		}

		if (city == null || city.length() < 1) {
			errors.add("city", new ActionError("error.city.required"));
		}

		if (state == null || state.length() < 1) {
			errors.add("state", new ActionError("error.state.required"));
		}

		if (state.length() != 2) {
			errors.add("state", new ActionError("error.state.badstate"));
		}

		if (zip.length() > 0) {
			if (zip.length() != 5) {
				errors.add("zip", new ActionError("error.zip.badlength"));
			}
			else {
				try {
					Integer.parseInt(zip);
				}
				catch (NumberFormatException e) {
					errors.add("zip", new ActionError("error.zip.notnumber"));
				}
			}
		}
		
		return errors;
	}

	public void reset() {
		id = "0";
		name = "";
		address = "";
		city = "";
		state = "MA";
		zip = "";
		notes = "";
		usernotes = "";
	}
}

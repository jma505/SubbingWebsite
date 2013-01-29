package org.jmanderson.subbing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.hibernate.Organists;

/**
 * Defines the form for an organist to update personal information.
 * 
 * <br>11/14/03 Initial development.
 */
public class OrganistForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8868694058391323818L;
	private String username;
	private String nickname;
	private String fullname;
	private String details;
	private String travel;
	private boolean available;
	private boolean availableSaturday;
	private boolean availableSunday;
	private boolean showincal;
	private String weddingsFunerals;
	
	public OrganistForm() {
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public void setTravel(String travel) {
		this.travel = travel;
	}
	
	public void setWeddingsFunerals(String weddingsFunerals) {
		this.weddingsFunerals = weddingsFunerals;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public void setAvailableSaturday(boolean available) {
		availableSaturday = available;
	}
	
	public void setAvailableSunday(boolean available) {
		availableSunday = available;
	}
	
	public void setShowincal(boolean showincal) {
		this.showincal = showincal;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public String getDetails() {
		return details;
	}
	
	public String getTravel() {
		return travel;
	}
	
	public String getWeddingsFunerals() {
		return weddingsFunerals;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean getAvailable() {
		return available;
	}
	
	public boolean isAvailableSaturday() {
		return availableSaturday;
	}
	
	public boolean isAvailableSunday() {
		return availableSunday;
	}
	
	public boolean showInCalendar() {
		return showincal;
	}
	
	public void setFromOrganist(Organists organist) {
		this.username = organist.getUsername();
		this.nickname = organist.getNickname();
		this.fullname = organist.getFullname();
		this.details = organist.getDetails();
		this.travel = organist.getTravel();
		this.available = organist.getAvailable().booleanValue();
		this.availableSaturday = organist.getAvailsat().booleanValue();
		this.availableSunday = organist.getAvailsun().booleanValue();
		this.showincal = organist.getShowincal().booleanValue();
		this.weddingsFunerals = organist.getWedfun();
	}
	
	public void reset(ActionMapping actionMapping, HttpServletRequest request) {
		nickname = "";
		fullname = "";
		details = "";
		travel = "";
		weddingsFunerals = "";
		available = false;
		availableSaturday = false;
		availableSunday = false;
		showincal = true;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}

}


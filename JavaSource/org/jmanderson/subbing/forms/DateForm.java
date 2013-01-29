package org.jmanderson.subbing.forms;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class DateForm extends org.apache.struts.action.ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3785339118739130035L;

	private int id;

	private int year;

	private int month;

	private int day;

	private String username;

	private int locationid;

	private String dateNotes;

	private String played;

	private boolean tentative;

	// fields used if adding a Location instead of using an existing one
	private String name;

	private String address;

	private String city;

	private String state;

	private String zip;

	private String notes;

	private String usernotes;

	private boolean holiday;

	public DateForm() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setPlayed(String played) {
		if (played != null) {
			this.played = played;
		} else {
			this.played = "";
		}
	}

	public void setTentative(boolean tentative) {
		this.tentative = tentative;
	}

	public void setHoliday(boolean holiday) {
		this.holiday = holiday;
	}

	public int getId() {
		return id;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public String getUsername() {
		return username;
	}

	public int getLocationid() {
		return locationid;
	}

	public String getDateNotes() {
		return dateNotes;
	}

	public String getPlayed() {
		return played;
	}

	public boolean getTentative() {
		return tentative;
	}

	public String getUsernotes() {
		return usernotes;
	}

	public void setUsernotes(String usernotes) {
		this.usernotes = usernotes;
	}

	public boolean getHoliday() {
		return holiday;
	}

	public String getDisplayDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(month + 1).append("/").append(day).append("/").append(year);
		return sb.toString();
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest request) {
		id = 0;
		Calendar now = Calendar.getInstance();
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH);
		day = now.get(Calendar.DAY_OF_MONTH);
		username = "";
		locationid = 0;
		dateNotes = "";
		played = "";
		tentative = false;
	}

	public ActionErrors validate(ActionMapping actionMapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (locationid == 0) {
			if (name == null || name.length() == 0) {
				errors.add("location",
						new ActionError("error.missing.location"));
			} else {
				if (name == null || name.length() < 1) {
					errors.add("name", new ActionError("error.name.required"));
				}

				if (city == null || city.length() < 1) {
					errors.add("city", new ActionError("error.city.required"));
				}

				if (state == null || state.length() < 1) {
					errors
							.add("state", new ActionError(
									"error.state.required"));
				}

				if (state.length() != 2) {
					errors
							.add("state", new ActionError(
									"error.state.badstate"));
				}
				if (zip.length() > 0) {
					if (zip.length() != 5) {
						errors.add("zip",
								new ActionError("error.zip.badlength"));
					} else {
						try {
							Integer.parseInt(zip);
						} catch (NumberFormatException e) {
							errors.add("zip", new ActionError(
									"error.zip.notnumber"));
						}
					}
				}

			}
		}
		return errors;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city = string;
	}

	/**
	 * @param string
	 */
	public void setDateNotes(String string) {
		if (string != null) {
			dateNotes = string;
		} else {
			dateNotes = "";
		}
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
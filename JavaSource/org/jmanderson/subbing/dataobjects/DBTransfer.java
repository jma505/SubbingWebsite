package org.jmanderson.subbing.dataobjects;

import org.jmanderson.subbing.DateHelper;
import org.jmanderson.subbing.hibernate.Location;

/**
 * Transfer object to get data from the database to the
 * objects that can manupulate it.
 * 
 * <br>11/12/03 Added boolean tentative attribute and getters/setters for it.
 */
public class DBTransfer {

	private final int id;
	private int year;
	private String month;
	private int day;
	private String organist;
	private int locationId;
	private Location location;
	private String piecesPlayed;
	private String notes;
	private boolean tentative;
	
	public DBTransfer(int id) {
		this.id = id;
		year = -1;
		month = "";
		day = -1;
		organist = "";
		locationId = 0;
		location = null;
		piecesPlayed = "";
		notes = "";
		tentative = false;
	}
	
	
	/**
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return
	 */
	public String getMonthString() {
		return month;
	}

	public int getMonthInt() {
		return ((Integer) DateHelper.monthNumbers.get(month)).intValue();
	}
	
	/**
	 * @return
	 */
	public String getOrganist() {
		return organist;
	}

	/**
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param i
	 */
	public void setDay(int i) {
		day = i;
	}

	/**
	 * @param string
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @param string
	 */
	public void setMonth(String string) {
		month = string;
	}

	public void setMonth(int month) {
		this.month = DateHelper.MONTHS[month];
	}
	
	/**
	 * @param string
	 */
	public void setOrganist(String string) {
		organist = string;
	}

	/**
	 * @param i
	 */
	public void setYear(int i) {
		year = i;
	}

	/**
	 * @return
	 */
	public String getPiecesPlayed() {
		return piecesPlayed;
	}

	/**
	 * @param string
	 */
	public void setPiecesPlayed(String string) {
		piecesPlayed = string;
	}

	/**
	 * @return
	 */
	public int getLocationId() {
		return locationId;
	}

	/**
	 * @return
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param i
	 */
	public void setLocationId(int i) {
		locationId = i;
	}

	/**
	 * @param string
	 */
	public void setNotes(String string) {
		notes = string;
	}

	/**
	 * @return
	 */
	public boolean getTentative() {
		return tentative;
	}

	/**
	 * @param b
	 */
	public void setTentative(boolean tentative) {
		this.tentative = tentative;
	}

}

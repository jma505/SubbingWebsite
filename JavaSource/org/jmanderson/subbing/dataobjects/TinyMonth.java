package org.jmanderson.subbing.dataobjects;

/**
 * Used for getting month/year combinations back from the
 * DateHelper class in the right order.
 */
public class TinyMonth {

	private final String month;
	private final int year;

	public TinyMonth(String month, int year) {
		this.month = month;
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

}

package org.jmanderson.subbing.hibernate;
// Generated by MyEclipse - Hibernate Tools



/**
 * Holidays generated by MyEclipse - Hibernate Tools
 */
public class Holidays extends AbstractHolidays implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public Holidays() {
    }

    public Holidays(int year, int month, int day, String description) {
    	super(new Integer(year), new Integer(month), new Integer(day), description);
    }
   
}
/*
 * Created Wed Sep 07 17:38:49 EDT 2005 by MyEclipse Hibernate Tool.
 */ 
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;
import org.jmanderson.subbing.EncodeDecode;
import org.jmanderson.subbing.forms.DateForm;

/**
 * A class that represents a row in the 'schedules' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Schedules
    extends AbstractSchedules
    implements Serializable, Lifecycle
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9049325196644362790L;

	/**
     * Simple constructor of Schedules instances.
     */
    public Schedules()
    {
    }
    
    public Schedules(Integer id) {
    	super(id);
    }

	public boolean onDelete(Session arg0) throws CallbackException {
		
		return Lifecycle.NO_VETO;
	}

	public void onLoad(Session arg0, Serializable arg1) {
		
		setNotes(EncodeDecode.decode(getNotes()));
		setPlayed(EncodeDecode.decode(getPlayed()));
		setService_time(EncodeDecode.decode(getService_time()));
	}

	public boolean onSave(Session arg0) throws CallbackException {
		
		setNotes(EncodeDecode.encode(getNotes()));
		setPlayed(EncodeDecode.encode(getPlayed()));
		setService_time(EncodeDecode.encode(getService_time()));
		
		return Lifecycle.NO_VETO;
	}

	public boolean onUpdate(Session arg0) throws CallbackException {
		
		return onSave(arg0);
	}

    /* Add customized code below */
	
	public int getLocationId() {
		if (getLocation() != null) {
			return getLocation().getId().intValue();
		}
		else {
			return 0;
		}
	}

	public void setFromDateForm(DateForm form, Organists organist) {
		setYear(new Integer(form.getYear()));
		setMonth(new Integer(form.getMonth()));
		setDay(new Integer(form.getDay()));
		setNotes(form.getDateNotes());
		setPlayed(form.getPlayed());
		setTentative(new Boolean(form.getTentative()));
		setOrganists(organist);
		setHoliday(new Boolean(form.getHoliday()));
		/* TODO: add code to get time of service */
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getOrganists().getNickname()).append(":");
		sb.append(getMonth()).append("/").append(getDay()).append("/").append(getYear());
		if (getHoliday().booleanValue()) {
			sb.append(":Holiday");
		}
		return sb.toString();
	}
}

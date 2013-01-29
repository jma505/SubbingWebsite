/*
 * Created Wed Sep 07 17:32:58 EDT 2005 by MyEclipse Hibernate Tool.
 */ 
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;
import org.jmanderson.subbing.EncodeDecode;
import org.jmanderson.subbing.HtmlHelper;
import org.jmanderson.subbing.forms.LocationForm;

/**
 * A class that represents a row in the 'location' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Location
    extends AbstractLocation
    implements Serializable, Lifecycle
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8074430229800437802L;
	
	private String usernotes = "";

	/**
     * Simple constructor of Location instances.
     */
    public Location()
    {
    }

    public Location(LocationForm form) {
    	if (!form.getId().equals("0")) {
    		setId(Integer.valueOf(form.getId()));
    	}
    	setName(form.getName());
    	setAddress(form.getAddress());
    	setCity(form.getCity());
    	setState(form.getState());
    	setNotes(form.getNotes());
    	if (form.getZip().length() > 0) {
    		setZip(Integer.valueOf(form.getZip()));
    	}
    	else {
    		setZip(Integer.valueOf(0));
    	}
    }
    
    public void setUsernotes(String usernotes) {
    	if (usernotes != null) {
    		this.usernotes = usernotes;
    	}
    }
    
    public String getUsernotes() {
    	return usernotes;
    }
    
	public boolean onDelete(Session arg0) throws CallbackException {
		
		return Lifecycle.NO_VETO;
	}

	// TODO Figure out how to deal with usernotes!
	
	public void onLoad(Session arg0, Serializable arg1) {
		
		setName(EncodeDecode.decode(getName()));
		setAddress(EncodeDecode.decode(getAddress()));
		setCity(EncodeDecode.decode(getCity()));
		setNotes(EncodeDecode.decode(getNotes()));
		
		setUsernotes(EncodeDecode.decode(getUsernotes()));
	}

	public boolean onSave(Session arg0) throws CallbackException {
		
		setName(EncodeDecode.encode(getName()));
		setAddress(EncodeDecode.encode(getAddress()));
		setCity(EncodeDecode.encode(getCity()));
		setNotes(EncodeDecode.encode(getNotes()));
		
		setUsernotes(EncodeDecode.encode(getUsernotes()));
		
		return Lifecycle.NO_VETO;
	}

	public boolean onUpdate(Session arg0) throws CallbackException {
		
		return onSave(arg0);
	}

    /* Add customized code below */
	
	public String getDisplayName() {
		StringBuffer sb = new StringBuffer();
		if (getId().intValue() < 999999) {
			sb.append(getCity()).append(", ");
			sb.append(getState()).append(" - ");
		}
		sb.append(getName());
		return sb.toString();
	}

	public String getXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<location id=\"").append(getId().toString()).append("\">");
		sb.append("<name>").append(HtmlHelper.convert(getName())).append("</name>");
		sb.append("<address>").append(HtmlHelper.convert(getAddress())).append("</address>");
		sb.append("<city>").append(HtmlHelper.convert(getCity())).append("</city>");
		sb.append("<state>").append(HtmlHelper.convert(getState())).append("</state>");
		sb.append("<zip>").append(getZipDisplay()).append("</zip>");
		sb.append("<notes>").append(HtmlHelper.convert(getNotes())).append("</notes>");
		sb.append("<usernotes>").append(HtmlHelper.convert(getUsernotes())).append("</usernotes>");
		sb.append("</location>");

		return sb.toString();
	}

	public void setFromLocationForm(LocationForm lform) {
		setId(new Integer(lform.getId()));
		setName(lform.getName());
		setAddress(lform.getAddress());
		setCity(lform.getCity());
		setState(lform.getState());
		if (lform.getZip().length() > 0) {
			setZip(Integer.valueOf(lform.getZip()));
		}
		else {
			setZip(Integer.valueOf(0));
		}
		setNotes(lform.getNotes());
		setUsernotes(lform.getUsernotes());
	}

	public void setLocationForm(LocationForm lform) {
		lform.setId(getId().toString());
		lform.setName(getName());
		lform.setAddress(getAddress());
		lform.setCity(getCity());
		lform.setState(getState());
		lform.setZip(getZipDisplay());
		lform.setNotes(getNotes());
		lform.setUsernotes(getUsernotes());
	}

	public String toString() {
		return getName();
	}
	
	public String getZipDisplay() {
		StringBuffer sb = new StringBuffer();
		if (getZip().intValue() > 0) {
			DecimalFormat df = new DecimalFormat("00000");
			sb.append(df.format(getZip()));
		}
		return sb.toString();
	}
}

/*
 * Created Wed Sep 07 17:35:37 EDT 2005 by MyEclipse Hibernate Tool.
 */
package org.jmanderson.subbing.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;
import org.jmanderson.subbing.EncodeDecode;
import org.jmanderson.subbing.HtmlHelper;
import org.jmanderson.subbing.forms.OrganistForm;

/**
 * A class that represents a row in the 'organists' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class Organists extends AbstractOrganists implements Serializable,
		Lifecycle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7732954792588210910L;

	/**
	 * Simple constructor of Organists instances.
	 */
	public Organists() {
	}

	/**
	 * Constructor of Organists instances given a simple primary key.
	 * 
	 * @param username
	 */
	public Organists(java.lang.String username) {
		super(username);
	}

	/* Add customized code below */

	public String getXml() {

		StringBuffer sb = new StringBuffer();

		sb.append("<organist>");
		sb.append("<userid>").append(getUsername()).append("</userid>");
		sb.append("<shortName>").append(getNickname()).append("</shortName>");
		sb.append("<fullName>")
				.append((getFullname() == null ? "" : getFullname()))
				.append("</fullName>");

		try {
			Iterator iter = getPhoneSet().iterator();
			while (iter.hasNext()) {
				Phone phone = (Phone) iter.next();
				sb.append("<phone><number>").append(phone.toString())
						.append("</number>");
				sb.append("<pac>");
				String s = phone.getAreacode().toString();
				if (!s.equals("0")) {
					sb.append(s);
				}
				sb.append("</pac>");
				sb.append("<pp>").append(phone.getPhone().toString())
						.append("</pp>");
				sb.append("<pe>");
				s = phone.getExtension().toString();
				if (!s.equals("0")) {
					sb.append(s);
				}
				sb.append("</pe>");
				sb.append("<pt>").append(phone.getType()).append("</pt>");
				sb.append("<id>").append(phone.getId()).append("</id>");
				sb.append("</phone>");
			}
		} catch (Exception e) {
			// ignore
			System.out.println(e);
		}

		try {
			Iterator iter = getEmailSet().iterator();
			while (iter.hasNext()) {
				Email email = (Email) iter.next();
				sb.append("<email><address>").append(email.toString())
						.append("</address>");
				sb.append("<em>").append(email.getEmail()).append("</em>");
				sb.append("<pr>").append(email.getPreferred()).append("</pr>");
				sb.append("<id>").append(email.getId()).append("</id>");
				sb.append("</email>");
			}
		} catch (Exception e) {
			// ignore
		}

		sb.append("<details>")
				.append((getDetails() == null ? "" : HtmlHelper
						.convert(getDetails()))).append("</details>");
		sb.append("<travel>")
				.append((getTravel() == null ? "" : HtmlHelper
						.convert(getTravel()))).append("</travel>");
		sb.append("<wf>")
				.append((getWedfun().isEmpty() ? "NOT AVAILABLE" : HtmlHelper
						.convert(getWedfun()))).append("</wf>");
		sb.append("<available>").append(getAvailable()).append("</available>");
		sb.append("<activeUser>")
				.append((getAvailable().booleanValue() ? "Yes" : "No"))
				.append("</activeUser>");
		sb.append("<availableSaturday>")
				.append((getAvailsat().booleanValue() ? "Yes" : "No"))
				.append("</availableSaturday>");
		sb.append("<availableSunday>")
				.append((getAvailsun().booleanValue() ? "Yes" : "No"))
				.append("</availableSunday>");
		sb.append("<showInCalendar>")
				.append((getShowincal().booleanValue() ? "Yes" : "No"))
				.append("</showInCalendar>");
		sb.append("</organist>");

		return sb.toString();
	}

	public boolean onDelete(Session arg0) throws CallbackException {

		return Lifecycle.NO_VETO;
	}

	public void onLoad(Session arg0, Serializable arg1) {

		setNickname(EncodeDecode.decode(getNickname()));
		setFullname(EncodeDecode.decode(getFullname()));
		setDetails(EncodeDecode.decode(getDetails()));
		setTravel(EncodeDecode.decode(getTravel()));
		setWedfun(EncodeDecode.decode(getWedfun()));
	}

	public boolean onSave(Session arg0) throws CallbackException {

		setNickname(EncodeDecode.encode(getNickname()));
		setFullname(EncodeDecode.encode(getFullname()));
		setDetails(EncodeDecode.encode(getDetails()));
		setTravel(EncodeDecode.encode(getTravel()));
		setWedfun(EncodeDecode.encode(getWedfun()));

		return Lifecycle.NO_VETO;
	}

	public boolean onUpdate(Session arg0) throws CallbackException {

		return onSave(arg0);
	}

	public void loadFromOrganistForm(OrganistForm form) {
		setUsername(form.getUsername());
		setNickname(form.getNickname());
		setFullname(form.getFullname());
		setDetails(form.getDetails());
		setTravel(form.getTravel());
		setWedfun(form.getWeddingsFunerals());
		setAvailable(new Boolean(form.getAvailable()));
		setAvailsun(new Boolean(form.isAvailableSunday()));
		setAvailsat(new Boolean(form.isAvailableSaturday()));
		setShowincal(new Boolean(form.showInCalendar()));
	}
	
	public void loadFromParameters(String fullname, String details, String travel, String wedfun, String available, String availsun, String availsat, String showincal) {
		setFullname(fullname);
		setDetails(details);
		setTravel(travel);
		setWedfun(wedfun);
		setAvailable(new Boolean(available));
		setAvailsun(new Boolean(availsun));
		setAvailsat(new Boolean(availsat));
		setShowincal(new Boolean(showincal));
	}

	public boolean isAvailableSaturdays() {
		return getAvailsat().booleanValue();
	}

	public boolean isAvailableSundays() {
		return getAvailsun().booleanValue();
	}

	public boolean showInCalendar() {
		return getShowincal().booleanValue();
	}

	public boolean isAvailableWedFun() {
		if (getWedfun().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isActiveUser() {
		return getAvailable().booleanValue();
	}
}

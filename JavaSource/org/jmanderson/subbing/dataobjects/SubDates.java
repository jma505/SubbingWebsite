package org.jmanderson.subbing.dataobjects;

import java.util.Iterator;
import java.util.List;

import org.jmanderson.subbing.DateHelper;
import org.jmanderson.subbing.SubbingException;
import org.jmanderson.subbing.hibernate.Schedules;

/**
 * Contains the collection of months and days, along with the associated
 * information (either available organists or details for a specific organist)
 * as required by the front end.
 */
public class SubDates {

	private List months;
	private static boolean highlighted;

	public SubDates() {
		months = new SubbingArrayList();
	}

	public void addMonth(Month month) {
		if (!months.isEmpty() && month.equals(months.get(months.size()-1))) {
			return;
		}
		months.add(month);
	}

	public void updateDetails(Month month, Schedules schedule, int id) {
		int index = months.indexOf(month);
		if (index >= 0) {
			Month m = (Month) months.get(index);
			m.updateDetails(schedule.getDay().intValue(), schedule
					.getLocation(), schedule.getTentative().booleanValue(), id,
					schedule.getPlayed(), schedule.getHoliday().booleanValue(), schedule.getService_time());
		}
	}

	public void removeOrganist(Schedules schedule, String organist) {
		Month m = new Month(DateHelper.getMonthName(schedule.getMonth().intValue()), schedule.getYear().intValue());
		int index = months.indexOf(m);
		if (index >= 0) {
			m = (Month) months.get(index);
			try {
				m.removeOrganist(schedule.getDay().intValue(), organist, schedule.getHoliday().booleanValue());
			} catch (SubbingException e) {
				// ignore
			}
		}
	}

	public void setTentative(Schedules schedule, String organist) {
		Month m = new Month(DateHelper.getMonthName(schedule.getMonth().intValue()), schedule.getYear().intValue());
		int index = months.indexOf(m);
		if (index >= 0) {
			m = (Month) months.get(index);
			try {
				m.setTentative(schedule.getDay().intValue(), organist, schedule.getHoliday().booleanValue());
			} catch (SubbingException e) {
				// ignore
			}
		}
	}

	public String getXml(boolean ascending, boolean saturdays, boolean sundays, boolean highlightThisWeekend) {
		StringBuffer sb = new StringBuffer();
		highlighted = false;

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<months>");

		Iterator iter = months.iterator();
		while (iter.hasNext()) {
			Month month = (Month) iter.next();
			sb.append(month.getXml(ascending, saturdays, sundays, highlightThisWeekend));
		}

		sb.append("</months>");

		return sb.toString();
	}

	public static void setHighlighted() {
		highlighted = true;
	}
	
	public static boolean getHighlighted() {
		return highlighted;
	}
}

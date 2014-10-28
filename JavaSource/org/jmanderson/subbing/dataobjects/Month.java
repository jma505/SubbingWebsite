package org.jmanderson.subbing.dataobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmanderson.subbing.DateHelper;
import org.jmanderson.subbing.HtmlHelper;
import org.jmanderson.subbing.SubbingException;
import org.jmanderson.subbing.hibernate.Location;

/**
 * Contains all information about this month. The TreeMap of day information
 * contains values which are either a List of available organists, or a String
 * with the location information for where a single organist is playing on that
 * date.
 * 
 */
public class Month {

	/**
	 * DayKey is used to provide correct ordering for Holiday services when they
	 * fall on a Saturday or Sunday. There is the possibility of enhancing this
	 * class to also carry the name of the holiday.
	 */
	class DayKey implements Comparable {
		int day;

		boolean holiday;

		DayKey(int day, boolean holiday) {
			this.day = day;
			this.holiday = holiday;
		}

		int getDay() {
			return day;
		}

		boolean isHoliday() {
			return holiday;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Day=").append(day);
			if (holiday) {
				sb.append(" - Holiday");
			}
			return sb.toString();
		}

		public int compareTo(Object o) {

			if (o instanceof DayKey) {
				DayKey day2 = (DayKey) o;
				if (this.day < day2.day) {
					return -1;
				}
				if (this.day > day2.day) {
					return 1;
				}
				if (this.holiday == false && day2.holiday == true) {
					return -1;
				}
				if (this.holiday == true && day2.holiday == false) {
					return 1;
				}
			}
			return 0;
		}

		public boolean equals(Object o) {
			if (o instanceof DayKey) {
				DayKey day2 = (DayKey) o;
				if (this.day == day2.day) {
					if (this.holiday == day2.holiday) {
						return true;
					}
				}
			}
			return false;
		}

		public int hashCode() {
			int hash = 31 + day;
			hash = hash * 31 + (holiday == true ? 1 : 0);
			return hash;
		}

		String getDayString() {
			return Integer.toString(day);
		}
	}

	class DayInfo {
		boolean isLocation;

		int locationId;

		String location;

		List available;

		boolean tentative;

		int id;

		String piecesPlayed;

		boolean saturday = false;

		boolean holiday = false;
		
		String description;
		
		String service_time;

		DayInfo(List available) {
			update(available);
		}

		DayInfo(Location location, boolean tentative, int id,
				String piecesPlayed, String service_time) {
			update(location, tentative, id, piecesPlayed, service_time);
		}

		public String toString() {
			return location;
		}

		void update(List available) {
			isLocation = false;
			this.available = available;
			tentative = false;
		}

		void update(Location location, boolean tentative, int id,
				String piecesPlayed, String service_time) {
			isLocation = true;
			this.locationId = location.getId().intValue();
			StringBuffer sb = new StringBuffer();
			sb.append(location.getName());
			String city = location.getCity();
			if (locationId != 999999 && city != null && city.length() > 0) {
				sb.append(", ").append(city).append(" ").append(
						location.getState());
			}
			this.location = sb.toString();
			this.tentative = tentative;
			this.id = id;
			this.piecesPlayed = piecesPlayed;
			this.service_time = service_time;
		}

		String getDetails() {
			if (isLocation) {
				return location;
			} else {
				return available.toString();
			}
		}

		int getLocationId() {
			if (isLocation) {
				return locationId;
			} else {
				return 0;
			}
		}

		void setTentative(boolean tentative) {
			this.tentative = tentative;
		}

		boolean getTentative() {
			return tentative;
		}

		void setSaturday() {
			saturday = true;
		}

		boolean isSaturday() {
			return saturday;
		}

		void setHoliday() {
			holiday = true;
		}

		boolean isHoliday() {
			return holiday;
		}

		String getDescription() {
			return description;
		}
		
		void setDescription(String description) {
			this.description = description;
		}
		
		void setService_time(String service_time) {
			this.service_time = service_time;
		}
		
		String getService_time() {
			return service_time;
		}
	}

	private String month;

	private int year;

	private Map days;

	public Month(String month, int year) {
		this.month = month;
		this.year = year;
		days = new HashMap();
	}

	public Month(TinyMonth tinyMonth) {
		this(tinyMonth.getMonth(), tinyMonth.getYear());
	}

	public void addDay(int day, boolean saturday) {
		addDay(day, saturday, false, "");
	}

	public void addDay(int day, boolean saturday, boolean holiday) {
		addDay(day, saturday, holiday, "");
	}
	
	public void addDay(int day, boolean saturday, boolean holiday, String description) {
		DayInfo dayInfo = new DayInfo(new SubbingArrayList());
		if (saturday) {
			dayInfo.setSaturday();
		}
		if (holiday) {
			dayInfo.setHoliday();
		}
		dayInfo.setDescription(description);
		days.put(new DayKey(day, holiday), dayInfo);
	}

	public void addDay(int day, List available, boolean saturday) {
		addDay(day, available, saturday, false, "");
	}

	public void addDay(int day, List available, boolean saturday,
			boolean holiday, String description) {
		DayInfo dayInfo = new DayInfo(available);
		if (saturday) {
			dayInfo.setSaturday();
		}
		if (holiday) {
			dayInfo.setHoliday();
			dayInfo.setDescription(description);
		}
		days.put(new DayKey(day, holiday), dayInfo);
	}

	public void addDay(int day, Location location, boolean tentative, int id,
			String piecesPlayed, boolean saturday, boolean holiday, String description, String service_time) {
		DayInfo dayInfo = new DayInfo(location, tentative, id, piecesPlayed, service_time);
		if (saturday) {
			dayInfo.setSaturday();
		}
		if (holiday) {
			dayInfo.setHoliday();
			dayInfo.setDescription(description);
		}
		days.put(new DayKey(day, holiday), dayInfo);
	}

	public boolean hasDay(int day, boolean holiday) {
		return days.containsKey(new DayKey(day, holiday));
	}

	public void updateDetails(int day, Location location, boolean tentative,
			int id, String piecesPlayed, boolean holiday, String service_time) {
		DayInfo dayInfo = new DayInfo(location, tentative, id, piecesPlayed, service_time);
		DayKey key = new DayKey(day, holiday);
		DayInfo oldInfo = (DayInfo) days.get(key);
		if (oldInfo != null && oldInfo.isSaturday()) {
			dayInfo.setSaturday();
		} else if (oldInfo != null && oldInfo.isHoliday()) {
			dayInfo.setHoliday();
			dayInfo.setDescription(oldInfo.getDescription());
		}
		days.put(new DayKey(day, dayInfo.isHoliday()), dayInfo);
	}

	public void removeOrganist(int day, String organist, boolean holiday)
			throws SubbingException {
		DayInfo di = (DayInfo) days.get(new DayKey(day, holiday));
		if (!di.isLocation) {
			di.available.remove(organist);
		} else {
			throw new SubbingException(
					"Invalid DayInfo type for removeOrganist()");
		}
	}

	public void setTentative(int day, String organist, boolean holiday)
			throws SubbingException {
		DayInfo di = (DayInfo) days.get(new DayKey(day, holiday));
		if (!di.isLocation) {
			int idx = di.available.indexOf(organist);
			StringBuffer sb = new StringBuffer();
			sb.append("(").append(organist).append(")");
			di.available.set(idx, sb.toString());
		} else {
			throw new SubbingException(
					"Invalid DayInfo type for setTentative()");
		}
	}

	public String getXml(boolean ascending, boolean saturdays, boolean sundays, boolean highlightThisWeekend) {
		StringBuffer sb = new StringBuffer();
		List dayList = new ArrayList();

		sb.append("<month sundays=\"");
		sb.append(days.size()).append("\">");
		sb.append("<name>").append(month).append(" ");
		sb.append(year).append("</name>");

		Iterator iter = days.keySet().iterator();
		while (iter.hasNext()) {
			dayList.add((DayKey) iter.next());
		}

		if (ascending) {
			Collections.sort(dayList);
		} else {
			Collections.sort(dayList, Collections.reverseOrder());
		}

		DayKey dkey = null;
		boolean inGroup = true;
		int previousSunday = 0;
		int previousSaturday = 0;
		for (int i = 0; i < dayList.size(); i++) {
			// special case where Sundays are not listed, and the first of the
			// month is a Sunday
			if (i == 0) {
				int day = ((DayKey) dayList.get(0)).getDay();
				if (day == 1) {
					if (!((DayInfo) days.get(dayList.get(0))).isSaturday()
							&& !sundays) {
						i = 1;
					}
				}
				if (highlightThisWeekend && !SubDates.getHighlighted() && highlight(((DayKey) dayList.get(i)).getDay())) {
//					System.out.println("HIGHLIGHTING WEEKEND " + getMonth() + " " + ((DayKey) dayList.get(i)).getDay());
					SubDates.setHighlighted();
					sb.append("<weekend highlight=\"yes\">");
				}
				else {
					sb.append("<weekend highlight=\"no\">");
				}
			}

			// End of grouping ... reset tags
			if (!inGroup) {
				if (highlightThisWeekend && !SubDates.getHighlighted() && highlight(((DayKey) dayList.get(i)).getDay())) {
//					System.out.println("HIGHLIGHTING WEEKEND " + getMonth() + " " + ((DayKey) dayList.get(i)).getDay());
					SubDates.setHighlighted();
					sb.append("</weekend><weekend highlight=\"yes\">");
				}
				else {
					sb.append("</weekend><weekend highlight=\"no\">");
				}
				inGroup = true;
			}

			DayInfo d1 = (DayInfo) days.get(dayList.get(i));
			DayKey d2 = null;
			if ((i + 1) < dayList.size()) {
				d2 = (DayKey) dayList.get(i + 1);
			}
			if (d1.isSaturday()) {
				if (saturdays) {
					dkey = (DayKey) dayList.get(i);
					previousSaturday = dkey.getDay();
					String date = dkey.getDayString();
					appendXml(sb, d1, date, true, false, d1.getDescription());
					if (d2 == null) {
						if (sundays) {
							appendXml(sb, null, null, false, false, null);
						}
					} else {
						if (d2.isHoliday() && (d2.getDay() == previousSaturday)) {
//							inGroup = false;
							;
						} else if (!sundays) {
							inGroup = false;
						}
					}
				}
			} else if (d1.isHoliday()) {
				dkey = (DayKey) dayList.get(i);
				String date = dkey.getDayString();
				appendXml(sb, d1, date, false, true, d1.getDescription());
				if (d2 == null) {
					if (previousSaturday > previousSunday && sundays) {
						appendXml(sb, null, null, false, false, null);
					}
				} else {
					if (dkey.getDay() == previousSunday) {
						inGroup = false;
					} else if (d2.isHoliday()) {
						inGroup = false;
					} else if (d2.getDay() == (previousSaturday + 7)) {
						inGroup = false;
					}
				}
			} else { // Sunday
				if (sundays) {
					dkey = (DayKey) dayList.get(i);
					String date = dkey.getDayString();
					appendXml(sb, d1, date, false, false, null);
					previousSunday = dkey.getDay();
					if (d2 != null && d2.isHoliday()
							&& d2.getDay() == previousSunday) {
						;
					} else {
						inGroup = false;
					}
				}
			}
		}

		sb.append("</weekend></month>");
//System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * Appends XML information for saturdays and sundays
	 * 
	 * @param sb
	 *            StringBuffer (already in progress ...)
	 * @param info
	 *            DayInfo object
	 * @param date
	 *            String representation of the date (day only)
	 * @param saturday
	 *            boolean, true if this is a Saturday
	 * @param holiday
	 *            boolean, true if this is a holiday
	 */
	private void appendXml(StringBuffer sb, DayInfo info, String date,
			boolean saturday, boolean holiday, String holidayName) {
		if (saturday) {
			sb.append("<saturday>");
		} else if (holiday) {
			sb.append("<holiday>");
		} else {
			sb.append("<sunday>");
		}
		if (info != null) {
			sb.append("<day>").append(date).append("</day>");
			String details = HtmlHelper.convert(info.getDetails());
			sb.append("<details>").append(details).append("</details>");
			if (holiday) {
				sb.append("<holidayName>");
				if (holidayName == null) {
					sb.append("");
				} else {
					sb.append(holidayName);
				}
				sb.append("</holidayName>");
			}
			if (info.isLocation) {
				sb.append("<locationId>").append(info.locationId).append(
						"</locationId>");
				sb.append("<tentative>").append(info.tentative).append(
						"</tentative>");
				sb.append("<id>").append(info.id).append("</id>");
				details = HtmlHelper.convert(info.piecesPlayed);
				sb.append("<played>").append(details).append("</played>");
				sb.append("<service_time>").append(info.getService_time()).append("</service_time>");
			}
		}
		if (saturday) {
			sb.append("</saturday>");
		} else if (holiday) {
			sb.append("</holiday>");
		} else {
			sb.append("</sunday>");
		}
	}

	/**
	 * @return
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public int getYear() {
		return year;
	}

	public boolean equals(Object o) {
		boolean match = false;
		if (o instanceof Month) {
			Month month = (Month) o;
			if (this.month.equals(month.getMonth())
					&& this.year == month.getYear()) {
				match = true;
			}
		}
		return match;
	}

	public String toString() {
		return month + ", " + year;
	}
	
	/*
	 * This method determines if the weekend being generated in getXML() should be highlighted because
	 * it is either the current or upcoming weekend.
	 * 
	 * NOTE that "i" always comes in as a Saturday.
	 */
	private boolean highlight(int i) {
		Calendar cal = Calendar.getInstance();
//		String todayMonth = ((String) DateHelper.getMonthName(cal.get(Calendar.MONTH)));
//		int todayYear = cal.get(Calendar.YEAR);
//		int todayDay = cal.get(Calendar.DAY_OF_MONTH);
//		if (todayMonth.equals(this.month)) {
//			if (todayYear == this.year) {
//				// this Month object represents the current month
//				System.out.println("TODAY is " + todayDay + " " + todayMonth + " " + todayYear);
//				System.out.println("CHECKING against " + i + " " + this.month + " " + this.year);
//				if (i == (todayDay - 1)) { // Special case where today is a Sunday
//					return true;
//				}
//				if ((i >= todayDay) && (i - todayDay <= 6)) {
//					return true;
//				}
//			}
//		}
		Calendar cal2 = Calendar.getInstance();
		cal2.set(this.year, DateHelper.extractMonthFromDisplay(this.month), i);
		long diffTime = cal2.getTimeInMillis() - cal.getTimeInMillis();
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		if (diffDays >= -1 && diffDays <= 6) {
			return true;
		}
		return false;
	}
}

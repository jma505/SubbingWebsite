package org.jmanderson.subbing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jmanderson.subbing.DateHelper;
import org.jmanderson.subbing.EncodeDecode;
import org.jmanderson.subbing.dataobjects.Month;
import org.jmanderson.subbing.dataobjects.SmallUser;
import org.jmanderson.subbing.dataobjects.SubDates;
import org.jmanderson.subbing.dataobjects.SubbingArrayList;
import org.jmanderson.subbing.dataobjects.TinyMonth;
import org.jmanderson.subbing.hibernate.Holidays;
import org.jmanderson.subbing.hibernate.Location;
import org.jmanderson.subbing.hibernate.Location2;
import org.jmanderson.subbing.hibernate.Organists;
import org.jmanderson.subbing.hibernate.Schedules;
import org.jmanderson.subbing.hibernate.SessionFactory;

/**
 * Prepares the Data objects to be returned to the presentation layer. This has
 * been pulled out as a separate class so that the logic involved in combining
 * multiple pieces of information into a final answer does not have to live in
 * the Action servlet.
 * 
 * All methods are static for convenience, and return XML strings.
 */

public class DataPreparer {

	private DataPreparer() {
	}

	/**
	 * Gets the availability of organists for each date for 12 months (beginning
	 * with the first Sunday of the month containing the next Sunday after
	 * today)
	 * 
	 * @param ds
	 *            DataSource object
	 * @return XML string
	 */
	public static String getAvailabilityAsXML() {
		SubDates subDates = getSubDates();
		String xml = subDates.getXml(true, true, true, false);

		return xml;
	}

	public static Schedules getSchedule(int year, int month, int day,
			String organist, boolean holiday) {

		Schedules schedule = null;
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Schedules.class);
			criteria.setFetchMode("location", FetchMode.JOIN);
			criteria.add(Restrictions.eq("year", new Integer(year)));
			criteria.add(Restrictions.eq("month", new Integer(month)));
			criteria.add(Restrictions.eq("day", new Integer(day)));
			criteria.add(Restrictions.eq("organists.username", organist));
			criteria.add(Restrictions.eq("holiday", Boolean.valueOf(holiday)));
			schedule = (Schedules) criteria.uniqueResult();
			if (schedule == null) {
				schedule = new Schedules(new Integer(0));
			}
		} catch (Exception e) {
			// TODO
		} finally {
			SessionFactory.closeSession();
		}

		return schedule;
	}

	public static int getScheduleID(int year, int month, int day, String organist, boolean holiday) {
		return getSchedule(year, month, day, organist, holiday).getId().intValue();
	}
	
	public static int getScheduleLocationID(int year, int month, int day, String organist, boolean holiday) {
		return getSchedule(year, month, day, organist, holiday).getLocationId();
	}
	
	public static String getScheduleUnavailable(int year, int month, int day, String organist, boolean holiday) {
		Schedules schedule = getSchedule(year, month, day, organist, holiday);
		if (schedule != null) {
			return schedule.getUnavailable().toString();
		}
		else {
			return "false";
		}
	}
	
	public static String getScheduleNotes(int year, int month, int day, String organist, boolean holiday) {
		Schedules schedule = getSchedule(year, month, day, organist, holiday);
		if (schedule != null) {
			return schedule.getNotes();
		}
		else {
			return "";
		}
	}
	
	public static String getSchedulePieces(int year, int month, int day, String organist, boolean holiday) {
		Schedules schedule = getSchedule(year, month, day, organist, holiday);
		if (schedule != null) {
			return schedule.getPlayed();
		}
		else {
			return "";
		}
	}
	
	public static String getScheduleTime(int year, int month, int day, String organist, boolean holiday) {
		Schedules schedule = getSchedule(year, month, day, organist, holiday);
		if (schedule != null) {
			return schedule.getService_time();
		}
		else {
			return "";
		}
	}
	
	public static String getScheduleTentative(int year, int month, int day, String organist, boolean holiday) {
		Schedules schedule = getSchedule(year, month, day, organist, holiday);
		if (schedule != null) {
			return schedule.getTentative().toString();
		}
		else {
			return "false";
		}
	}
	
	public static String getOrganistsAsXML() {

		StringBuffer sb = new StringBuffer("<organists>");
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Organists.class);
			criteria.setFetchMode("emailSet", FetchMode.SELECT);
			criteria.setFetchMode("phoneSet", FetchMode.SELECT);
			criteria.add(Restrictions.eq("available", Boolean.TRUE));
			criteria.addOrder(Order.asc("nickname"));
			List organists = criteria.list();
			Iterator iter = organists.iterator();
			Organists organist;
			while (iter.hasNext()) {
				organist = (Organists) iter.next();
				sb.append(organist.getXml());
			}
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}
		sb.append("</organists>");

		return sb.toString();
	}

	public static String getOrganistAsXML(String nickname) {

		String xml = "";
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Organists.class);
			criteria.setFetchMode("emailSet", FetchMode.SELECT);
			criteria.setFetchMode("phoneSet", FetchMode.SELECT);
			criteria.add(Restrictions.eq("nickname",
					EncodeDecode.encode(nickname)));
			Organists organist = (Organists) criteria.uniqueResult();
			if (organist != null) {
				xml = organist.getXml();
			}
			else {
				System.out.println("DataPreparer.getOrganistAsXML(): organist object is null (" + nickname + ")");
			}
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}

		return xml;
	}

	public static List getNicknames() {
		List names = new ArrayList();
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Organists.class);
			criteria.add(Restrictions.eq("available", Boolean.TRUE));
			criteria.addOrder(Order.asc("nickname"));
			Iterator iter = criteria.list().iterator();

			Organists organist;
			while (iter.hasNext()) {
				organist = (Organists) iter.next();
				names.add(organist.getNickname());
			}
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
		} finally {
			SessionFactory.closeSession();
		}

		return names;
	}

	private static SubDates getSubDates() {

		List availableSaturdays = new ArrayList();
		List availableSundays = new ArrayList();
		List availableAll = new ArrayList();

		try {
			Session session = SessionFactory.currentSession();

			Criteria criteria = session.createCriteria(Organists.class);
			criteria.add(Restrictions.eq("available", Boolean.TRUE));
			criteria.add(Restrictions.eq("showincal", Boolean.TRUE));
			criteria.addOrder(Order.asc("nickname"));
			Iterator allOrganists = criteria.list().iterator();

			Organists organist;
			while (allOrganists.hasNext()) {
				organist = (Organists) allOrganists.next();
				availableAll.add(organist.getNickname());
				if (organist.isAvailableSaturdays()) {
					availableSaturdays.add(organist.getNickname());
				}
				if (organist.isAvailableSundays()) {
					availableSundays.add(organist.getNickname());
				}
			}
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}

		List months = DateHelper.getMonthsForDisplay(false);
		SubDates subDates = new SubDates();

		Iterator iterMonths = months.iterator();
		while (iterMonths.hasNext()) {
			Month month = new Month((TinyMonth) iterMonths.next());
			List sundays = DateHelper.getSundaysOfMonth(month.getMonth(),
					month.getYear());
			List saturdays = DateHelper.getSaturdaysOfMonth(month.getMonth(),
					month.getYear());
			List holidays = DateHelper.getOtherDaysOfMonth(month.getMonth(),
					month.getYear());
			Iterator iterSundays = sundays.iterator();
			Iterator iterSaturdays = saturdays.iterator();
			Iterator iterHolidays = holidays.iterator();
			int firstSaturday = ((Integer) iterSaturdays.next()).intValue();
			int firstSunday = ((Integer) iterSundays.next()).intValue();
			if (firstSaturday < firstSunday) {
				month.addDay(firstSaturday, new SubbingArrayList(
						availableSaturdays), true);
				month.addDay(firstSunday,
						new SubbingArrayList(availableSundays), false);
				while (iterSaturdays.hasNext()) {
					int saturday = ((Integer) iterSaturdays.next()).intValue();
					month.addDay(saturday, new SubbingArrayList(
							availableSaturdays), true);
					if (iterSundays.hasNext()) {
						int sunday = ((Integer) iterSundays.next()).intValue();
						month.addDay(sunday, new SubbingArrayList(
								availableSundays), false);
					}
				}
			} else {
				month.addDay(firstSunday,
						new SubbingArrayList(availableSundays), false);
				month.addDay(firstSaturday, new SubbingArrayList(
						availableSaturdays), true);
				while (iterSundays.hasNext()) {
					int sunday = ((Integer) iterSundays.next()).intValue();
					month.addDay(sunday,
							new SubbingArrayList(availableSundays), false);
					if (iterSaturdays.hasNext()) {
						int saturday = ((Integer) iterSaturdays.next())
								.intValue();
						month.addDay(saturday, new SubbingArrayList(
								availableSaturdays), true);
					}
				}
			}
			Holidays holiday;
			while (iterHolidays.hasNext()) {
				holiday = (Holidays) iterHolidays.next();
				int otherDay = holiday.getDay().intValue();
				if (!month.hasDay(otherDay, true)) {
					month.addDay(otherDay, new SubbingArrayList(availableAll),
							false, true, holiday.getDescription());
				}
			}
			subDates.addMonth(month);
		}

		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Schedules.class);
			criteria.setFetchMode("location", FetchMode.JOIN);
			// criteria.add(Restrictions.eq("organists.available",
			// Boolean.TRUE));
			criteria.add(Restrictions.sqlRestriction(DateHelper
					.getSQLDatePredicate(false)));
			criteria.addOrder(Order.asc("year"));
			criteria.addOrder(Order.asc("month"));
			criteria.addOrder(Order.asc("day"));
			Iterator iter = criteria.list().iterator();
			Schedules schedule;
			while (iter.hasNext()) {
				schedule = (Schedules) iter.next();
				if (schedule.getTentative().booleanValue()) {
					subDates.setTentative(schedule, schedule.getOrganists()
							.getNickname());
				} else {
					subDates.removeOrganist(schedule, schedule.getOrganists()
							.getNickname());
				}
			}
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
		} finally {
			SessionFactory.closeSession();
		}

		return subDates;
	}

	/**
	 * Gets the schedule for the specified organist. The schedule is for 13
	 * months, beginning with the month which contains the first Sunday after
	 * today.
	 * 
	 * @param ds
	 *            DataSource object
	 * @param organist
	 *            Nickname of the requested organist
	 * @param period
	 *            If true, get the previous 12 months
	 * @return XML string
	 */
	public static String getOrganistScheduleAsXML(boolean previous,
			String username) throws Exception {

		SubDates subDates = new SubDates();

		List months = null;
		if (previous) {
			months = DateHelper.getPreviousMonthsForDisplay();
		} else {
			months = DateHelper.getMonthsForDisplay(true);
		}
		Iterator iterMonths = months.iterator();
		while (iterMonths.hasNext()) {
			Month month = new Month((TinyMonth) iterMonths.next());
			List sundays = DateHelper.getSundaysOfMonth(month.getMonth(),
					month.getYear());
			List saturdays = DateHelper.getSaturdaysOfMonth(month.getMonth(),
					month.getYear());
			List holidays = DateHelper.getOtherDaysOfMonth(month.getMonth(),
					month.getYear());
			Iterator iterSundays = sundays.iterator();
			Iterator iterSaturdays = saturdays.iterator();
			Iterator iterHolidays = holidays.iterator();
			int firstSaturday = ((Integer) iterSaturdays.next()).intValue();
			int firstSunday = ((Integer) iterSundays.next()).intValue();
			if (firstSaturday < firstSunday) {
				month.addDay(firstSaturday, true);
				month.addDay(firstSunday, false);
				while (iterSaturdays.hasNext()) {
					int saturday = ((Integer) iterSaturdays.next()).intValue();
					month.addDay(saturday, true);
					if (iterSundays.hasNext()) {
						int sunday = ((Integer) iterSundays.next()).intValue();
						month.addDay(sunday, false);
					}
				}
			} else {
				month.addDay(firstSunday, false);
				month.addDay(firstSaturday, true);
				while (iterSundays.hasNext()) {
					int sunday = ((Integer) iterSundays.next()).intValue();
					month.addDay(sunday, false);
					if (iterSaturdays.hasNext()) {
						int saturday = ((Integer) iterSaturdays.next())
								.intValue();
						month.addDay(saturday, true);
					}
				}
			}
			Holidays holiday;
			while (iterHolidays.hasNext()) {
				holiday = (Holidays) iterHolidays.next();
				int holidayDate = holiday.getDay().intValue();
				if (!month.hasDay(holidayDate, true)) {
					month.addDay(holidayDate, false, true,
							holiday.getDescription());
				}
			}
			subDates.addMonth(month);
		}

		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Schedules.class);

			criteria.setFetchMode("location", FetchMode.JOIN);
			criteria.add(Expression.eq("organists.username", username));
			if (previous) {
				criteria.add(Expression.sql(DateHelper
						.getSQLDatePredicatePrevious()));
			} else {
				criteria.add(Expression.sql(DateHelper
						.getSQLDatePredicate(true)));
			}

			criteria.addOrder(Order.asc("year"));
			criteria.addOrder(Order.asc("month"));
			criteria.addOrder(Order.asc("day"));
			Iterator iter = criteria.list().iterator();
			Schedules schedule;
			Month month;
			while (iter.hasNext()) {
				schedule = (Schedules) iter.next();
				month = new Month(DateHelper.getMonthName(schedule.getMonth()
						.intValue()), schedule.getYear().intValue());
				subDates.updateDetails(month, schedule, schedule.getId()
						.intValue());
			}
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}

		Organists org = getOrganist(username);
		boolean highlightThisWeekend = (previous ? false : true);
		String xml = subDates.getXml(true, org.getAvailsat().booleanValue(),
				org.getAvailsun().booleanValue(), highlightThisWeekend);

		return xml;
	}

	/**
	 * Gets the current logged-in user's information for display and potential
	 * edit.
	 * 
	 * @param ds
	 *            DataSource
	 * @param userid
	 * @return Organist object
	 */
	public static Organists getMyInfo(String userid) {

		Organists organist = null;
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Organists.class);
			criteria.setFetchMode("emailSet", FetchMode.JOIN);
			criteria.setFetchMode("phoneSet", FetchMode.JOIN);
			criteria.add(Restrictions.eq("username", userid));
			organist = (Organists) criteria.uniqueResult();
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}
		return organist;
	}

	public static String getMyName(String userid) {
		Organists organist = getMyInfo(userid);
		return organist.getFullname();
	}

	public static String getMyDetails(String userid) {
		Organists organist = getMyInfo(userid);
		return organist.getDetails();
	}

	public static String getMyTravel(String userid) {
		Organists organist = getMyInfo(userid);
		return organist.getTravel();
	}

	public static String getMyWedFun(String userid) {
		Organists organist = getMyInfo(userid);
		return organist.getWedfun();
	}

	public static String getMyAvailSat(String userid) {
		Organists organist = getMyInfo(userid);
		return organist.getAvailsat().toString();
	}

	public static String getMyAvailSun(String userid) {
		Organists organist = getMyInfo(userid);
		return organist.getAvailsun().toString();
	}

	public static String getMyShowInCal(String userid) {
		Organists organist = getMyInfo(userid);
		return organist.getShowincal().toString();
	}

	public static String getMyIsActiveUser(String userid) {
		Organists organist = getMyInfo(userid);
		return Boolean.toString(organist.isActiveUser());
	}

	public static String getLocationsAsXML(String username) {

		List locations = getLocationsAsList(username);
		Iterator iter = locations.iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("<locations>");
		while (iter.hasNext()) {
			Location loc = (Location) iter.next();
			sb.append(loc.getXml());
		}
		sb.append("</locations>");
		return sb.toString();
	}

	public static String getLocationsForForm(String username, String listname) {
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\"").append(listname).append("\" id=\"").append(listname).append("\" data-dojo-type=\"dijit/form/Select\" required=\"true\">");
		ArrayList list = (ArrayList) getLocationsAsList(username);
		Iterator iter = list.iterator();
		Location location;
		while (iter.hasNext()) {
			location = (Location) iter.next();
			if (location.getId().intValue() < 999999) {
			sb.append("<option value=\"").append(location.getId().toString()).append("\">");
			sb.append(location.getDisplayName()).append("</option>");
			}
		}
		sb.append("<option value=\"0\" selected=\"selected\">(<b><i>EITHER</i></b> select a Location from this list)</option></select>");
		return sb.toString();
	}
	
	public static List getLocationsAsList(String username) {

		List locations = null;
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Location.class);
			criteria.addOrder(Order.asc("state"));
			criteria.addOrder(Order.asc("city"));
			criteria.addOrder(Order.asc("name"));
			locations = criteria.list();
			if (locations != null) {
				criteria = session.createCriteria(Location2.class);
				criteria.add(Restrictions.eq("username", username));
				criteria.setProjection(Projections.rowCount());
				int count = ((Integer) criteria.list().get(0)).intValue();
				if (count > 0) {
					Iterator iter = locations.iterator();
					Location location = null;
					Location2 location2 = null;
					while (iter.hasNext()) {
						location = (Location) iter.next();
						criteria = session.createCriteria(Location2.class);
						criteria.add(Restrictions.eq("username", username));
						criteria.add(Restrictions.eq("locationid",
								location.getId()));
						location2 = (Location2) criteria.uniqueResult();
						if (location2 != null) {
							location.setUsernotes(location2.getNotes());
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
		} finally {
			SessionFactory.closeSession();
		}

		if (locations == null) {
			locations = new ArrayList();
		}

		return locations;
	}

	public static Location getLocation(int id, String username) {

		Location location = null;
		try {
			Session session = SessionFactory.currentSession();
			location = (Location) session.get(Location.class, new Integer(id));
			if (location != null) {
				if (username != null) {
					Criteria criteria = session.createCriteria(Location2.class);
					criteria.add(Restrictions.eq("username", username));
					criteria.add(Restrictions.eq("locationid", location.getId()));
					Location2 location2 = (Location2) criteria.uniqueResult();
					if (location2 != null) {
						location.setUsernotes(location2.getNotes());
					}
				}
			}
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}

		if (location == null) {
			location = new Location();
		}

		return location;
	}

	public static String getLocationDisplay(String locId) {
		StringBuffer sb = new StringBuffer();
		Location location = getLocation(Integer.parseInt(locId), null);
		sb.append(location.getName());
		sb.append(" (").append(location.getCity()).append(", ");
		sb.append(location.getState()).append(")");

		return sb.toString();
	}

	public static String getLocationPiecesAsXml(String username, String locId)
			throws Exception {

		SubDates subDates = new SubDates();

		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Schedules.class);
			criteria.add(Restrictions.eq("organists.username", username));
			criteria.add(Restrictions.eq("location.id", new Integer(locId)));
			criteria.add(Restrictions.sqlRestriction("length(played) > 0"));
			criteria.addOrder(Order.desc("year"));
			criteria.addOrder(Order.desc("month"));
			criteria.addOrder(Order.desc("day"));
			Iterator iter = criteria.list().iterator();
			Schedules schedule;
			Month month;
			while (iter.hasNext()) {
				schedule = (Schedules) iter.next();
				month = new Month(DateHelper.getMonthName(schedule.getMonth()
						.intValue()), schedule.getYear().intValue());
				subDates.addMonth(month);
				subDates.updateDetails(month, schedule, 0);
			}
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
		} finally {
			SessionFactory.closeSession();
		}

		return subDates.getXml(false, true, true, false);
	}

	public static String getMyPiecesAsXml(String username) throws Exception {

		SubDates subDates = new SubDates();

		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Schedules.class);
			criteria.setFetchMode("location", FetchMode.JOIN);
			criteria.add(Restrictions.eq("organists.username", username));
			criteria.add(Restrictions.sqlRestriction("length(played) > 0"));
			criteria.addOrder(Order.desc("year"));
			criteria.addOrder(Order.desc("month"));
			criteria.addOrder(Order.desc("day"));
			Iterator iter = criteria.list().iterator();
			Schedules schedule;
			Month month;
			while (iter.hasNext()) {
				schedule = (Schedules) iter.next();
				month = new Month(DateHelper.getMonthName(schedule.getMonth()
						.intValue()), schedule.getYear().intValue());
				subDates.addMonth(month);
				subDates.updateDetails(month, schedule, 0);
			}
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
		} finally {
			SessionFactory.closeSession();
		}

		return subDates.getXml(false, true, true, false);
	}

	/**
	 * THIS ONE STAYS AS IS.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAllUsersAsXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		List users = new ArrayList();
		SmallUser user = null;

		Session session = null;
		try {
			session = SessionFactory.currentSession();
			Connection conn = session.connection();
			PreparedStatement pStmt = conn
					.prepareStatement("select a.username, b.nickname, b.fullname, a.lastlogin, a.status, a.comments from users a, organists b where b.username = a.username order by a.username");
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				user = new SmallUser();
				user.setUsername(rs.getString("username"));
				user.setNickname(EncodeDecode.decode(rs.getString("nickname")));
				user.setFullname(EncodeDecode.decode(rs.getString("fullname")));
				user.setLastlogin(rs.getString("lastlogin"));
				user.setStatus(rs.getInt("status"));
				user.setComments(rs.getString("comments"));
				if (user.getComments() == null) {
					user.setComments("");
				}
				users.add(user);
			}

		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
		} finally {
			session.disconnect();
			SessionFactory.closeSession();
		}
		Iterator iter = users.iterator();
		sb.append("<users>");
		while (iter.hasNext()) {
			user = (SmallUser) iter.next();
			sb.append(user.getXML());
		}
		sb.append("</users>");

		return sb.toString();
	}

	public static Organists getOrganist(String username) throws Exception {

		return getMyInfo(username);
	}

	public static String getMyXml(String username) throws Exception {

		Organists organist = getMyInfo(username);
		if (organist != null) {
			return organist.getXml();
		} else {
			return "not found";
		}
	}

	public static List getHolidaysForMonth(int month, int year) {

		List holidays = new ArrayList();

		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Holidays.class);
			criteria.add(Restrictions.eq("year", Integer.valueOf(year)));
			criteria.add(Restrictions.eq("month", Integer.valueOf(month)));
			criteria.addOrder(Order.asc("day"));
			Iterator iter = criteria.list().iterator();
			Holidays holiday;
			while (iter.hasNext()) {
				holiday = (Holidays) iter.next();
				holidays.add(holiday);
			}
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}

		return holidays;
	}

	public static String getHolidaysListDisplay() {
		
		List holidays = new ArrayList();
		
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Holidays.class);
			criteria.addOrder(Order.desc("year"));
			criteria.addOrder(Order.asc("month"));
			criteria.addOrder(Order.asc("day"));
			Iterator iter = criteria.list().iterator();
			Holidays holiday;
			while (iter.hasNext()) {
				holiday = (Holidays) iter.next();
				holidays.add(holiday);
			}
		} catch (Exception e) {
			// TODO logging
		} finally {
			SessionFactory.closeSession();
		}
		
		StringBuffer sb = new StringBuffer();
		Iterator iter = holidays.iterator();
		Holidays holiday;
		int month, day, year;
		String description, monthD;
		sb.append("<table border=\"0\" cellspacing=\"5\" cellpadding=\"5\">");
		while (iter.hasNext()) {
			holiday = (Holidays) iter.next();
			month = holiday.getMonth().intValue();
			monthD = DateHelper.getMonthName(month);
			day = holiday.getDay().intValue();
			year = holiday.getYear().intValue();
			description = holiday.getDescription();
			sb.append("<tr><td>").append(monthD).append(" ").append(day).append(", ").append(year).append("</td>");
			sb.append("<td>").append(description).append("</td></tr>");
		}
		return sb.toString();
		
	}
}

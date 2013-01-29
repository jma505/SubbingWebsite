package org.jmanderson.subbing;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.jmanderson.subbing.dataobjects.SubbingArrayList;
import org.jmanderson.subbing.dataobjects.TinyMonth;

/**
 * Contains methods to process the months and years used
 * as dates for the Subbing application.
 * 
 * NOTE: If the value in MONTHS_TO_DISPLAY changes from 12,
 *       then the getSQLDatePredicate() method needs to be
 *       drastically rewritten, because it assumes 12 months!!
 * 
 * As of 6/17/2004, some information is cached on a daily basis to prevent extra work.
 */
public class DateHelper {

	public static final String[] MONTHS;
	public static final HashMap monthNumbers;
	private static final int MONTHS_TO_DISPLAY = 12;

	private static final int MONTHSCACHE = 1;
	private static List monthsForDisplay;
	private static List adminMonthsForDisplay;
	private static long timeGenerated1 = 0L;
	private static final int PREVMONTHSCACHE = 2;
	private static List previousMonthsForDisplay;
	private static long timeGenerated2 = 0L;
	private static final int PREVSQLCACHE = 3;
	private static String sqlDatePredicatePrevious;
	private static long timeGenerated3 = 0L;
	private static final int SQLCACHE = 4;
	private static String sqlDatePredicate;
	private static long timeGenerated4 = 0L;
	private static final int SQLCACHEADMIN = 5;
	private static String sqlDatePredicateAdmin;
	private static long timeGenerated5 = 0L;

	static {
		MONTHS = new String[Calendar.DECEMBER + 1];
		MONTHS[Calendar.JANUARY] = "Jan";
		MONTHS[Calendar.FEBRUARY] = "Feb";
		MONTHS[Calendar.MARCH] = "Mar";
		MONTHS[Calendar.APRIL] = "Apr";
		MONTHS[Calendar.MAY] = "May";
		MONTHS[Calendar.JUNE] = "Jun";
		MONTHS[Calendar.JULY] = "Jul";
		MONTHS[Calendar.AUGUST] = "Aug";
		MONTHS[Calendar.SEPTEMBER] = "Sep";
		MONTHS[Calendar.AUGUST] = "Aug";
		MONTHS[Calendar.OCTOBER] = "Oct";
		MONTHS[Calendar.NOVEMBER] = "Nov";
		MONTHS[Calendar.DECEMBER] = "Dec";
		monthNumbers = new HashMap();
		monthNumbers.put("Jan", new Integer(Calendar.JANUARY));
		monthNumbers.put("Feb", new Integer(Calendar.FEBRUARY));
		monthNumbers.put("Mar", new Integer(Calendar.MARCH));
		monthNumbers.put("Apr", new Integer(Calendar.APRIL));
		monthNumbers.put("May", new Integer(Calendar.MAY));
		monthNumbers.put("Jun", new Integer(Calendar.JUNE));
		monthNumbers.put("Jul", new Integer(Calendar.JULY));
		monthNumbers.put("Aug", new Integer(Calendar.AUGUST));
		monthNumbers.put("Sep", new Integer(Calendar.SEPTEMBER));
		monthNumbers.put("Oct", new Integer(Calendar.OCTOBER));
		monthNumbers.put("Nov", new Integer(Calendar.NOVEMBER));
		monthNumbers.put("Dec", new Integer(Calendar.DECEMBER));
	}

	/**
	 * Private constructor ...
	 */
	private DateHelper() {
	}

	private static boolean generatedToday(int type) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date date = cal.getTime();

		long testtime = 0L;
		switch (type) {
			case MONTHSCACHE :
				testtime = timeGenerated1;
				break;
			case PREVMONTHSCACHE :
				testtime = timeGenerated2;
				break;
			case PREVSQLCACHE :
				testtime = timeGenerated3;
				break;
			case SQLCACHE :
				testtime = timeGenerated4;
				break;
			case SQLCACHEADMIN:
				testtime = timeGenerated5;
				break;
		}
		return (testtime >= date.getTime());
	}

	/**
	 * @param extraMonth TRUE if one extra month should be generated for admin display
	 */
	public static List getMonthsForDisplay(boolean extraMonth) {
		if (!generatedToday(MONTHSCACHE)) {
			List months = new SubbingArrayList();
			TinyMonth tm = null;

			int month = getThisMonth();
			int year = getThisYear();
			if (month == 12) {
				month = 0;
			}
			int loopMonth = month;

			for (int i = 0; i < MONTHS_TO_DISPLAY; i++) {
				loopMonth = (month + i) % 12;
				if ((loopMonth == Calendar.JANUARY) && (i > 0))
					year++;
				tm = new TinyMonth(MONTHS[loopMonth], year);
				months.add(tm);
			}
			monthsForDisplay = new SubbingArrayList(months);

			loopMonth = (loopMonth + 1) % 12;
			if ((loopMonth == Calendar.JANUARY))
				year++;
			tm = new TinyMonth(MONTHS[loopMonth], year);
			months.add(tm);
			adminMonthsForDisplay = new SubbingArrayList(months);

			timeGenerated1 = System.currentTimeMillis();
		}
		return (extraMonth ? adminMonthsForDisplay : monthsForDisplay);
	}

	/**
	 * Returns a list of the previous 12 months.
	 * @return List of months
	 */
	public static List getPreviousMonthsForDisplay() {
		if (!generatedToday(PREVMONTHSCACHE)) {
			List months = new SubbingArrayList();
			TinyMonth tm = null;

			int month = getThisMonth();
			int year = getThisYear() - 1;
			if (month == 12) {
				month = 0;
				// year += 1; -- Commented out 1/1/08 because on 12/31/07 this caused an invalid previous schedule display
			}

			int loopMonth = month;

			for (int i = 0; i < MONTHS_TO_DISPLAY; i++) {
				loopMonth = (month + i) % 12;
				if ((loopMonth == Calendar.JANUARY) && (i > 0))
					year++;
				tm = new TinyMonth(MONTHS[loopMonth], year);
				months.add(tm);
			}
			previousMonthsForDisplay = new SubbingArrayList(months);
			timeGenerated2 = System.currentTimeMillis();
		}

		return previousMonthsForDisplay;
	}

	/**
	 * Determines whether the current month should be
	 * displayed.  If today is not Sunday, and the following
	 * Sunday is not in this month, then don't display the
	 * current month.
	 * @param Calendar the current date
	 * @return boolean True if we should display the current month
	 */
	private static boolean displayThisMonth(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day > 21) {
			int lastSunday = getLastSundayOfThisMonth();
			if (day > lastSunday) {
				int lastSaturday = getLastSaturdayOfThisMonth();
				if (day > lastSaturday)
					return false;
			}
		}
		return true;
	}

	/**
	 * Determines the date of the last Sunday in the current
	 * month.
	 * @return int
	 */
	private static int getLastSundayOfThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	private static int getLastSaturdayOfThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Returns a List containing a series of Integers, one
	 * for each Sunday in the month requested.
	 * @param month
	 * @param year
	 * @return List of Integers
	 */
	public static List getSundaysOfMonth(String month, int year) {
		SubbingArrayList list = new SubbingArrayList();
		int monthNum = ((Integer) monthNumbers.get(month)).intValue();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, monthNum);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
		int d = 0;
		int date = cal.get(Calendar.DATE);
		while (date > d) {
			d = date;
			list.add(new Integer(date));
			cal.add(Calendar.WEEK_OF_MONTH, 1);
			date = cal.get(Calendar.DATE);
		}
		return list;
	}

	public static List getSaturdaysOfMonth(String month, int year) {
		SubbingArrayList list = new SubbingArrayList();
		int monthNum = ((Integer) monthNumbers.get(month)).intValue();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, monthNum);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
		int d = 0;
		int date = cal.get(Calendar.DATE);
		while (date > d) {
			d = date;
			list.add(new Integer(date));
			cal.add(Calendar.WEEK_OF_MONTH, 1);
			date = cal.get(Calendar.DATE);
		}
		return list;
	}

	public static List getOtherDaysOfMonth(String month, int year) {
		
		int monthNum = ((Integer) monthNumbers.get(month)).intValue();		
		List holidays = DataPreparer.getHolidaysForMonth(monthNum, year);
		
		return new SubbingArrayList(holidays);
	}
	
	/** 
	 * Determines the first month to be displayed, which
	 * will be either the current month or the next month.
	 * NOTE: Months are numbered starting at 0.  If this method returns
	 * 	      12, then this is December, but there aren't any Sundays left.
	 * @return int
	 * @see displayThisMonth()
	 */
	private static int getThisMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		if (displayThisMonth(cal))
			return month;
		else
			return (month + 1);
	}

	/** 
	 * Determines the 4-digit year of the first month to
	 * be displayed.
	 * @return int
	 * @see getThisMonth()
	 */
	private static int getThisYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, getThisMonth());
		return cal.get(Calendar.YEAR);
	}

	public static String getSQLDatePredicatePrevious() {
		if (!generatedToday(PREVSQLCACHE)) {
			StringBuffer sb = new StringBuffer();
			sb.append('(');

			// Special case: this is January, so just display the
			// entire previous calendar year

			if (getThisMonth() == Calendar.JANUARY) {
				sb.append("year = ").append(getThisYear() - 1);
			} else {
				int thisMonth = getThisMonth();
				int thisYear = getThisYear();
				if (thisMonth == 12) {
					thisMonth = 0;
				}
				int lastYear = thisYear - 1;
				sb.append("(year = ").append(lastYear);
				sb.append(" and month >= ").append(thisMonth);
				sb.append(") or (year = ").append(thisYear);
				sb.append(" and month <");
				sb.append(thisMonth);
				sb.append(")");
			}
			sb.append(')');
			sqlDatePredicatePrevious = sb.toString();
			timeGenerated3 = System.currentTimeMillis();
		}
		return sqlDatePredicatePrevious;
	}

	/**
	 * Generates a SQL predicate which selects by dates.
	 * @param admin TRUE if user is admin, which causes 13 rather than 12 months to be returned
	 * @return String
	 */
	public static String getSQLDatePredicate(boolean admin) {
		if (admin && generatedToday(SQLCACHEADMIN)) {
			return sqlDatePredicateAdmin;
		}
		else if (!admin && generatedToday(SQLCACHE)) {
			return sqlDatePredicate;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append('(');

		// Special case: this is January, so just display
		//               the entire calendar year

		if (getThisMonth() == Calendar.JANUARY && !admin) {
			sb.append("year = ").append(getThisYear());
		} else {
			int thisMonth = getThisMonth();
			int thisYear = getThisYear();
			if (thisMonth == 12) {
				thisMonth = 0;
			}
			int nextYear = thisYear + 1;
			sb.append("(year = ").append(thisYear);
			sb.append(" and month >= ").append(thisMonth);
			sb.append(") or (year = ").append(nextYear);
			sb.append(" and month <");
			sb.append((admin ? "= " : " "));
			sb.append(thisMonth);
			sb.append(")");
		}
		sb.append(')');

		if (admin) {
			sqlDatePredicateAdmin = sb.toString();
			timeGenerated5 = System.currentTimeMillis();
		}
		else {
			sqlDatePredicate = sb.toString();
			timeGenerated4 = System.currentTimeMillis();
		}
		
		return sb.toString();
	}

	public static int extractMonthFromDisplay(String display) {
		String monthStr = display.substring(0, 3);
		Integer month = (Integer) monthNumbers.get(monthStr);
		return month.intValue();
	}

	public static int extractYearFromDisplay(String display) {
		String yearStr = display.substring(display.length() - 4);
		return Integer.parseInt(yearStr);
	}

	public static String getMonthName(int month) {
		return MONTHS[month];
	}
}

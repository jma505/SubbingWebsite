package org.jmanderson.subbing;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jmanderson.subbing.forms.OrganistForm;
import org.jmanderson.subbing.forms.PasswordForm;
import org.jmanderson.subbing.forms.UserForm;
import org.jmanderson.subbing.hibernate.Email;
import org.jmanderson.subbing.hibernate.Holidays;
import org.jmanderson.subbing.hibernate.Location;
import org.jmanderson.subbing.hibernate.Location2;
import org.jmanderson.subbing.hibernate.Organists;
import org.jmanderson.subbing.hibernate.Phone;
import org.jmanderson.subbing.hibernate.Schedules;
import org.jmanderson.subbing.hibernate.SessionFactory;
import org.jmanderson.subbing.hibernate.Users;

/**
 * Contains static methods which are used to update data in the database.
 */
public class DataUpdater {

	private static final String NEW_PASSWORD = "subbing";

	private DataUpdater() {
	}

	public static void addOrUpdatePhone2(String username, int id, int ac,
			int pp, int pe, String pt) throws Exception {

		Organists organist = DataPreparer.getOrganist(username);
		Phone phone = new Phone();
		if (id > 0) {
			phone.setId(new Integer(id));
		}
		phone.setAreacode(new Integer(ac));
		phone.setPhone(new Integer(pp));
		phone.setExtension(new Integer(pe));
		phone.setType(pt);
		phone.setOrganists(organist);
		addOrUpdatePhone(organist, phone);
	}

	public static void addOrUpdatePhone(Organists organist, Phone phone)
			throws Exception {
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(phone);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void deletePhone(String id) throws Exception {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Phone phone = (Phone) session.load(Phone.class, new Integer(id));
			session.delete(phone);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void addOrUpdateEmail2(String username, int id,
			String emailAddr, boolean preferred) throws Exception {

		Organists organist = DataPreparer.getOrganist(username);
		Email email = new Email();
		if (id > 0) {
			email.setId(new Integer(id));
		}
		email.setEmail(emailAddr);
		email.setPreferred(new Boolean(preferred));
		email.setOrganists(organist);
		addOrUpdateEmail(organist, email);
	}

	public static void addOrUpdateEmail(Organists organist, Email email)
			throws Exception {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			if (email.getPreferred().booleanValue()) {
				Criteria criteria = session.createCriteria(Email.class);
				criteria.add(Restrictions.eq("organists.username",
						organist.getUsername()));
				criteria.add(Restrictions.eq("preferred", Boolean.TRUE));
				Iterator iter = criteria.list().iterator();
				Email e;
				while (iter.hasNext()) {
					e = (Email) iter.next();
					e.setPreferred(Boolean.FALSE);
					session.update(e);
				}
				session.flush();
			}
			session.saveOrUpdate(email);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}

	}

	public static void deleteEmail(String id) throws Exception {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Email email = (Email) session.load(Email.class, new Integer(id));
			session.delete(email);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}

	}

	public static void updatePassword(String username, PasswordForm form)
			throws Exception {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Users user = (Users) session.get(Users.class, username);
			user.setPassword(EncodeDecode.passwordEncode(form.getNewPassword1()));
			session.update(user);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static String updatePassword2(String username, String oldpwd,
			String newpwd) throws Exception {
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("username", username));
			Users user = (Users) criteria.uniqueResult();
			if (!user.passwordMatches(oldpwd)) {
				return "ERROR: Incorrect current password";
			}
			txn = session.beginTransaction();
			user = (Users) session.get(Users.class, username);
			user.setPassword(EncodeDecode.passwordEncode(newpwd));
			session.update(user);
			txn.commit();
		} catch (Exception e) {
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}

		return "Password changed";
	}

	public static void addOrUpdateLocation2(String username, int id,
			String name, String address, String city, String state, int zip,
			String notes, String usernotes) throws Exception {
		Location location = new Location();
		if (id > 0) {
			location.setId(new Integer(id));
		}
		location.setName(name);
		location.setAddress(address);
		location.setCity(city);
		location.setState(state);
		location.setZip(new Integer(zip));
		location.setNotes(notes);
		addOrUpdateLocation(location, usernotes, username);
	}

	public static void addOrUpdateLocation(Location location, String usernotes,
			String username) throws Exception {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(location);
			
			Criteria criteria = session.createCriteria(Location2.class);
			criteria.add(Restrictions.eq("username", username));
			criteria.add(Restrictions.eq("locationid", location.getId()));
			Location2 location2 = (Location2) criteria.uniqueResult();
			if (location2 == null) {
				if (usernotes != null && usernotes.length() > 0) {
					location2 = new Location2();
					location2.setLocationid(location.getId());
					location2.setUsername(username);
					location2.setNotes(usernotes);
					session.saveOrUpdate(location2);
				}
			}
			else if (usernotes != null && usernotes.length() > 0) {
				location2.setNotes(usernotes);
				session.saveOrUpdate(location2);
			}
			else {
				session.delete(location2);
			}
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}

	}

	public static void updateScheduleMultiple(String username, String dates,
			int locationID, String notes, String time, boolean tentative,
			boolean unavailable) {
		String[] dates2 = dates.split("-");
		String[] dates3;
		int year, month, id;
		for (int i = 0; i < dates2.length; i++) {
			dates3 = dates2[i].split(":");
			year = DateHelper.extractYearFromDisplay(dates3[0]);
			month = DateHelper.extractMonthFromDisplay(dates3[0]);
			id = DataPreparer.getScheduleID(year, month,
					Integer.parseInt(dates3[1]), username,
					Boolean.valueOf(dates3[2]).booleanValue());
			addOrUpdateSchedule(username, id, year, month,
					Integer.parseInt(dates3[1]), locationID, notes, "", time,
					tentative, Boolean.valueOf(dates3[2]).booleanValue(),
					unavailable, true);
		}
	}

	public static void addOrUpdateSchedule(String username, int id, int year,
			int month, int day, int locationID, String notes, String pieces,
			String time, boolean tentative, boolean holiday, boolean unavailable, boolean updateMultiple) {

		/* If updateMultiple is true and the Schedule already exists (id > 0), then we will only
		 * overwrite the following fields: locationID, notes, time, tentative, unavailable. Even in this
		 * case, notes and time will only be overwritten if the parameters passed do not contain a zero-length
		 * String.
		 * 
		 * Control for this alternate is through the boolean alternatePath, avoiding double condition checks
		 * in several places.
		 */
		Transaction txn = null;
		boolean alternatePath = false;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Schedules schedule;
			if (id > 0) {
				schedule = (Schedules) session.get(Schedules.class,
						new Integer(id));
				if (updateMultiple == true) {
					alternatePath = true;
				}
			} else {
				schedule = new Schedules();
			}
			Organists organist = (Organists) session.get(Organists.class,
					username);
			schedule.setOrganists(organist);
			schedule.setYear(new Integer(year));
			schedule.setMonth(new Integer(month));
			schedule.setDay(new Integer(day));
			if (unavailable) {
				locationID = 999999;
			}
			Location location = (Location) session.get(Location.class,
					new Integer(locationID));
			schedule.setLocation(location);
			if (alternatePath) {
				if (notes.length() > 0) {
					schedule.setNotes(notes);
				}
				if (time.length() > 0) {
					schedule.setService_time(time);
				}
			} else {
				schedule.setNotes(notes);
				schedule.setPlayed(pieces);
				schedule.setService_time(time);
			}
			schedule.setTentative(new Boolean(tentative));
			schedule.setHoliday(new Boolean(holiday));
			schedule.setUnavailable(new Boolean(unavailable));
			session.saveOrUpdate(schedule);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void deleteSchedule(int id) {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Schedules schedule = (Schedules) session.get(Schedules.class,
					new Integer(id));
			session.delete(schedule);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static String updateMyInfo2(HttpSession sess, String username,
			String fullname, String info, String travel, String wedfun,
			String availsat, String availsun, String showincal,
			String activeuser) {

		Transaction txn = null;
		try {
			Organists organist = DataPreparer.getOrganist(username);
			organist.loadFromParameters(fullname, info, travel, wedfun,
					activeuser, availsun, availsat, showincal);
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			session.update(organist);
			txn.commit();
			if (sess != null)
				sess.setAttribute("organist", organist);
			else
				System.out.println("HTTPSESSION NULL");
		} catch (Exception e) {
			txn.rollback();
			return "System error: information NOT updated.";
		} finally {
			SessionFactory.closeSession();
		}
		return "Information updated";
	}

	public static void updateMyInfo(OrganistForm oForm) {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Organists organist = new Organists();
			organist.loadFromOrganistForm(oForm);
			session.update(organist);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			txn.rollback();
			System.out.println(e);
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void addOrUpdateUser(String username, String nickname, String fullname, String comments) {
		
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Organists organist = (Organists) session.get(Organists.class, username);
			if (organist == null) {
				organist = new Organists();
				organist.setUsername(username);
				organist.setNickname(nickname);
				organist.setFullname(fullname);
				organist.setDetails("");
				organist.setTravel("");
				organist.setWedfun("");
				organist.setAvailable(Boolean.FALSE);
				organist.setAvailsat(Boolean.FALSE);
				organist.setAvailsun(Boolean.TRUE);
				organist.setShowincal(Boolean.FALSE);
				session.save(organist);
				Users user = new Users();
				user.setUsername(username);
				user.setPassword(EncodeDecode.passwordEncode(NEW_PASSWORD));
				user.setLastlogin(new Date(0l));
				user.setComments(comments);
				session.save(user);
			}
			else {
				organist.setNickname(nickname);
				organist.setFullname(fullname);
				session.update(organist);
				Users user = (Users) session.get(Users.class, username);
				user.setComments(comments);
				session.update(user);
			}
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}
	
	public static void addUser(UserForm form) {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Organists organist = new Organists();
			organist.setUsername(form.getUsername());
			organist.setNickname(form.getNickname());
			organist.setFullname(form.getFullname());
			organist.setAvailable(Boolean.FALSE);
			organist.setAvailsat(Boolean.FALSE);
			organist.setAvailsun(Boolean.TRUE);
			organist.setShowincal(Boolean.TRUE);
			session.save(organist);
			Users user = new Users();
			user.setUsername(form.getUsername());
			user.setPassword(EncodeDecode.passwordEncode(NEW_PASSWORD));
			user.setLastlogin(new Date(0l));
			session.save(user);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void updateUser(UserForm form) {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Organists organist = (Organists) session.get(Organists.class,
					form.getOldusername());
			organist.setNickname(form.getNickname());
			organist.setFullname(form.getFullname());
			session.update(organist);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void deleteUser(String username) {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Organists organist = (Organists) session.get(Organists.class,
					username);
			session.delete(organist);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void updateLastLogin(String username) {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Criteria criteria = session.createCriteria(Users.class);
			criteria.add(Restrictions.eq("username", username));
			Users user = (Users) criteria.uniqueResult();
			user.setLastlogin(new Date());
			if (user.getStatus().intValue() != Users.ACTIVE) {
				user.setStatus(new Integer(Users.ACTIVE));
			}
			session.save(user);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void resetUserPassword(String username) {

		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Users user = (Users) session.get(Users.class, username);
			user.setPassword(EncodeDecode.passwordEncode(NEW_PASSWORD));
			session.update(user);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}
	
	public static void setUserActive(String username) {
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Users user = (Users) session.get(Users.class, username);
			user.setActive();
			session.update(user);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}
	
	public static void setUserInactive(String username) {
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Users user = (Users) session.get(Users.class, username);
			user.setInactive();
			session.update(user);
			Organists organist = (Organists) session.get(Organists.class,
					username);
			organist.setShowincal(Boolean.FALSE);
			session.update(organist);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}
	
	public static void setUserLocked(String username) {
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Users user = (Users) session.get(Users.class, username);
			user.setLocked();
			session.update(user);
			Organists organist = (Organists) session.get(Organists.class,
					username);
			organist.setShowincal(Boolean.FALSE);
			organist.setAvailable(Boolean.FALSE);
			session.update(organist);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}
	
	public static void addHolidays(int month1, int day1, int month2, int day2, int month3, int day3, int month4, int day4, int year) {
		Holidays holiday;
		
		holiday = new Holidays(year, month1, day1, "Ash Wednesday");
		addHoliday(holiday);
		
		holiday = new Holidays(year, month2, day2, "Maundy Thursday");
		addHoliday(holiday);
		
		holiday = new Holidays(year, month3, day3, "Good Friday");
		addHoliday(holiday);
		
		holiday = new Holidays(year, month4, day4, "Easter Vigil");
		addHoliday(holiday);
		
		holiday = new Holidays(year, Calendar.DECEMBER, 24, "Christmas Eve");
		addHoliday(holiday);
		
		// do not Christmas Day if it falls on a Sunday
		Calendar cal = Calendar.getInstance();
		cal.set(year, Calendar.DECEMBER, 25);
		if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			holiday = new Holidays(year, Calendar.DECEMBER, 25, "Christmas Day");
			addHoliday(holiday);
		}
		
		// add Thanksgiving Eve and Day
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 4);
		holiday = new Holidays(year, Calendar.NOVEMBER, cal.get(Calendar.DATE) - 1, "Thanksgiving Eve");
		addHoliday(holiday);
		holiday = new Holidays(year, Calendar.NOVEMBER, cal.get(Calendar.DATE), "Thanksgiving Day");
		addHoliday(holiday);
		
	}
	
	private static void addHoliday(Holidays holiday) {
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			session.save(holiday);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}
	
	public static void setTentativeOff(String username, int year,
			int month, int day, boolean holiday) {

		Schedules schedule = null;
		Transaction txn = null;
		try {
			Session session = SessionFactory.currentSession();
			txn = session.beginTransaction();
			Criteria criteria = session.createCriteria(Schedules.class);
			criteria.setFetchMode("location", FetchMode.JOIN);
			criteria.add(Restrictions.eq("year", new Integer(year)));
			criteria.add(Restrictions.eq("month", new Integer(month)));
			criteria.add(Restrictions.eq("day", new Integer(day)));
			criteria.add(Restrictions.eq("organists.username", username));
			criteria.add(Restrictions.eq("holiday", Boolean.valueOf(holiday)));
			schedule = (Schedules) criteria.uniqueResult();
			schedule.setTentative(Boolean.FALSE);
			session.saveOrUpdate(schedule);
			txn.commit();
		} catch (Exception e) {
			// TODO logging
			System.out.println(e);
			txn.rollback();
		} finally {
			SessionFactory.closeSession();
		}
	}

	
}

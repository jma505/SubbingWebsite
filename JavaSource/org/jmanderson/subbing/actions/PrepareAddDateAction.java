package org.jmanderson.subbing.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jmanderson.subbing.DataPreparer;
import org.jmanderson.subbing.DateHelper;
import org.jmanderson.subbing.forms.DateForm;
import org.jmanderson.subbing.hibernate.Schedules;
import org.jmanderson.subbing.hibernate.Users;

public class PrepareAddDateAction extends org.apache.struts.action.Action {

	// Global Forwards
	public static final String GLOBAL_FORWARD_home = "home";
	public static final String GLOBAL_FORWARD_login = "login";
	public static final String GLOBAL_FORWARD_logout = "logout";
	public static final String GLOBAL_FORWARD_organist_info = "organist_info";
	public static final String GLOBAL_FORWARD_updateBasicInfo = "updateBasicInfo";

	// Local Forwards
	public static final String FORWARD_success = "success";

	public PrepareAddDateAction() {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = (Users) request.getSession().getAttribute("user");
		if (user == null) {
			return mapping.findForward(GLOBAL_FORWARD_home);
		}

		List locations = DataPreparer.getLocationsAsList(user.getUsername());
		request.getSession().setAttribute("locations", locations);
		String monthString = request.getParameter("month");
		String dayString = request.getParameter("day");
		boolean holiday = Boolean.parseBoolean(request.getParameter("holiday"));
		int month = DateHelper.extractMonthFromDisplay(monthString);
		int year = DateHelper.extractYearFromDisplay(monthString);
		int day = Integer.parseInt(dayString);
		DateForm dform = (DateForm) form;
		dform.setMonth(month);
		dform.setYear(year);
		dform.setDay(day);
		dform.setUsername(user.getUsername());
		Schedules schedule = DataPreparer.getSchedule(year, month, day, user.getUsername(), holiday);
		dform.setLocationid(schedule.getLocationId());
		dform.setDateNotes(schedule.getNotes());
		dform.setPlayed(schedule.getPlayed());
		Boolean b = schedule.getTentative();
		if (b != null) {
			dform.setTentative(b.booleanValue());
		}
		else {
			dform.setTentative(false);
		}
		dform.setHoliday(holiday);
		dform.setId(schedule.getId().intValue());
		return mapping.findForward(FORWARD_success);
	}

}
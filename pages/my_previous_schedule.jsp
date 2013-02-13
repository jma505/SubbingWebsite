<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ page import="org.jmanderson.subbing.DataUpdater" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div id="warning2"></div>
<p />

<textarea id="xslmyprevsched" style="display: none;">
<c:import url="/XML/myschedule2.xsl" />
</textarea>
<textarea id="xsllocpieces2" style="display: none;">
<c:import url="/XML/locationpieces.xsl" />
</textarea>

<script type="text/javascript">
  var reloadMyPreviousSchedule = function() {
	  <%String myUsername = ((Users) request.getSession().getAttribute("user")).getUsername();%>
	  DataPreparer.getOrganistScheduleAsXML(true, "<%=myUsername%>", displayMyPreviousSchedule);
	}
	var displayMyPreviousSchedule = function(xmlIn) {
		var xslt = xmlParse(dojo.byId('xslmyprevsched').value);
		var xmlt = xmlParse(xmlIn);
		var html = xsltProcess(xmlt, xslt);
		dojo.byId('myPreviousSchedule').innerHTML = html;
	}
	var checkCalendar2 = function() {
		var showInCal;
		var myUsername = "<%=myUsername%>";
		DataPreparer.getMyShowInCal(myUsername, { async:false, callback:function(str) {
			if (str == "false") {
				dojo.byId('warning2').innerHTML = "<center><font color='red' size='+1'><i>Your availability is not visible from the public &quot;Calendar&quot; tab.<br />To correct this, go to the &quot;[My Info]&quot; tab and check the &quot;Show in Calendar&quot; checkbox.</i></font></center>";
			}
			else {
				dojo.byId('warning2').innerHTML = " ";
			}
		}});
	}
	var showLocationPieces2 = function(locID) {
		DataPreparer.getLocationDisplay(locID, {callback:function(str) { dojo.byId('piecesLocation2').innerHTML = str;}});
		var myUsername = "<%=myUsername%>";
		DataPreparer.getLocationPiecesAsXml(myUsername, locID, {callback:function(xmlIn) {
			var xslt = xmlParse(dojo.byId('xsllocpieces2').value);
			var xmlt = xmlParse(xmlIn);
			var html = xsltProcess(xmlt, xslt);
			dojo.byId('locationPieces2').innerHTML = html;
		}});
		dijit.byId("piecesPlayed2").show();
	}
	var showDateDialog2 = function(month, day, holiday) {
		var m, y, u, t, id;
		dojo.byId("ddDay2").innerHTML = day;
		dojo.byId("ddHoliday2").innerHTML = holiday;
		DateHelper.extractMonthFromDisplay(month, {async:false, callback:function(str) {m=str;}});
		DateHelper.extractYearFromDisplay(month, {async:false, callback:function(str) {y=str;}});
		dojo.byId("ddM2").innerHTML = m;
		dojo.byId("ddY2").innerHTML = y;
		var ddd = "<b>Schedule for " + day + " " + month + "</b><p/>";
		dojo.byId("ddDate2").innerHTML = ddd;
		DataPreparer.getLocationsForForm("<%=myUsername%>", "locationList2", {async:false, callback:function(str) {
			dojo.byId("ddLocations2").innerHTML = str;
		}});
		DataPreparer.getScheduleID(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			id = str;
		}});
		dojo.byId("ddID2").innerHTML = id;
		if (id == 0) {
			dijit.byId("ddUnavailable2").set("checked",false);
			dijit.byId("ddTentative2").set("checked", false);
			dojo.byId("ddNotes2").value = "";
			dojo.byId("ddPieces2").value = "";
			dojo.byId("ddTime2").value = "";
		}
		else {
		DataPreparer.getScheduleLocationID(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dojo.byId("locationList2").value = str;
		}});
		DataPreparer.getScheduleUnavailable(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			u = str;
		}});
		var ubool = true;
		if (u == "false") {
			ubool = false;
		}
		dijit.byId("ddUnavailable2").set("checked", ubool);
		DataPreparer.getScheduleTentative(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			t = str;
		}});
		var tbool = true;
		if (t == "false") {
			tbool = false;
		}
		dijit.byId("ddTentative2").set("checked", tbool);
		DataPreparer.getScheduleNotes(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dojo.byId("ddNotes2").value = str;
		}});
		DataPreparer.getSchedulePieces(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dojo.byId("ddPieces2").value = str;
		}});
		DataPreparer.getScheduleTime(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dojo.byId("ddTime2").value = str;
		}});
		}
		dijit.byId("dateDialog2").show();
	}
	var submitDate2 = function() {
		var id = dojo.byId("ddID2").innerHTML;
		var year = dojo.byId("ddY2").innerHTML;
		var month = dojo.byId("ddM2").innerHTML;
		var day = dojo.byId("ddDay2").innerHTML;
		var holiday = dojo.byId("ddHoliday2").innerHTML;
		var locID = dojo.byId("locationList2").value;
		var notes = dojo.byId("ddNotes2").value;
		var pieces = dojo.byId("ddPieces2").value;
		var time = dojo.byId("ddTime2").value;
		var tentative = dijit.byId("ddTentative2").get("checked");
		var unavailable = dijit.byId("ddUnavailable2").get("checked");
		DataUpdater.addOrUpdateSchedule("<%=myUsername%>", id, year, month, day, locID, notes, pieces, time,
				tentative, holiday, unavailable, false, {async:false, callback:function(){
					reloadMyPreviousSchedule();
					reloadCalendar();
				}});
	}
	var deleteSchedule2 = function(id) {
		DataUpdater.deleteSchedule(id, {async:false, callback:function() {
			reloadMyPreviousSchedule();
			reloadCalendar();
		}});
	}
</script>
<p><center><font color="blue"><i>
  <b>&raquo;</b>&nbsp;&nbsp;Click on a date to update the schedule for that day and the pieces played<br/>
  <b>&raquo;</b>&nbsp;&nbsp;Hover on the <img src="images/checkmark3.gif" /> to view the pieces played that day<br/>
  <b>&raquo;</b>&nbsp;&nbsp;Click on a location name to see all pieces ever played at that location<br/>
  <b>&raquo;</b>&nbsp;&nbsp;Click the <img src="images/delete_sm.png" /> to delete that day&#39;s schedule<br/>
</i></font></center></p>

<div data-dojo-type="dijit.Dialog" id="dateDialog2" title="Edit Schedule" execute="submitDate2();">
<center><div id="ddDate2"></div></center>
<table>
<tr><td colspan="2"><hr/></td></tr><tr>
<th rowspan="3" align="right">Location:</th>
<td><div id="ddLocations2"></div></td>
</tr><tr><th align="center"><i>- OR -</i></th>
</tr><tr><td><input data-dojo-type="dijit.form.CheckBox" name="ddUnavailable2" id="ddUnavailable2">&nbsp;Check here to be UNAVAILABLE</td></tr>
<tr><td colspan="2"><hr/></td></tr><tr><th align="right">Time:</th><td><input data-dojo-type="dijit.form.TextBox" type="text" name="ddTime2" id="ddTime2"/></td></tr>
<tr>
			<th align="right" valign="top">
				Notes:
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text" name="ddNotes2"
					id="ddNotes2" style="width: 400px;">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'ddNotes2'">Use this field for any information you want to capture
					relating to the service, rehearsals and times, etc.</span>
					<div id="ddM2" style="display:none;"></div><div id="ddY2" style="display:none;"></div>
					<div id="ddID2" style="display:none;"></div><div id="ddDay2" style="display:none;"></div>
					<div id="ddHoliday2" style="display:none;"></div>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Pieces Played:
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text"
					name="ddPieces2" id="ddPieces2" style="width: 400px">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'ddPieces2'">Entering pieces played here allows you to take advantage
					of other functionality on this site such as seeing all the pieces you have played at this Location,
					what you played on any given date, etc.</span>
			</td>
		</tr>
		<tr><th align="right">Tentative?</th><td><input data-dojo-type="dijit.form.CheckBox" name="ddTentative2" id="ddTentative2"></td></tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit">
					<b>Update Schedule</b>
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('dateDialog2').hide();">
					<i>Cancel</i>
				</button>
			</td>
		</tr>
</table>
</div>
<div data-dojo-type="dijit.Dialog" id="piecesPlayed2"
	title="Pieces Played">
<center><em><div id="piecesLocation2"></div></em></center>
<div id="locationPieces2"></div>	
<center>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('piecesPlayed2').hide();">
					<b>Close Window</b>
				</button></center>
</div>
	

 
<div id="myPreviousSchedule"></div>

<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ page import="org.jmanderson.subbing.DataUpdater" %>
<%@ page import="org.jmanderson.subbing.DateHelper" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div id="warning1"></div>
<p />
<textarea id="xslmysched" style="display: none;">
<c:import url="/XML/myschedule.xsl" />
</textarea>
<textarea id="xsllocpieces" style="display: none;">
<c:import url="/XML/locationpieces.xsl" />
</textarea>

<script type="text/javascript">
  var reloadMySchedule = function() {
	  <%String myUsername = ((Users) request.getSession().getAttribute("user")).getUsername(); %>
		DataPreparer.getOrganistScheduleAsXML(false, "<%=myUsername%>", displayMySchedule);
	}
	var displayMySchedule = function(xmlIn) {
		$('#myCurrentSchedule').xslt(xmlIn, $('#xslmysched').text());
	}
	var checkCalendar = function() {
		var showInCal;
		var myUsername = "<%=myUsername%>";
		DataPreparer.getMyShowInCal(myUsername, { async:false, callback:function(str) {
			require(["dojo/dom"], function(dom) {
			if (str == "false") {
				dom.byId('warning1').innerHTML = "<center><font color='red' size='+1'><i>Your availability is not visible from the public &quot;Calendar&quot; tab.<br />To correct this, go to the &quot;[My Info]&quot; tab and check the &quot;Show in Calendar&quot; checkbox.</i></font></center>";
			}
			else {
				dom.byId('warning1').innerHTML = " ";
			}
			});
		}});
	}
	var showLocationPieces = function(locID) {
		DataPreparer.getLocationDisplay(locID, {callback:function(str) { require(["dojo/dom"], function(dom) { dom.byId('piecesLocation').innerHTML = str; }); }});
		var myUsername = "<%=myUsername%>";
		DataPreparer.getLocationPiecesAsXml(myUsername, locID, {callback:function(xmlIn) {
			$('#locationPieces').xslt(xmlIn, $('#xsllocpieces').text());
		}});
		dijit.byId("piecesPlayed").show();
	}
	var showDateDialog = function(month, day, holiday) {
		require(["dojo/dom"], function(dom) {
		var m, y, u, t, id;
		dom.byId("ddDay").innerHTML = day;
		dom.byId("ddHoliday").innerHTML = holiday;
		DateHelper.extractMonthFromDisplay(month, {async:false, callback:function(str) {m=str;}});
		DateHelper.extractYearFromDisplay(month, {async:false, callback:function(str) {y=str;}});
		dom.byId("ddM").innerHTML = m;
		dom.byId("ddY").innerHTML = y;
		var ddd = "<b>Schedule for " + day + " " + month + "</b><p/>";
		dom.byId("ddDate").innerHTML = ddd;
		DataPreparer.getLocationsForForm("<%=myUsername%>", "locationList", {async:false, callback:function(str) {
			dom.byId("ddLocations").innerHTML = str;
		}});
		DataPreparer.getScheduleID(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			id = str;
		}});
		dom.byId("ddID").innerHTML = id;
		if (id == 0) {
			dijit.byId("ddUnavailable").set("checked",false);
			dijit.byId("ddTentative").set("checked", false);
			dom.byId("ddNotes").value = "";
			dom.byId("ddPieces").value = "";
			dom.byId("ddTime").value = "";
		}
		else {
		DataPreparer.getScheduleLocationID(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dom.byId("locationList").value = str;
		}});
		DataPreparer.getScheduleUnavailable(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			u = str;
		}});
		var ubool = true;
		if (u == "false") {
			ubool = false;
		}
		dijit.byId("ddUnavailable").set("checked", ubool);
		DataPreparer.getScheduleTentative(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			t = str;
		}});
		var tbool = true;
		if (t == "false") {
			tbool = false;
		}
		dijit.byId("ddTentative").set("checked", tbool);
		DataPreparer.getScheduleNotes(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dom.byId("ddNotes").value = str;
		}});
		DataPreparer.getSchedulePieces(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dom.byId("ddPieces").value = str;
		}});
		DataPreparer.getScheduleTime(y, m, day, "<%=myUsername%>", holiday, {async:false, callback:function(str) {
			dom.byId("ddTime").value = str;
		}});
		}
		dijit.byId("dateDialog").show();
		});
	}
	var submitDate = function() {
		require(["dojo/dom"], function(dom) {
		var id = dom.byId("ddID").innerHTML;
		var year = dom.byId("ddY").innerHTML;
		var month = dom.byId("ddM").innerHTML;
		var day = dom.byId("ddDay").innerHTML;
		var holiday = dom.byId("ddHoliday").innerHTML;
		var locID = dom.byId("locationList").value;
		var notes = dom.byId("ddNotes").value;
		var pieces = dom.byId("ddPieces").value;
		var time = dom.byId("ddTime").value;
		var tentative = dijit.byId("ddTentative").get("checked");
		var unavailable = dijit.byId("ddUnavailable").get("checked");
		DataUpdater.addOrUpdateSchedule("<%=myUsername%>", id, year, month, day, locID, notes, pieces, time,
				tentative, holiday, unavailable, false, {async:false, callback:function(){
					reloadMySchedule();
					reloadCalendar();
				}});
		});
	}
	var deleteSchedule = function(id) {
		DataUpdater.deleteSchedule(id, {async:false, callback:function() {
			reloadMySchedule();
			reloadCalendar();
		}});
	}
	var doBulkUpdate = function() {
		require(["dojo/dom"], function(dom) {
		var nl = dojo.query(".bulkSelect:checked");
		if (nl.length > 0) {
			var dates = "";
			var processed = false;
			DataPreparer.getLocationsForForm("<%=myUsername%>", "locationList3", {async:false, callback:function(str) {
				dom.byId("ddLocations3").innerHTML = str;
			}});
			nl.forEach(function(node, index, nodelist) {
				dates = dates + node.getAttribute("value") + "-";
				if (!processed) {
					processed = true;
					var spl = node.getAttribute("value").split(":");
					var month = spl[0];
					var id, u, t, y, m;
					DateHelper.extractMonthFromDisplay(month, {async:false, callback:function(str) {m=str;}});
					DateHelper.extractYearFromDisplay(month, {async:false, callback:function(str) {y=str;}});
					var d = spl[1];
					var h = spl[2];
					DataPreparer.getScheduleID(y, m, d, "<%=myUsername%>", h, {async:false, callback:function(str) {
						id = str;
					}});
					if (id == 0) {
						dijit.byId("ddUnavailable3").set("checked",false);
						dijit.byId("ddTentative3").set("checked", false);
						dom.byId("ddNotes3").value = "";
						dom.byId("ddTime3").value = "";
					}
					else {
						DataPreparer.getScheduleLocationID(y, m, d, "<%=myUsername%>", h, {async:false, callback:function(str) {
							dom.byId("locationList3").value = str;
						}});
						DataPreparer.getScheduleUnavailable(y, m, d, "<%=myUsername%>", h, {async:false, callback:function(str) {
							u = str;
						}});
						var ubool = true;
						if (u == "false") {
							ubool = false;
						}
						dijit.byId("ddUnavailable3").set("checked", ubool);
						DataPreparer.getScheduleTentative(y, m, d, "<%=myUsername%>", h, {async:false, callback:function(str) {
							t = str;
						}});
						var tbool = true;
						if (t == "false") {
							tbool = false;
						}
						dijit.byId("ddTentative3").set("checked", tbool);
						DataPreparer.getScheduleNotes(y, m, d, "<%=myUsername%>", h, {async:false, callback:function(str) {
							dom.byId("ddNotes3").value = str;
						}});
						DataPreparer.getScheduleTime(y, m, d, "<%=myUsername%>", h, {async:false, callback:function(str) {
							dom.byId("ddTime3").value = str;
						}});
					}	
				};
			});
			dom.byId("ddDates").innerHTML = dates;
			dijit.byId("dateDialogMultiple").show();
		}
		});
	}
	var submitDateMultiple = function() {
		require(["dojo/dom"], function(dom) {
		var dates = dom.byId("ddDates").innerHTML;
		var locID = dom.byId("locationList3").value;
		var notes = dom.byId("ddNotes3").value;
		var time = dom.byId("ddTime3").value;
		var tentative = dijit.byId("ddTentative3").get("checked");
		var unavailable = dijit.byId("ddUnavailable3").get("checked");
		DataUpdater.updateScheduleMultiple("<%=myUsername%>", dates, locID, notes, time,
				tentative, unavailable, {async:false, callback:function(){
					reloadMySchedule();
					reloadCalendar();
				}});
		});
	}
	var toggleTentativeOff = function(month, day, holiday) {
		var m, y;
		DateHelper.extractMonthFromDisplay(month, {async:false, callback:function(str) {m=str;}});
		DateHelper.extractYearFromDisplay(month, {async:false, callback:function(str) {y=str;}});
		DataUpdater.setTentativeOff("<%=myUsername%>", y, m, day, holiday, {async:false, callback:function() {
			reloadMySchedule();
			reloadCalendar();
		}});
	}
</script>
<p><center><font color="blue"><i>
  <b>&raquo;</b>&nbsp;&nbsp;Click on a date to update the schedule or the pieces played for that day<br/>
  <b>&raquo;</b>&nbsp;&nbsp;Hover on the <img src="images/checkmark3.gif" /> to view the pieces played that day<br/>
  <b>&raquo;</b>&nbsp;&nbsp;Click on a location name to see all pieces ever played at that location<br/>
  <b>&raquo;</b>&nbsp;&nbsp;Click the <img src="images/delete_sm.png" /> to delete that day&#39;s schedule<br/>
  <b>&raquo;</b>&nbsp;&nbsp;Click the <img src="images/qm_sm.jpg" /> to remove the &quot;Tentative&quot; flag<br />
  <b>&raquo;</b>&nbsp;&nbsp;Checkmark multiple dates and click the &quot;Update Multiples&quot; button to schedule several dates at once<br />
</i></font></p><p>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="doBulkUpdate();">
					Update Multiples
				</button></center></p>

<div data-dojo-type="dijit.Dialog" id="dateDialog" title="Edit Schedule" execute="submitDate();">
<center><div id="ddDate"></div></center>
<table>
<tr><td colspan="2"><hr/></td></tr><tr>
<th rowspan="3" align="right">Location:</th>
<td><div id="ddLocations"></div></td>
</tr><tr><th align="center"><i>- OR -</i></th>
</tr><tr><td><input data-dojo-type="dijit.form.CheckBox" name="ddUnavailable" id="ddUnavailable">&nbsp;Check here to be UNAVAILABLE</td></tr>
<tr><td colspan="2"><hr/></td></tr><tr><th align="right">Time:</th><td><input data-dojo-type="dijit.form.TextBox" type="text" name="ddTime" id="ddTime"/></td></tr>
<tr>
			<th align="right" valign="top">
				Notes:
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text" name="ddNotes"
					id="ddNotes" style="width: 400px;">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'ddNotes'">Use this field for any information you want to capture
					relating to the service, rehearsals and times, etc.</span>
					<div id="ddM" style="display:none;"></div><div id="ddY" style="display:none;"></div>
					<div id="ddID" style="display:none;"></div><div id="ddDay" style="display:none;"></div>
					<div id="ddHoliday" style="display:none;"></div>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Pieces Played:
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text"
					name="ddPieces" id="ddPieces" style="width: 400px">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'ddPieces'">Entering pieces played here allows you to take advantage
					of other functionality on this site such as seeing all the pieces you have played at this Location,
					what you played on any given date, etc.</span>
			</td>
		</tr>
		<tr><th align="right">Tentative?</th><td><input data-dojo-type="dijit.form.CheckBox" name="ddTentative" id="ddTentative"></td></tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit">
					<b>Update Schedule</b>
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('dateDialog').hide();">
					<i>Cancel</i>
				</button>
			</td>
		</tr>
</table>
</div>

<div data-dojo-type="dijit.Dialog" id="dateDialogMultiple" title="Edit Schedule" execute="submitDateMultiple();">
<center><b>Editing Multiple Dates</b></center><div id="ddDates" style="display:none;"></div>
<table>
<tr><td colspan="2"><hr/></td></tr><tr>
<th rowspan="3" align="right">Location:</th>
<td><div id="ddLocations3"></div></td>
</tr><tr><th align="center"><i>- OR -</i></th>
</tr><tr><td><input data-dojo-type="dijit.form.CheckBox" name="ddUnavailable3" id="ddUnavailable3">&nbsp;Check here to be UNAVAILABLE</td></tr>
<tr><td colspan="2"><hr/></td></tr><tr><th align="right">Time:</th><td><input data-dojo-type="dijit.form.TextBox" type="text" name="ddTime3" id="ddTime3"/></td></tr>
<tr>
			<th align="right" valign="top">
				Notes:
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text" name="ddNotes3"
					id="ddNotes3" style="width: 400px;">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'ddNotes3'">Use this field for any information you want to capture
					relating to the service, rehearsals and times, etc.</span>
			</td>
		</tr>
		<tr><th align="right">Tentative?</th><td><input data-dojo-type="dijit.form.CheckBox" name="ddTentative3" id="ddTentative3"></td></tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit">
					<b>Update Schedules</b>
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="reloadMySchedule();dijit.byId('dateDialogMultiple').hide();">
					<i>Cancel</i>
				</button>
			</td>
		</tr>
</table>
</div>

<div data-dojo-type="dijit.Dialog" id="piecesPlayed"
	title="Pieces Played">
<center><em><div id="piecesLocation"></div></em></center>
<div id="locationPieces"></div>	
<center>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('piecesPlayed').hide();">
					<b>Close Window</b>
				</button></center>
</div>
	
	 
<div id="myCurrentSchedule"></div>


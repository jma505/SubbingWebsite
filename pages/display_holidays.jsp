<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ page import="org.jmanderson.subbing.DataUpdater" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<script type="text/javascript">
	function reloadHolidays() {
		DataPreparer.getHolidaysListDisplay({async:false, callback:function(str) {
			dojo.byId("allHolidays").innerHTML = str;
		}});
	}
	function submitHoliday() {
		var date1 = dijit.byId("date1").value;
		var easter = new Date(date1);
		var ashwednesday = dojo.date.add(easter, "day", -46);
		var m1 = ashwednesday.getMonth();
		var d1 = ashwednesday.getDate();
		var y = ashwednesday.getFullYear();
		var maundythursday = dojo.date.add(easter, "day", -3);
		var m2 = maundythursday.getMonth();
		var d2 = maundythursday.getDate();
		var goodfriday = dojo.date.add(maundythursday, "day", 1);
		var m3 = goodfriday.getMonth();
		var d3 = goodfriday.getDate();
		var eastervigil = dojo.date.add(goodfriday, "day", 1);
		var m4 = eastervigil.getMonth();
		var d4 = eastervigil.getDate();
		DataUpdater.addHolidays(m1, d1, m2, d2, m3, d3, m4, d4, y, {async:false, callback:function() {
			reloadHolidays();
		}});
	}
</script>
<div data-dojo-type="dijit.Dialog" id="formSelectEaster"
	title="Select Easter Sunday" execute="submitHoliday()">

	<table rules="none" border="1" cellspacing="5" cellpadding="5"
		width="500">
		<tr>
			<th align="right">
				Select Easter Sunday:
			</th>
			<td>
				<input type="text" name="date1" id="date1"
					data-dojo-type="dijit.form.DateTextBox">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit">
					Add Holidays
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('formSelectEaster').hide();">
					Cancel
				</button>
			</td>
		</tr>
	</table>
</div>


<table align="center" border="0" cellspacing="5" cellpadding="5">
	<tr>
		<td align="center">
			<button data-dojo-type="dijit.form.Button" type="button"
				onClick="dijit.byId('formSelectEaster').show();">
				Add a Year&#39;s Holidays
			</button>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<div id="allHolidays"></div>
		</td>
	</tr>
</table>

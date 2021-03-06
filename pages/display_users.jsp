<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ page import="org.jmanderson.subbing.DataUpdater" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<textarea id="xslusers" style="display: none;">
<c:import url="/XML/allusers.xsl" />
</textarea>

<script type="text/javascript">
  var reloadUsers = function() {
	  DataPreparer.getAllUsersAsXML(displayUsers);
	}
	var displayUsers = function(xmlIn) {
		$('#allUsers').xslt(xmlIn, $('#xslusers').text());
	}
	var moveUserToRight = function(username, usershortname, userfullname, comments) {
		require(["dojo/dom"], function(dom) {
		dom.byId('userName').value = username;
		dom.byId('userShortname').value = usershortname;
		dom.byId('userFullname').value = userfullname;
		dom.byId('userComments').value = comments;
		dijit.byId("formChangeUser").show();
		});
	}
	var clearUserForm = function() {
		require(["dojo/dom"], function(dom) {
		dom.byId('userName').value = "";
		dom.byId('userShortname').value = "";
		dom.byId('userFullname').value = "";
		dom.byId('userComments').value = "";
		});
	}
	var submitUser = function() {
		require(["dojo/dom"], function(dom) {
		var username = dom.byId('userName').value;
		var usershortname = dom.byId('userShortname').value;
		var userfullname = dom.byId('userFullname').value;
		var usercomments = dom.byId('userComments').value;
		DataUpdater.addOrUpdateUser(username, usershortname, userfullname, usercomments, {
					async : false,
					callback : function(str) {
						reloadUsers();
					}
				});
		clearUserForm();
		});
	}
	var deleteUser = function(username) {
		DataUpdater.deleteUser(username, {async:false, callback:function() {reloadUsers();}});
	}
	var resetPassword = function(username) {
		DataUpdater.resetUserPassword(username);
	}
	var setActive = function(username) {
		DataUpdater.setUserActive(username, {async:false, callback:function() {reloadUsers();}});
	}
	var setInactive = function(username) {
		DataUpdater.setUserInactive(username, {async:false, callback:function() {reloadUsers();}});
	}
	var setLocked = function(username) {
		DataUpdater.setUserLocked(username, {async:false, callback:function() {reloadUsers();}});
	}
</script>
<div data-dojo-type="dijit.Dialog" id="formChangeUser"
	title="Add or Update a User" execute="submitUser()">

	<table rules="none" border="1" cellspacing="5" cellpadding="5"
		width="500">
		<tr>
			<th align="right">
				Username:
			</th>
			<td>
				<input size="20" type="text" required="true"
					data-dojo-type="dijit.form.ValidationTextBox"
					missingMessage="Username is required" name="userName" id="userName">
			</td>
		</tr>
		<tr>
			<th align="right">
				Short Name:
			</th>
			<td>
				<input size="20" type="text" required="true"
					data-dojo-type="dijit.form.ValidationTextBox"
					missingMessage="Short Name is required" name="userShortname" id="userShortname">
			</td>
		</tr>
		<tr>
			<th align="right">
				Full Name:
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" name="userFullname" id="userFullname">
			</td>
		</tr>
		<tr>
			<th align="right">
				Comments:
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" name="userComments" id="userComments">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit">
					Add/Update User
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('formChangeUser').hide(); clearUserForm();">
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
				onClick="dijit.byId('formChangeUser').show();">
				Add a User
			</button>
		</td>
	</tr>
	<tr><td align="center">
	<%
	Calendar cal1 = Calendar.getInstance();
	cal1.add(Calendar.MONTH, -3);
	Calendar cal2 = Calendar.getInstance();
	cal2.add(Calendar.MONTH, -6);
	Calendar cal3 = Calendar.getInstance();
	cal3.add(Calendar.YEAR, -1);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	StringBuffer sb = new StringBuffer();
	sb.append("Inactivate = ").append(sdf.format(cal1.getTime())).append("  ||  ");
	sb.append("Lock = ").append(sdf.format(cal2.getTime())).append("  ||  ");
	sb.append("Delete = ").append(sdf.format(cal3.getTime()));
	out.print(sb.toString());
	%>
	</td></tr>
	<tr>
		<td valign="top">
			<p/>
			<div id="allUsers"></div>
		</td>
	</tr>
	<tr>
		<td align="center">
			<button data-dojo-type="dijit.form.Button" type="button"
				onClick="dijit.byId('formChangeUser').show();">
				Add a User
			</button>
		</td>
	</tr>
</table>

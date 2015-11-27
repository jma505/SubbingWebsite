<%@ page import="org.jmanderson.subbing.hibernate.Organists"%>
<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ page import="org.jmanderson.subbing.DataUpdater"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>


<textarea id="xslme" style="display: none;">
<c:import url="/XML/myinfo2.xsl" />
</textarea>
<textarea id="xslmyemail" style="display: none;">
<c:import url="/XML/allemails.xsl" />
</textarea>
<textarea id="xslmyphones" style="display: none;">
<c:import url="/XML/allphones.xsl" />
</textarea>

<script type="text/javascript">
  var reloadMe = function() {
	  <%String myUsername = ((Users) request.getSession().getAttribute("user")).getUsername();%>
	  DataPreparer.getMyXml("<%=myUsername%>", displayMe);
  }
  var displayMe = function(xmlIn) {
	  $('#me').xslt(xmlIn, $('#xslme').text());
  }
  var reloadEmail = function() {
	  DataPreparer.getMyXml("<%=myUsername%>", displayMyEmail);
  }
  var displayMyEmail = function(xmlIn) {
	  $('#myemails').xslt(xmlIn, $('#xslmyemail').text());
  }
  var reloadPhone = function() {
	  DataPreparer.getMyXml("<%=myUsername%>", displayMyPhone);
  }
  var displayMyPhone = function(xmlIn) {
	  $('#myphones').xslt(xmlIn, $('#xslmyphones').text());
  }
  var deleteEmail = function(id) {
	  DataUpdater.deleteEmail(id, reloadEmail);
  }
  var moveEmailToRight = function(em, pr, id) {
	  require(["dojo/dom"], function(dom) {
	  dom.byId('emailID').innerHTML = id;
	  dom.byId('emailAddress').value = em;
	  dijit.byId('emailPreferred').set("value", pr);
	  });
  }
  var clearEmailAddUpdate = function() {
	  require(["dojo/dom"], function(dom) {
	  dom.byId('emailID').innerHTML = "";
	  dom.byId('emailAddress').value = "";
	  dijit.byId('emailPreferred').set("value", false);
	  });
  }
  var addUpdateEmail = function() {
	  require(["dojo/dom"], function(dom) {
	  var emId = dom.byId('emailID').innerHTML;
	  if (emId == "") {
		  emId = 0;
	  }
	  var emAddr = dom.byId('emailAddress').value;
	  var emPref = dijit.byId('emailPreferred').get("value");
	  if (emPref != false) {
		  emPref = true;
	  }
	  DataUpdater.addOrUpdateEmail2("<%=myUsername%>", emId, emAddr, emPref, reloadEmail);
	  clearEmailAddUpdate();
	  });
  }
  var deletePhone = function(id) {
	  DataUpdater.deletePhone(id, reloadPhone);
  }
  var movePhoneToRight = function(ac, pp, pe, pt, id) {
	  require(["dojo/dom"], function(dom) {
	  dom.byId('phoneID').innerHTML = id;
	  dom.byId('phoneAC').value = ac;
	  dom.byId('phonePP').value = pp;
	  dom.byId('phonePE').value = pe;
	  dom.byId('phonePT').value = pt;
	  });
  }
  var clearPhoneAddUpdate = function() {
	  require(["dojo/dom"], function(dom) {
	  dom.byId('phoneID').innerHTML = "";
	  dom.byId('phoneAC').value = "";
	  dom.byId('phonePP').value = "";
	  dom.byId('phonePE').value = "";
	  dom.byId('phonePT').value = "";
	  });
  }
  var addUpdatePhone = function() {
	  require(["dojo/dom"], function(dom) {
	  var phId = dom.byId('phoneID').innerHTML;
	  if (phId == "") {
		  phId = 0;
	  }
	  var phAc = dom.byId('phoneAC').value;
	  if (phAc == "") {
		  phAc = 0;
	  }
	  var phPp = dom.byId('phonePP').value;
	  if (phPp == "") {
		  phPp = 0;
	  }
	  var phPe = dom.byId('phonePE').value;
	  if (phPe == "") {
		  phPe = 0;
	  }
	  var phPt = dom.byId('phonePT').value;
	  DataUpdater.addOrUpdatePhone2("<%=myUsername%>", phId, phAc, phPp, phPe, phPt, reloadPhone);
	  clearPhoneAddUpdate();
	  });
  }
  </script>
  

<div data-dojo-type="dijit.Dialog" id="formChangeEmail"
	title="Change My Email">

<font color="blue"><i>To <b>ADD</b> a new Email Address, fill in the information on the right below and press the <b>&quot;Add/Update Email&quot;</b>
button.<p>
To <b>UPDATE</b> an Email Address, click the address on the left below, correct the information on the right, and press the
<b>&quot;Add/Update Email&quot; </b>button.<p>
To <b>DELETE</b> an Email Address, click the <img src="images/delete_sm.png"/> on the left below.</i></font><p>
<hr><p>

	<table border="0" align="center"><tr><td>
	<div id="myemails">
		Uh oh
	</div><p /></td><td><center><b>Add/Update</b></center><p />
	<table rules="none" border="1" cellspacing="5" cellpadding="5" width="500"><tr>
	<th align="right">Email:</td><td><div id="emailID" style="display:none"></div>
	<input data-dojo-type="dijit.form.TextBox" id="emailAddress" width="50em"></td></tr>
	<tr><th align="right">Preferred?</td><td>
	<input type="checkbox" data-dojo-type="dijit.form.CheckBox" id="emailPreferred">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'emailPreferred'">Check this box to
					have this email address marked as &quot;Preferred&quot;</span></td></tr>
	<tr><td align="center">
	<button data-dojo-type="dijit.form.Button" type="Button" onClick="clearEmailAddUpdate();">Clear</button></td>
	<td align="center">
	<button data-dojo-type="dijit.form.Button" type="Button" onClick="addUpdateEmail();">Add/Update Email</button></td></tr>
	</table>
	</td></tr></table>
	<center>
		<button data-dojo-type="dijit.form.Button" type="button"
			onClick="dijit.byId('formChangeEmail').hide(); reloadMe();">
			Close Window
		</button>
	</center>
</div>
<div data-dojo-type="dijit.Dialog" id="formChangePhone"
	title="Change My Phone">

<font color="blue"><i>To <b>ADD</b> a new Phone Number, fill in the information on the right below and press the <b>&quot;Add/Update Phone&quot;</b>
button.<p>
To <b>UPDATE</b> a Phone Number, click the number on the left below, correct the information on the right, and press the
<b>&quot;Add/Update Phone&quot; </b>button.<p>
To <b>DELETE</b> a Phone Number, click the <img src="images/delete_sm.png"/> on the left below.</i></font><p>
<hr><p>

	<table border="0" align="center"><tr><td>
	<div id="myphones">
		Uh oh
	</div><p /></td><td><center><b>Add/Update</b></center><p />
	<table rules="none" border="1" cellspacing="5" cellpadding="5" width="500"><tr>
	<th align="right">Area Code:</th><td><div id="phoneID" style="display:none"></div>
	<input data-dojo-type="dijit.form.TextBox" id="phoneAC" size="3" maxlength="3"></td></tr>
	<tr><th align="right">Phone Number:</th><td>
	<input data-dojo-type="dijit.form.TextBox" id="phonePP" size="7" maxlength="7">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'phonePP'">Format: &quot;9999999&quot;</span></td></tr>
	<tr><th align="right">Extension:</th><td>
	<input data-dojo-type="dijit.form.TextBox" id="phonePE" size="6" maxlength="6"></td></tr>
	<tr><th align="right">Type:</th><td>
	<input data-dojo-type="dijit.form.TextBox" id="phonePT">
				<span data-dojo-type="dijit.Tooltip" data-dojo-props="connectId: 'phonePT'">Optional description (e.g. Home, Work)</span></td></tr>
	<tr><td align="center">
	<button data-dojo-type="dijit.form.Button" type="Button" onClick="clearPhoneAddUpdate();">Clear</button></td>
	<td align="center">
	<button data-dojo-type="dijit.form.Button" type="Button" onClick="addUpdatePhone();">Add/Update Phone</button></td></tr>
	</table>
	</td></tr></table>
	<center>
		<button data-dojo-type="dijit.form.Button" type="button"
			onClick="dijit.byId('formChangePhone').hide(); reloadMe();">
			Close Window
		</button>
	</center>
</div>
<div data-dojo-type="dijit.Dialog" id="formChangePwd"
	title="Change My Password" execute="submitPassword();">
	<table>
		<tr>
			<th align="right">
				Current Password
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" type="password"
					name="oldpwd" id="oldpwd">
			</td>
		</tr>
		<tr>
			<th align="right">
				New Password
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" type="password"
					name="newpwd" id="newpwd" onKeyUp="validateNewPwd();">
			</td>
		</tr>
		<tr>
			<th align="right">
				Confirm New Password
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" type="password"
					name="newpwd2" id="newpwd2"
					onKeyUp="validateNewPwd(); return false;">
			</td>
		</tr>
		<tr>
			<th />
			<td>
				<span id="newpwdmsg"></span>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit"
					id="pwdSubmit">
					Submit
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('formChangePwd').hide();">
					Cancel
				</button>
			</td>
		</tr>
	</table>
</div>
<div data-dojo-type="dijit.Dialog" id="formBasicInfo"
	title="Update Basic Information" execute="submitBasicInfo()">
	<table>
		<tr>
			<th align="right" valign="top">
				Full Name
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" type="text"
					name="fullname" id="fullname">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'fullname'">Visitors to the site
					will see this name on the Organists Information tab</span>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Information
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text" name="info"
					id="info" style="width: 500px;">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'info'">Anything entered here
					will be displayed in the &quot;Information&quot; field on the
					Organists Information tab</span>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Travel Area
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text"
					name="travel" id="travel">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'travel'">Enter any restrictions
					on travel area (e.g. 50 miles from Springfield, MA)</span>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Weddings/Funerals
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text"
					name="wedfun" id="wedfun">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'wedfun'">Leave <strong>blank</strong>
					to be listed as unavailable. Any text entered here will be
					displayed with your information under &quot;Weddings/Funerals&quot;</span>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Available Saturdays
			</th>
			<td>
				<input data-dojo-type="dijit.form.CheckBox" name="availsat"
					id="availsat">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'availsat'">Check this box to
					indicate you are available for Saturday services</span>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Available Sundays
			</th>
			<td>
				<input data-dojo-type="dijit.form.CheckBox" name="availsun"
					id="availsun">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'availsun'">Check this box to
					indicate you are available for Sunday services</span>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Show in Organists list
			</th>
			<td>
				<input data-dojo-type="dijit.form.CheckBox" name="activeuser"
					id="activeuser">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'activeuser'">Check this box to
					have your name and information show on the Organists Information
					tab</span>
			</td>
		</tr>
		<tr>
			<th align="right" valign="top">
				Show in Calendar
			</th>
			<td>
				<input data-dojo-type="dijit.form.CheckBox" name="showincal"
					id="showincal">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'showincal'">Check this box to
					have your name show in the Calendar on dates you are available</span>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit">
					<b>Update My Information</b>
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('formBasicInfo').hide();">
					<i>Cancel</i>
				</button>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
    var initBasicInfo = function() {
    	require(["dojo/dom"], function(dom) {
		var myUsername = "<%=myUsername%>";
		var mfn, details, travel, wedfun, asat, asun, asic, aau;
		DataPreparer.getMyName(myUsername, { async:false, callback:function(str) {mfn = str;}});
		dom.byId("fullname").value = mfn;
		DataPreparer.getMyDetails(myUsername, { async:false, callback:function(str) {details = str;}});
		dom.byId("info").value = details;
		DataPreparer.getMyTravel(myUsername, { async:false, callback:function(str) {travel = str;}});
		dom.byId("travel").value = travel;
		DataPreparer.getMyWedFun(myUsername, { async:false, callback:function(str) {wedfun = str;}});
		dom.byId("wedfun").value = wedfun;
		DataPreparer.getMyAvailSat(myUsername, { async:false, callback:function(str) {asat = str;}});
		var asatbool = true;
		if (asat == "false") {
			asatbool = false;
		}
		dijit.byId("availsat").set("checked", asatbool);
		DataPreparer.getMyAvailSun(myUsername, { async:false, callback:function(str) {asun = str;}});
		var asunbool = true;
		if (asun == "false") {
			asunbool = false;
		}
		dijit.byId("availsun").set("checked", asunbool);
		DataPreparer.getMyShowInCal(myUsername, { async:false, callback:function(str) {asic = str;}});
		var asicbool = true;
		if (asic == "false") {
			asicbool = false;
		}
		dijit.byId("showincal").set("checked", asicbool);
		DataPreparer.getMyIsActiveUser(myUsername, { async:false, callback:function(str) {aau = str;}});
		var aaubool = true;
		if (aau == "false") {
			aaubool = false;
		}
		dijit.byId("activeuser").set("checked", aaubool);
    	});
	}
	var showBasicInfo = function() {
		initBasicInfo();
		dijit.byId("formBasicInfo").show();
	}
	var showEmail = function() {
		dijit.byId("formChangeEmail").show();
	}
	var showPhone = function() {
		dijit.byId("formChangePhone").show();
	}
	var showChangePwd = function() {
	    dijit.byId("formChangePwd").show();
	}
	var validateNewPwd = function() {
		require(["dojo/dom"], function(dom) {
	  var badpwd = "<font color=red><em>New Passwords do not Match</em></font>";
	  var pass1 = dom.byId("newpwd");
	  var pass2 = dom.byId("newpwd2");
	  var message = dom.byId("newpwdmsg");
	  if (pass1.value != pass2.value) { message.innerHTML = badpwd; }
	  else { message.innerHTML = ""; }
		});
	}
	var submitPassword = function() {
		require(["dojo/dom"], function(dom) {
	<%String username = ((Users) request.getSession()
					.getAttribute("user")).getUsername();%>
	  var username = "<%=username%>";
	  var oldpwd = dom.byId("oldpwd").value;
	  var newpwd1 = dom.byId("newpwd").value;
	  var newpwd2 = dom.byId("newpwd2").value;
	  DataUpdater.updatePassword2(username, oldpwd, newpwd1, { callback:function(str) { alert(str);}});
	  dom.byId("oldpwd").value = "";
	  dom.byId("newpwd").value = "";
	  dom.byId("newpwd2").value = "";
		});
	}
	var submitBasicInfo = function() {
		require(["dojo/dom"], function(dom) {
		<%username = ((Users) request.getSession().getAttribute("user"))
					.getUsername();%>
		var username = "<%=username%>";
		var fullname = dom.byId("fullname").value;
		var info = dom.byId("info").value;
		var travel = dom.byId("travel").value;
		var wedfun = dom.byId("wedfun").value;
		var asat = dijit.byId("availsat").get("checked");
		var asun = dijit.byId("availsun").get("checked");
		var showincal = dijit.byId("showincal").get("checked");
		var activeuser = dijit.byId("activeuser").get("checked");
		DataUpdater.updateMyInfo2(username, fullname, info, travel, wedfun,
				asat, asun, showincal, activeuser, {
					async : false,
					callback : function(str) {
						reloadMe();
						reloadMyPreviousSchedule();
						reloadMySchedule();
						checkCalendar();
						checkCalendar2();
					}
				});
		});
	}
</script>

<table align="center" border="0" cellspacing="5" cellpadding="5">
	<tr>
		<td>
			<table>
				<tr>
					<td>
						<button onClick="showBasicInfo();"
							data-dojo-type="dijit.form.Button" type="button">
							Update Basic Information
						</button>
					</td>
				</tr>
				<tr>
					<td>
						<button onClick="showPhone();" data-dojo-type="dijit.form.Button"
							type="button">
							Update Phone Number(s)
						</button>
					</td>
				</tr>
				<tr>
					<td>
						<button onClick="showEmail();" data-dojo-type="dijit.form.Button"
							type="button">
							Update Email Address(es)
						</button>
					</td>
				</tr>
				<tr>
					<td>
						<button onClick="showChangePwd();"
							data-dojo-type="dijit.form.Button" type="button">
							Change My Password
						</button>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<table border="0" cellspacing="5" cellpadding="5">
				<tr>
					<td valign="top">
						<div id="me">
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
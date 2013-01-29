<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="javascript">
function openwin(win, winname) {
  window.open(win, winname, config='toolbar=0,menubar=0,location=0,scrollbars=1,status=0');
}
</script>

<div id="navcontainer">
<ul id="navlist">
<li>
<html:link action="/showMySchedule.do?period=current">My Current Schedule</html:link>
</li>
<li>
<html:link action="/showMySchedule.do?period=previous">My Previous Schedule</html:link>
</li>
<li>
<html:link action="/getAllLocations.do">View/Edit Locations</html:link>
</li>
<li>
<html:link action="/getMyInfo.do">View/Update My Info</html:link>
</li>
<li>
<html:link action="/changePassword.do">Change my Password</html:link>
</li>
<li id="active"><a href="#" id="current">
Everything I've Played</a>
</li>
<li>
<html:link href="javascript:openwin('pages/help/sub_help.jsp', 'help');">Help</html:link>
</li>
<li>
<a href="mailto:john.anderson@bigfoot.com">Contact the Webmaster</a>
</li>
<logic:match value="jma" name="user" property="username">
<li>
<html:link action="/getAllUsers.do">Admin</html:link>
</li>
</logic:match>
<li>
<html:link forward="logout">Logoff</html:link>
</li>
</ul>
</div>



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
<li>
<html:link action="/getMyPieces.do">Everything I've Played</html:link>
</li>
<li id="active">
<a href="#" id="current">
Admin</a>
</li>
<li>
<html:link forward="logout">Logoff</html:link>
</li>
</ul>
</div>


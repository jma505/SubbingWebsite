<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<script language="javascript">
function openwin(win, winname) {
  window.open(win, winname, config='toolbar=0,menubar=0,location=0,scrollbars=1,status=0');
}
</script>

<div id="navcontainer">
<ul id="navlist">
<li>
<html:link forward="home">Schedules</html:link>
</li>
<li id="active"><a href="#" id="current">
Organist Info</a>
</li>
<li>
<html:link action="/organistInfoWF.do">Organists for Weddings and Funerals</html:link>
</li>
<li>
<html:link href="javascript:openwin('pages/news.jsp', 'news');">News (updated 11/24/06)</html:link>
</li>
<li>
<html:link action="/fees.do">Suggested Fees</html:link>
</li>
<li>
<html:link action="/checklist.do">Checklist</html:link>
</li>
<li>
<html:link href="javascript:openwin('pages/help/public_help.jsp', 'help');">Help</html:link>
</li>
<li>
<a href="mailto:john.anderson@bigfoot.com">Contact the Webmaster</a>
</li>
</ul>
</div>
<font size="-2">
<html:form action="processLogin.do" onsubmit="return validateLoginForm(this);">
<table border="0" align="center">
<tr>
<th align="left">Username:</th></tr>
<tr><td><html:text property="username" size="15"/></td>
</tr>
<tr>
<th align="left">Password:</th></tr>
<tr><td><html:password property="password" redisplay="false" size="15"/></td>
</tr>
<tr>
<td align="right"><html:submit value="Login" /> </td>
</table>
</html:form>
</font>

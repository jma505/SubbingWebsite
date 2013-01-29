<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

	<html:javascript formName="passwordForm"/>
<link rel="StyleSheet" type="text/css" href="/subbing/CSS/style1.css">

<center>

<html:errors/>

<p></p>
<html:form action="/changeMyPassword.do" focus="oldPassword">
<table border="0" cellspacing="5" cellpadding="5">
<tr>
	<th align="right">Old Password:</th>
	<td align="left"><html:password property="oldPassword" redisplay="false"/> </td>
</tr>
<tr>
	<th align="right">New Password:</th>
	<td align="left"><html:password property="newPassword1" redisplay="false"/></td>
</tr>
<tr>
	<th align="right">Confirm new Password:</th>
	<td align="left"><html:password property="newPassword2" redisplay="false"/></td>
</tr>
<tr>
	<td colspan="2"><p/></td>
</tr>
<tr>
	<td align="center"><html:submit value="Submit" /></td>
	<td align="center"><html:cancel onclick="bCancel=true;"/></td>
</tr>
</table>
</html:form>
</center>

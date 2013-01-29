<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:hidden property="id" />
<html:hidden property="oldusername"/>
<center><b>Add/Update a User</b></center><p>
<table border="1" cellspacing="5" cellpadding="5" rules="none">
<tr><th align="right">Username:</th>
<td align="left"><html:text property="username" size="20" maxlength="20" /></td>
</tr>
<tr><th align="right">Short Name:</th>
<td align="left"><html:text property="nickname" size="20" maxlength="20"/></td>
</tr>
<tr><th align="right">Full Name (optional):</th>
<td align="left"><html:text property="fullname" size="40" maxlength="40"/></td>
</tr>
</table>

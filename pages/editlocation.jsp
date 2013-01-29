<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<center>
<html:errors/>
<html:form action="/updateLocation.do" focus="name" >
<html:hidden property="id"/>
<table border="0" cellspacing="2" cellpadding="2">
<tr><th align="right">Name:</th>
<td align="left"><html:text property="name" /></td>
</tr>
<tr><th align="right">Address:</th>
<td align="left"><html:text property="address"/></td>
</tr>
<tr><th align="right">City:</th>
<td align="left"><html:text property="city"/></td>
</tr>
<tr><th align="right">State:</th>
<td align="left"><html:text property="state" size="2"/></td>
</tr>
<tr><th align="right" valign="top">Public Notes:</th>
<td align="left"><html:textarea property="notes" rows="5"/></td>
</tr>
<tr><th align="right" valign="top">Private Notes:</th>
<td align="left"><html:textarea property="usernotes" rows="5"/></td>
</tr>
<tr>
<td align="center"><html:submit value="Update Location" /></td>
<td align="center"><html:submit value="Cancel"/></td>
</tr>
</table>
</html:form>
</center>

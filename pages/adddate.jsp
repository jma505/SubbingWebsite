<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<center>
<p><html:errors/></p>
<html:form action="/addDate.do">
Add or Update Schedule for <html:text property="displayDate" readonly="true"/><br>
<table border="0" cellpadding="5" cellspacing="5">
<tr><td>
<html:hidden property="id"/>
<html:hidden property="year"/>
<html:hidden property="month"/>
<html:hidden property="day"/>
<html:hidden property="username"/>
<html:hidden property="holiday"/>
<table border="0" cellspacing="2" cellpadding="2">
<tr><th align="right">Location:</th>
<td align="left">
<html:select property="locationid" size="1">
<html:options collection="locations" labelProperty="displayName" property="id"/>
<html:option key="id" value="0">(Choose below or Add a new location--&gt;)</html:option>
</html:select>
</td>
</tr>
<tr><th align="right">Notes:</th>
<td align="left"><html:textarea property="dateNotes" rows="10"/></td>
</tr>
<tr><th align="right">Pieces Played:</th>
<td align="left"><html:textarea property="played" rows="5"/></td>
</tr>
<tr><th align="right"><span title="Selecting this will indicate that you might be available on this date.">Tentative</span></th>
<td align="left"><html:checkbox property="tentative"/></td>
</tr>
<tr><p></p></tr>
<tr>
<td align="center"><html:cancel>Cancel</html:cancel></td>
<td align="center"><html:submit value="Add or Update Schedule" /></td>
</tr>
</table>
</td>
<td>
<jsp:include page="/pages/addlocation.jsp"/>
</td>
</tr>
</table>
</html:form>

</center>

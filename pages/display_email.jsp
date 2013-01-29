<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<textarea id="xslmyemail" style="display: none;">
<c:import url="/XML/allemails.xsl" />
</textarea>
<script type="text/javascript">
  function reloadEmail() {
	  <%String myUsername = ((Users) request.getSession().getAttribute(
					"user")).getUsername();%>
	  DataPreparer.getMyXml("<%=myUsername%>", displayMyEmail);
  }
  function displayMyEmail(xmlIn) {
	  alert("in displayMyEmail(): " + xmlIn);
	  alert("XSL = " + dojo.byId('xslmyemail').value);
	  var xslt = xmlParse(dojo.byId('xslmyemail').value);
	  alert("xslt = " + xslt);
	  var xmlt = xmlParse(xmlIn);
	  alert("xmlt = " + xmlt);
	  var html = xsltProcess(xmlt, xslt);
	  alert("html = " + html);
	  dojo.byId('myemails').innerHTML = html;
  }
  </script>

<center>
To add a new Email Address, fill in the information on the right and press the <b>&quot;Add/Update Email&quot;</b>
button.<p>
To update an Email Address, click the address, correct the information on the right, and press the
<b>&quot;Add/Update Email&quot; </b>button.<p>
</center>

<table border="0" cellspacing="5" cellpadding="5">
<tr>
<td valign="top">
<div id="myemails">We have a problem, Houston!</div>
&nbsp;
</td>
<td valign="top">
<html:form action="/addEmail.do" focus="name">
<jsp:include page="/pages/addemail.jsp"/>
<center><html:submit value="Add/Update Email" /></center>

</html:form>
</td>
</tr>
</table>
<script type="text/javascript">
	require([ "dojo/domReady!" ], function() {
		reloadEmail();
	});
</script>
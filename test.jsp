<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<%@ page import="org.jmanderson.subbing.DataPreparer" %>
<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head>

<title>Pioneer Valley Substitute Organists</title>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.7.3/dijit/themes/claro/claro.css">

<script type="text/javascript" src="/subbing/scripts/basescripts.js">
</script>
<script src="/subbing/dwr/interface/DataPreparer.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/interface/DataUpdater.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/engine.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/util.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/ajaxslt/misc.js">
</script>
<script src="/subbing/ajaxslt/dom.js">
</script>
<script src="/subbing/ajaxslt/xslt.js">
</script>
<script src="/subbing/ajaxslt/xpath.js">
</script>

<script src="//ajax.googleapis.com/ajax/libs/dojo/1.7.3/dojo/dojo.js"
               data-dojo-config="async: true, parseOnLoad: true"></script>
<script type="text/javascript">
  require(["dojo/parser","dijit/layout/TabContainer","dijit/layout/ContentPane","dijit/layout/LinkPane","dijit/form/Form",
           "dijit/layout/BorderContainer","dijit/form/TextBox","dijit/form/Button","dijit/Dialog","dijit/form/DateTextBox",
           "dijit/form/CheckBox","dijit/Tooltip","dijit/form/Textarea","dojo/dom"]);
</script>

</head>
<body class="claro">
<script type="text/javascript">
  function reloadEmail() {
	  <%String myUsername2 = ((Users) request.getSession().getAttribute("user")).getUsername();%>
	  DataPreparer.getMyXml("<%=myUsername2%>", displayMyEmail);
  }
  function displayMyEmail(xmlIn) {
	  alert("in displayMyEmail(): " + xmlIn);
	  alert("XSL = " + dojo.byId('xslmyemail').value);
	  var xslt = xmlParse(dojo.byId('xslmyemail').value);
	  var xmlt = xmlParse(xmlIn);
	  var html = xsltProcess(xmlt, xslt);
	  alert("html = " + html);
	  dojo.byId('myemails').innerHTML = html;
  }
  function showEmail() {
		dijit.byId("formChangeEmail").show();
	}
	</script>

<div data-dojo-type="dijit.Dialog" id="formChangeEmail"
	title="Change My Email">
	<div id="myemails">
		Uh oh
	</div>
	<center>
		<button data-dojo-type="dijit.form.Button" type="button"
			onClick="dijit.byId('formChangeEmail').hide();">
			Done
		</button>
	</center>
</div>
<button onClick="showEmail();" data-dojo-type="dijit.form.Button"
							type="button">
							Update Email Address(es)
						</button>
<script type="text/javascript">
	require([ "dojo/domReady!" ], function() {
		reloadEmail();
	});
</script>
</body>
</html>

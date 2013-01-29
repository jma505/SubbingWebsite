<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>

<title>Pioneer Valley Substitute Organists</title>

<style type="text/css">
  @import "dojo/dijit/themes/soria/soria.css";
  @import "dojo/dojo/resources/dojo.css";
  @import "CSS/style1.css";
  html, body, #mainDiv {
     width: 100%;
     height: 100%;
     overflow: hidden;
     padding: 0 0 0 0;
     margin: 10 10 10 10;
     }
  .dijitTabPaneWrapper {
     padding: 10px 10px 10px 10px;
  }
</style>

</head>
<body class="soria">

<html:javascript formName="loginForm"/>
<center>
<h2>Member's Login:</h2>
<logic:messagesPresent>
<font color="red"><UL>
<html:errors/>
</UL></font>
</logic:messagesPresent>

</center>
<p/>
<html:form action="processLogin.do" focus="username" onsubmit="return validateLoginForm(this);">
<table border="0" cellspacing="5" cellpadding="5" align="center">
<tr>
<th align="right">Username:</th>
<td align="left"><html:text property="username" /></td>
</tr>
<tr>
<th align="right">Password:</th>
<td align="left"><html:password property="password" redisplay="false" /></td>
</tr>
</table>
<center>
<html:submit value="Login" />
<p>
<html:cancel onclick="bCancel=true;"/>
</center>
</html:form>
</body>
</html>


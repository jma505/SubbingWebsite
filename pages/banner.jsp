<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<table width="100%" border="0">
<tr>
<td rowspan="2">&nbsp;&nbsp;
<img src="images/pvsologo1.gif" border="0"/>
</td></tr>
<logic:present name="user">
  <tr><td>
  <form action="servlet/LogoutServlet" method="get">
    <button type="submit" data-dojo-type="dijit.form.Button">Click to Log Off</button>
  </form>
  </td><td><a href="mailto:bassooner@gmail.com">Email the Webmaster</a><br><font color="#82accd" size="-3">v2.4.0</font></td></tr>
</logic:present>
<logic:notPresent name="user">
  <tr>
    <td>
    <div data-dojo-type="dijit.form.Form" encType="multipart/form-data" method="get" action="servlet/LoginServlet">
        <script type="dojo/method" data-dojo-event="onSubmit">
			if(this.validate()) {
				return true;
			} else {
				return false;
			}
		</script>
		<table border="0" align="left">
		<tr>
          <th align="left"><small>Username:</small></th>
          <th align="left"><small>Password:</small></th>
        </tr>
        <tr>
          <td><input type="text" required="true" name="username" id="username" size="15"
            data-dojo-type="dijit.form.ValidationTextBox"/></td>
          <td><input type="password" required="true" name="password" id="password" redisplay="false" size="15"
            data-dojo-type="dijit.form.ValidationTextBox" missingMessage="Password is Required"/></td>
          <td><button type="submit" data-dojo-type="dijit.form.Button">Click to login</button> </td>
        </tr>
        </table>
        </div>
    </td><td><a href="mailto:bassooner@gmail.com">Email the Webmaster</a><br><font color="82accd" size="-3">v2.4.0</font></td>
  </tr>
</logic:notPresent>
</table>


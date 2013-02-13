<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ page import="org.jmanderson.subbing.DataUpdater" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<textarea id="xslloc" style="display: none;">
<c:import url="/XML/allLocations.xsl" />
</textarea>

<script type="text/javascript">
    var reloadLocations = function() {
	  <%String myUsername = ((Users) request.getSession().getAttribute(
					"user")).getUsername();%>
	  DataPreparer.getLocationsAsXML("<%=myUsername%>", displayLocations);
	}
	var displayLocations = function(xmlIn) {
		var xslt = xmlParse(dojo.byId('xslloc').value);
		var xmlt = xmlParse(xmlIn);
		var html = xsltProcess(xmlt, xslt);
		dojo.byId('allLocations').innerHTML = html;
	}
	var moveLocationToRight = function(id, name, address, city, state, zip, notes,
			usernotes) {
		dojo.byId('locID').innerHTML = id;
		dojo.byId('locName').value = name;
		dojo.byId('locAddress').value = address;
		dojo.byId('locCity').value = city;
		dojo.byId('locState').value = state;
		dojo.byId('locZIP').value = zip;
		dojo.byId('locNotes').value = notes;
		dojo.byId('locUsernotes').value = usernotes;
		dijit.byId("formChangeLocation").show();
	}
	var clearLocationForm = function() {
		dojo.byId('locID').innerHTML = "";
		dojo.byId('locName').value = "";
		dojo.byId('locAddress').value = "";
		dojo.byId('locCity').value = "";
		dojo.byId('locState').value = "";
		dojo.byId('locZIP').value = "";
		dojo.byId('locNotes').value = "";
		dojo.byId('locUsernotes').value = "";
	}
	var submitLocation = function() {
		var id = dojo.byId('locID').innerHTML;
		if (id == "") {
			id = 0;
		}
		var name = dojo.byId('locName').value;
		var address = dojo.byId('locAddress').value;
		var city = dojo.byId('locCity').value;
		var state = dojo.byId('locState').value;
		var zip = dojo.byId('locZIP').value;
		if (zip == "") {
			zip = 0;
		}
		var notes = dojo.byId('locNotes').value;
		var usernotes = dojo.byId('locUsernotes').value;
		var username = "<%=myUsername%>";
		DataUpdater.addOrUpdateLocation2(username, id, name, address, city,
				state, zip, notes, usernotes, {
					async : false,
					callback : function(str) {
						reloadLocations();
					}
				});
		clearLocationForm();
	}
</script>
<div data-dojo-type="dijit.Dialog" id="formChangeLocation"
	title="Add or Update a Location" execute="submitLocation()">

	<table rules="none" border="1" cellspacing="5" cellpadding="5"
		width="500">
		<tr>
			<th align="right">
				Name:
			</th>
			<td>
				<div id="locID" style="display: none"></div>
				<input size="80" type="text" required="true"
					data-dojo-type="dijit.form.ValidationTextBox"
					placeHolder="Church Name" style="width:350px"
					missingMessage="Location Name is required" name="locName" id="locName">
			</td>
		</tr>
		<tr>
			<th align="right">
				Address:
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" name="locAddress" id="locAddress"
				style="width:350px">
			</td>
		</tr>
		<tr>
			<th align="right">
				City:
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" name="locCity" id="locCity">
			</td>
		</tr>
		<tr>
			<th align="right">
				State:
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" name="locState" id="locState" size="2"
					maxlength="2" style="width:30px">
			</td>
		</tr>
		<tr>
			<th align="right">
				ZIP:
			</th>
			<td>
				<input data-dojo-type="dijit.form.TextBox" name="locZIP" id="locZIP" size="5"
					maxlength="5" style="width:80px">
			</td>
		</tr>
		<tr>
			<th align="right">
				Public Notes:
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text" name="locNotes"
					id="locNotes" style="width: 350px;">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'locNotes'">Any information entered in this field will be visible
					to everyone. Share things you feel would be useful to others.</span>
			</td>
		</tr>
		<tr>
			<th align="right">
				Private Notes:
			</th>
			<td>
				<input data-dojo-type="dijit.form.Textarea" type="text" name="locUsernotes"
					id="locUsernotes" style="width: 350px;">
				<span data-dojo-type="dijit.Tooltip"
					data-dojo-props="connectId:'locUsernotes'">Information entered in this field is private
					and visible only to you.</span>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button data-dojo-type="dijit.form.Button" type="submit">
					Submit
				</button>
				<button data-dojo-type="dijit.form.Button" type="button"
					onClick="dijit.byId('formChangeLocation').hide(); clearLocationForm();">
					Cancel
				</button>
			</td>
		</tr>
	</table>
</div>

<p>
	<font color="blue"><i>To <b>ADD</b> a new Location, click
			the button below and fill out the form which appears. <font
			color="red">Check this list <b>BEFORE</b> adding a new
				Location.</font>
		<p>
				To
				<b>UPDATE</b> a Location, click on the name, correct the information
				in the form which appears, and press the
				<b>&quot;Add/Update Location&quot; </b>button.
	</i>
	</font>
</p>


<table align="center" border="0" cellspacing="5" cellpadding="5">
	<tr>
		<td align="center">
			<button data-dojo-type="dijit.form.Button" type="button"
				onClick="dijit.byId('formChangeLocation').show();">
				Add a Location
			</button>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<div id="allLocations"></div>
		</td>
	</tr>
	<tr>
		<td align="center">
			<button data-dojo-type="dijit.form.Button" type="button"
				onClick="dijit.byId('formChangeLocation').show();">
				Add a Location
			</button>
		</td>
	</tr>
</table>

<%@ page import="org.jmanderson.subbing.DataPreparer" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<b><i><font size="+1" color="green">Green dates (Saturdays & Sundays)</font></i></b>
and <b><i><font size="+1" color="purple">Purple dates (Church Holidays)</font></i></b>
have at least one substitute potentially available -- their names can be viewed 
by resting the mouse pointer on the date.  <i>(Doing this on a <b><font color="purple">Purple</font></b> date will
also display the holiday name to help avoid confusion.)</i>
No substitutes are reported as available on
<b><font color="red" size="+1">Red</font></b>
dates.
<p>
	Parentheses around a substitute's name indicate a <em>tentative</em> commitment on that date.
<p>
	<em><font size="+1">Availability is never guaranteed.</font></em> You must contact the substitute to verify and reserve a date. 
	Contact information is available on the <i>"Organists Information"</i> tab.

	<textarea id="xslcal" style="display: none;">
	<c:import url="/XML/schedule2.dojo190.xsl" />
	</textarea>
	<script type="text/javascript">
	require(["dijit/Tooltip", "dojo/query!css2", "dojo/domReady!"], function(Tooltip){
	    new Tooltip({
	        connectId: "cal",
	        selector: "span",
	        getContent: function(matchedNode){
	            return matchedNode.getAttribute("tooltiptext");
	        }
	    });
	});
	function transformCalendar(xmlin) {
		require(["dojo/dom"], function(dom) {
		var xslCalendar = dom.byId('xslcal').value;
		var xslt = xmlParse(xslCalendar);
		  var xmlt = xmlParse(xmlin);
		  var html = xsltProcess(xmlt, xslt);
		  dom.byId('cal').innerHTML = html;
		});
	}
	function reloadCalendar() {
		DataPreparer.getAvailabilityAsXML(transformCalendar);
	}
	</script>
	<div id="cal"></div>

<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<script type="text/javascript">
	dojo.require("dijit.layout.BorderContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dojo.parser");
</script>

<textarea id="xsl" style="display: none;">
<c:import url="/XML/organist.xsl" />
</textarea>
<div data-dojo-type="BorderContainer" design="headline" style="width: 800px; height: 600px;">
	<div data-dojo-type="dijit.layout.ContentPane" region="top"
		style="height: 30px;" splitter="true">
		<i>Mouse over a name to view that person's details:</i>
	</div>
	<div data-dojo-type="dijit.layout.ContentPane" region="left" splitter="true"
		style="width: 200px;">
		<br />
		<%
			List names = DataPreparer.getNicknames();
			Iterator iter = names.iterator();

			out.print("<h3>");
out.print("<table border='0'><tr><td>");
String name;
for (int i = 0;i < names.size()/2; i++) {
  name = (String) iter.next();
  out.print("<a onMouseover='getOrganist(\"" + name + "\")'>" + name + "</a><br>");
}
out.print("</td><td>");
while (iter.hasNext()) {
  name = (String) iter.next();
  out.print("<a onMouseover='getOrganist(\"" + name + "\")'>" + name + "</a><br>");
  }
			out.print("</td></tr></table></h3>");
		%>
	</div>
	<div data-dojo-type="dijit.layout.ContentPane" region="center" id="organist">
		<br />
	</div>
	
</div>



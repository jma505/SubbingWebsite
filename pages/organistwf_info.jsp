<%@ page import="org.jmanderson.subbing.DataPreparer" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<textarea id="xsl" style="display: none;">
<c:import url="/XML/organist.xsl" />
</textarea>
<div id="instructions">
<i>Mouse over a name to view that person's details and contact information</i>
</div>
<table border="0" cellpadding="5" cellspacing="5" width="100%">
<tr><td align="left" valign="top" width="15%">
<%
  List names = DataPreparer.getNicknames();
  Iterator iter = names.iterator();
  
  out.print("<h3>");
  
  String name;
  int temp = names.size();
  temp = temp / 2;
  if (temp * 2 < names.size()) {
	  temp++;
  }
  for (int i = 0;i < temp; i++) {
    name = (String) iter.next();
    out.print("<a onMouseover='getOrganist(\"" + name + "\")'>" + name + "</a><br>");
  }
  out.print("</h3></td><td align=\"left\" valign=\"top\" width=\"15%\"><h3>");

  
  while (iter.hasNext()) {
     name = (String) iter.next();
    out.print("<a onMouseover='getOrganist(\"" + name + "\")'>" + name + "</a><br>");
  }
  
  out.print("</h3></td>");

 
%>
</td>
<td align="left" valign="top">
<p />
<p />
<p />
<p />
<div id="organist">
</div>
</td></tr></table>

<script type="text/javascript">
require(["dojo/dom"], function(dom) {
  dom.byId('organist').value = "";
});
</script>

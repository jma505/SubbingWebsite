<%@ page import="org.jmanderson.subbing.DataPreparer" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<textarea id="xsl" style="display: none;">
<c:import url="/XML/organist.xsl" />
</textarea>
<textarea id="xml" style="display: none;">
</textarea>
<div id="instructions">
<i>Mouse over a name to view that person's details:</i>
</div>
<table border="0" cellpadding="5" cellspacing="5" width="100%">
<tr><td align="left" valign="top">
<%
  List names = DataPreparer.getNicknames(false);
  Iterator iter = names.iterator();
  
  out.print("<h3>");
  while (iter.hasNext()) {
    String name = (String) iter.next();
    out.print("<a onMouseover='getOrganist(\"" + name + "\")'>" + name + "</a><br>");
  }
  out.print("</h3>");
%>
</td><td align="left" valign="top">
<p>
<div id="organist">
</div>
</td></tr></table>

<script>
  document.getElementById('organist').value = "";
</script>
<noscript>
<c:import url="/XML/organists.xsl" var="xslt"/>
<x:parse var="xml">
<%=DataPreparer.getOrganistsAsXML()%>
</x:parse>
<x:transform xml="${xml}" xslt="${xslt}"/>
</noscript>

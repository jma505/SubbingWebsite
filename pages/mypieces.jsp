<%@ page import="org.jmanderson.subbing.hibernate.Users" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<c:import url="/XML/mypieces.xsl" var="xslt_mp">
</c:import>
<x:parse var="xml_mp">
<%=org.jmanderson.subbing.DataPreparer.getMyPiecesAsXml(((Users) request.getSession().getAttribute("user")).getUsername())%>
</x:parse>
<x:transform xml="${xml_mp}" xslt="${xslt_mp}">
</x:transform>

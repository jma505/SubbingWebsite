<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>



<html:html locale="true">
<head>
	<title>Organist Availability</title>
</head>
<body>
<c:import url="/XML/xsl2.xsl" var="xslt">
</c:import>
<x:parse var="xml">
<%request.getAttribute("available");%>
</x:parse>
<x:transform xml="${xml}" xslt="${xslt}">
</x:transform>
</body>
</html:html>
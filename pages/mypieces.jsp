<%@ page import="org.jmanderson.subbing.hibernate.Users" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<input placeholder="Type any text here to search Pieces Played" id="search1" size="100" type="text"/>

<script>
$('#search1').keyup(function () {

    var valThis = this.value.toLowerCase();

    $('#searchtable tr').each(function () {
    	$row = $(this);
    	var played = $row.children("td:first").text();
        var textL = played.toLowerCase();
        (textL.indexOf(valThis) > -1) ? $row.show() : $row.hide();
    });

});
</script>
<c:import url="/XML/mypieces.xsl" var="xslt_mp">
</c:import>
<x:parse var="xml_mp">
<%=org.jmanderson.subbing.DataPreparer.getMyPiecesAsXml(((Users) request.getSession().getAttribute("user")).getUsername())%>
</x:parse>
<x:transform xml="${xml_mp}" xslt="${xslt_mp}">
</x:transform>

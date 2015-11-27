<%@ page import="org.jmanderson.subbing.hibernate.Users"%>
<%@ page import="org.jmanderson.subbing.DataPreparer"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<p />
<div id='container' style='height: 1000px;'>placeholder</div>

<script src="http://code.highcharts.com/highcharts.js"></script>

<script type="text/javascript">
  var reloadMyLocations = function() {
	  <%String myUsername = ((Users) request.getSession().getAttribute("user")).getUsername(); %>
		DataPreparer.getMyLocationsAsCSV("<%=myUsername%>", displayMyLocations);
	}
	var displayMyLocations = function(csv) {
		//$('#container').text(csv);
    //$('#container').highcharts({
    //    chart: {
    //        type: 'bar'
    //    },
    //    data: {
    //        csv: csv
    //    },
    //    title: {
    //        text: 'My Locations'
    //    },
    //    yAxis: {
    //        title: {
    //            text: 'Number of Times Visited'
    //        }
    //    },
    //    xAxis: {
    //    	type: 'category'
    //    }
    //});
    var options = {
    chart: {
        renderTo: 'container',
        type: 'bar'
    },
    title: {
        text: 'My Locations'
    },
    xAxis: {
        type: 'category'
    },
    yAxis: {
        title: {
            text: 'Number of Visits'
        }
    },
    series: []
};
    var lines = csv.split('\n');
    $('#container').css('height',200+lines.length*20);
    var save = "";
    var mydata = {name: 'Visits', data: [] };
    
    // Iterate over the lines and add categories or series
    $.each(lines, function(lineNo, line) {
    
        var items = line.split(',');
        
            $.each(items, function(itemNo, item) {
                if (itemNo == 0) {
                    save = item;
                } else {
                    mydata.data.push([save, parseFloat(item)]);
                }
            });
         
    });
    options.series.push(mydata);
   
    var chart = new Highcharts.Chart(options);
	}
	
	$(function() {
		reloadMyLocations();
	});
</script>


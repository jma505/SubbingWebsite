<%@ page import="org.jmanderson.subbing.DataPreparer" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico"/>

<title>Pioneer Valley Substitute Organists</title>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.8.3/dijit/themes/claro/claro.css">

<script type="text/javascript" src="/subbing/scripts/basescripts.js">
</script>
<script src="/subbing/dwr/interface/DataPreparer.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/interface/DataUpdater.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/interface/DateHelper.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/interface/ValidateUser.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/engine.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/dwr/util.js" language="javascript" type="text/javascript">
</script>
<script src="/subbing/ajaxslt/misc.js">
</script>
<script src="/subbing/ajaxslt/dom.js">
</script>
<script src="/subbing/ajaxslt/xslt.js">
</script>
<script src="/subbing/ajaxslt/xpath.js">
</script>

<style type="text/css">
  @import "CSS/style1.css";
  html, body, #mainDiv {
     width: 99%;
     height: 100%;
     overflow: hidden;
     padding: 0 0 0 0;
     margin: 0 5 10 5;
     }
  .dijitTabPaneWrapper {
     padding: 5px 30px 10px 5px;
  }
</style>
<script src="http://ajax.googleapis.com/ajax/libs/dojo/1.8.3/dojo/dojo.js"
               data-dojo-config="async: true, parseOnLoad: true"></script>
<script type="text/javascript">
  require(["dojo/parser","dijit/layout/TabContainer","dijit/layout/ContentPane","dijit/form/Form","dojo/_base/fx",
           "dijit/layout/BorderContainer","dijit/form/TextBox","dijit/form/Button","dijit/Dialog","dijit/form/DateTextBox","dojo/query",
           "dijit/form/CheckBox","dijit/form/Select","dijit/Tooltip","dijit/form/Textarea","dijit/form/ValidationTextBox","dojo/dom","dojo/date","dijit/layout/AccordionContainer"]);
  var un = "NLI";
  var un2 = "NLI";
</script>

</head>
<body class="claro" id="basebody" style="opacity:0;">
 <div data-dojo-type="dijit.layout.BorderContainer" gutters="false" id="mainDiv">
   <div data-dojo-type="dijit.layout.ContentPane" region="top">
   <c:import url="banner.jsp"/>
   </div>
  <div data-dojo-type="dijit.layout.ContentPane" region="center" id="mainContent">
  <div id="tabcontainer" data-dojo-type="dijit.layout.TabContainer" 
      style="width: 98%; height: 80%; left: 2%; right: 2%;">
    <div id="schedules" data-dojo-type="dijit.layout.ContentPane" title="Calendar" style="overflow: auto;" onShow="reloadCalendar();">
      <c:import url="schedule.jsp" />
    </div>
    <div data-dojo-type="dijit.layout.ContentPane" href="pages/help/public_help2.jsp" title="Read Me" style="overflow:auto">
    </div>
    <div data-dojo-type="dijit.layout.ContentPane" title="Organists Information"
      style="overflow:auto" href="pages/organistwf_info.jsp" executeScripts="true" refreshOnShow="true">
    </div>
    <div data-dojo-type="dijit.layout.ContentPane" href="pages/fees.jsp" title="Fees" style="overflow: auto">
    </div>
    <div data-dojo-type="dijit.layout.ContentPane" href="pages/checklist.jsp" title="Checklist" style="overflow:auto">
    </div>
    <div data-dojo-type="dijit.layout.ContentPane" href="pages/news.jsp" title="News (10/3/2012)"
       style="overflow:auto">
    </div>
    <logic:present name="user">
    <script type="text/javascript">un = "LI";</script>
    <style type="text/css">
      .dijitTabPaneWrapper {
        padding: 3px 14px 5px 5px;
      }
    </style>
    <div id="members" data-dojo-type="dijit.layout.TabContainer" title="Members' Area" selected="true" nested="false">
      <div data-dojo-type="dijit.layout.ContentPane" title="[Current 13 Months]">
        <c:import url="my_schedule.jsp" />
      </div>
      <div data-dojo-type="dijit.layout.ContentPane" title="[Previous 12 Months]">
        <c:import url="my_previous_schedule.jsp" />
      </div>
      <div data-dojo-type="dijit.layout.ContentPane" title="[Locations]">
        <c:import url="display_locations.jsp" />
      </div>
      <div data-dojo-type="dijit.layout.ContentPane" title="[My Pieces]">
        <c:import url="mypieces.jsp" />
      </div>
      <div data-dojo-type="dijit.layout.ContentPane" title="[My Info]">
      <c:import url="display_myinfo.jsp"/>
      </div>
      <div data-dojo-type="dijit.layout.ContentPane" title="[Help]">
        <c:import url="help/sub_help.jsp" />
      </div>
      <div data-dojo-type="dijit.layout.ContentPane" title="[Terms of Use]" href="pages/help/termsofuse.jsp">
      <c:import url="help/termsofuse.jsp" />
      </div>
      <div data-dojo-type="dijit.layout.ContentPane" href="pages/news2.jsp" title="[News (2/24/2013)]"
       style="overflow:auto">
    </div>
    <logic:match value="jma" name="user" property="username">
    	<script type="text/javascript">un2 = "LI";</script>
        <div data-dojo-type="dijit.layout.ContentPane" title="<i>[Users Admin]</i>">
        <c:import url="display_users.jsp" />
        </div>
        <div data-dojo-type="dijit.layout.ContentPane" title="<i>[Holidays Admin]</i>">
        <c:import url="display_holidays.jsp" />
        </div>
      </logic:match>
    </div>
    </logic:present>
  </div>
  </div>
</div>
<script>
    require(["dojo/_base/fx", "dojo/dom", "dojo/domReady!"], function(fx, dom) {
        var fadeTarget = dom.byId("basebody");
        fx.fadeOut({ node: fadeTarget }).play();
        setTimeout(function() {fx.fadeIn({ node: fadeTarget }).play();},1500);
		if (un.toString() == 'LI') {
			setTimeout(function() {
			reloadMySchedule();
			checkCalendar();
			reloadMyPreviousSchedule();
			checkCalendar2();
			reloadMe();
			reloadEmail();
			reloadPhone();
			reloadLocations();
			if (un2.toString() == 'LI') {
				reloadHolidays();
				reloadUsers();
			}
		},500);}
    });
</script></body>
</html>

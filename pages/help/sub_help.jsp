<div data-dojo-type="dijit/layout/AccordionContainer" >
	<div data-dojo-type="dijit/layout/ContentPane" title="Using Help" style="height:50px;">
	Click on a heading below to open that Help topic
	</div>
        <div data-dojo-type="dijit/layout/ContentPane" title="Current 13 Months & Previous 12 Months Tabs">
            This tab shows your current 13-month or previous 12-month schedule:<p>
<ul>
<li><b>Month/Date</b> - You will see Saturdays, Sundays, or both depending on your &quot;My Info&quot; tab settings.
Saturdays are BLUE, Sundays are BLACK, and Church Holidays (e.g. Christmas Eve, Ash Wednesday) are in RED.
Click on the Date to create or update your schedule information for that date.  See <b>&quot;Schedule - Update Date&quot;</b>
below for more information on updating your schedule.</li><br>
<li><b>Checkbox</b> - See the Help section <b>&quot;Schedule - Update Multiples&quot;</b> for information on using
this new feature.</li><br>
<li><b>Pieces?</b> - If there is a <img src="images/checkmark3.gif"/> in this column, you have entered &quot;Pieces Played&quot;
for this date.  Hover on the checkmark to view your pieces.</li><br>
<li><b>Time</b> - An optional free-form field where you can capture any time you like.  This could be the time of the service,
the time the building opens that morning, the time you need to leave home, etc.</li><br>
<li><b>Location</b> - The church where you are scheduled to play.  Clicking on it will display all of the past dates you
have played at this location <u>for which you have entered Pieces Played</u>.</li><br>
<li><b>Tentative?</b> - If there is a <img src="images/qm_sm.jpg"/> in this column, you have marked this
date as Tentative.  Your name will show up in parentheses on the list of available organists for this date, 
indicating that you might be available.  One particularly good use of Tentative is scheduling possible vacation dates
by checking &quot;Unavailable&quot; on the schedule form.  It can also be used to tentatively reserve a playing commitment,
in which case this reminds you that you may not have received confirmation for that date.  <b>Clicking on the image</b> will toggle the
Tentative flag off; this is a shortcut to save time.</li><br>
<li><b>Delete</b> - A <img src="images/delete_sm.png"/> appears in this column for every scheduled date.  Clicking on
it will delete this date's schedule.  Once you click on this, you cannot recover that date's information; you will have to
create a new schedule for that date.</li><br>
<li><b>Church Holiday Name</b> - For any Church Holiday (dates in RED), the name of that holiday will show up in
small print at the end of the line.  This will help to eliminate confusion when a Church Holiday occurs on a Saturday
or Sunday (e.g. December 2006 - Christmas Eve is on a Sunday, so both the Sunday and the Holiday show up on the
schedule).</li><br>
</ul>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="Schedule - Update Date">
<ul>
<li><b>Location</b> - EITHER select a location from the dropdown box (locations are in alphabetical order by State, then City, then Name)
OR check the &quot;Unavailable&quot; box.  If you do both, the &quot;Unavailable&quot; checkbox wins.  If the location you
want is not in the list, you must add it through the Locations tab; this change is to help prevent the addition of duplicate
or invalid locations.</li>
<li><b>Time</b> - You may optionally enter the time of the service, the time you want to arrive at the church, the time you
need to leave your house, or any other time you want.  This is a free-form field, so any time format is acceptable.  In fact,
there is nothing to prevent you putting absolutely anything at all in this field, but more than a few characters will seriously 
mess up this tab&#39;s view.</li>
<li><b>Notes</b> - This field will expand to any length, and can be used to capture anything you need to remember about this
date.</li>
<li><b>Pieces Played</b> - This field will expand to any length, and can be used to document the pieces you played that day.  
If you enter your pieces regularly, you can use the &quot;My Pieces&quot; tab to see everything you&#39;ve played, or click
on a location name in the schedule tabs to see just the pieces you&#39;ve played at that location.  (I use that feature
frequently to see what I&#39;ve played on the last few visits!)</li>
<li><b>Tentative?</b> - Use this checkbox to flag a schedule as tentative.  Your name will show inside parentheses for that
date on the public calendar as an indication that you might or might not be available.  The <img src="images/qm_sm.jpg"/> that
appears in the schedule listing also serves as a reminder that you have an unconfirmed date.</li>
</ul>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="Schedule - Update Multiples">
<center><i>This is my favorite new feature -- I hope you like it!</i></center><p/>
<ul>
<li>Use this feature to add or update several dates at once with the same location (or &quot;Unavailable&quot;).  <i>There are reasons why you may
not want to use this on existing schedules -- see &quot;Details&quot; below.</i>  This feature is available only on the &quot;Current
13 Months&quot; tab.</li>
<li><b>Using the feature</b> - Check the checkbox next to the day for each date you want to apply your changes to, then click
the &quot;Update Multiples&quot; button.  <b>The first date checked</b> will be used as the template for the rest that are checked;
if the first is blank, the form that comes up will be blank.  If the first is a scheduled date, the form will be pre-populated
with that first date&#39;s information.</li>
<li><b>Fields Available</b> - The only field not available with this feature is &quot;Pieces Played.&quot;  You would not
expect to play the same pieces at the same location over and over, right?</li>
<li><b>Details</b> - All dates updated will have IDENTICAL information for Location/Unavailable and for Tentative,
based on the values you have entered. The Notes and Time fields work differently: if there is data entered on the
update form, that data will be written to each of the dates updated; if the field is empty on the form, any existing data
on the dates updated will be left unchanged. The Pieces Played field will always be left unchanged on the dates updated.</li>
</ul>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="Locations Tab">
<p>All current locations are listed here, sorted by state, then city, then name.
Hover your mouse cursor on a location name to see at least the first part
of any <b>public notes</b> for that location. If you have entered any <b>private notes</b>, those will also be
shown.</p>
<ul>
<li><b>To Edit a Location</b> (or see all information for it) - Click on the location name.  A form will open up where you
can see and edit the Name, Address, City, State, ZIP, and the Public and Private user notes.</li>
<li><b>To Add a location</b> - Click the &quot;Add a Location&quot; button and a blank form will open up.  <b>Before adding
a new location, check the list to be sure that you are not duplicating one which already exists.</b></li>
<li><b>Public Notes</b> - These are visible to all organists on the site, and are intended to be helpful information
that someone who is unfamiliar with this location would want to know.</li>
<li><b>Private Notes</b> - This is visible only by you, and can be used for anything you want to remember but which you
do not want to share.  One possible use would be the driving time from your house to the church.</li>
</ul>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="My Pieces Tab">
            <p>If you have entered pieces you have played each week, clicking on this tab will bring up a list of
            everywhere you have played and the pieces you played that day, in reverse chronological order.  Only those
            scheduled dates where you have entered pieces will show in this view.</p>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="My Info Tab">
This tab is where you update your personal information and select whether you want to be able to available on Saturdays,
Sundays, or both; that latter choice also determines what dates appear in your schedule tabs.<p/>
Clicking the &quot;Update Basic Information&quot; allows you to change most of your personal information.  A form will appear
which has pop-up help for every field.  The two fields which may benefit from additional explanation are these:
<ul>
<li><b>Show in Organists List</b> - If checked, your name and information will appear on the &quot;Organists Information&quot;
tab which is visible to the public.  If this is not checked, nobody will know how to get in touch with you.  When this
is checked, you agree to log in to the site at least once every 6 months to ensure that your contact information is
current.</li>
<li><b>Show in Calendar</b> - If checked, your name will appear on the public &quot;Calendar&quot; tab for every date you
available.  You also agree to do your best to keep your schedule on this site reasonably up to date.  Failure to log in at
least once every 3 months will cause this box to be unchecked; your contact information will still be visible, but you will
not appear on the calendar.</li>
</ul>
<p>Clicking &quot;Update Phone Number(s)&quot; or &quot;Update Email Address(es)&quot; allows you to enter and edit one or
more of each.</p>
        </div>
    </div>
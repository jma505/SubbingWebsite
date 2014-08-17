<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:html="/WEB-INF/struts-html.tld">
	
	<xsl:template match="/">
		<font size="+1">
			<div class="table">
				<table align="center" border="1" cellspacing="5"
					cellpadding="5" frame="void" rules="rows">
					<tr>
						<th align="right">Month</th>
						<th align="center">Date</th>
						<th></th>
						<th align="center">Time</th>
						<th align="center">Pieces?</th>
						<th align="left">Location</th>
						<th align="center">Tentative?</th>
						<th align="center">Delete</th>
					</tr>
					<xsl:apply-templates select="months/month"/>
				</table>
			</div>
		</font>
	</xsl:template>

	<xsl:template match="months/month">
	    <xsl:variable name="mName"><xsl:value-of select="name"/></xsl:variable>
	    <xsl:variable name="rowsV"><xsl:value-of select="count(weekend/*/day)"/></xsl:variable>
		<xsl:apply-templates select="weekend">
			<xsl:with-param name="monthName" select="$mName"/>
			<xsl:with-param name="rows" select="$rowsV"/>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="weekend">
		<xsl:param name="monthName"/>
		<xsl:param name="rows"/>
		<xsl:variable name="hl">
			<xsl:value-of select="@highlight"/>
		</xsl:variable>
		<xsl:variable name="firstWeekend">
			<xsl:choose>
				<xsl:when test="position() = 1">1</xsl:when>
				<xsl:otherwise>0</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:for-each select="*">
			<xsl:element name="tr">
				<xsl:if test="$hl = 'yes'">
					<xsl:attribute name="bgcolor">
						#e7edf5
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="$firstWeekend = 1">
					<xsl:if test="position() = 1">
						<th align="right" valign="top">
							<xsl:attribute name="rowspan">
								<xsl:value-of select="$rows" />
							</xsl:attribute>
							<xsl:value-of select="$monthName" />
						</th>
					</xsl:if>
				</xsl:if>
				<xsl:variable name="color">
					<xsl:choose>
						<xsl:when test="name() = 'saturday'">blue</xsl:when>
						<xsl:when test="name() = 'holiday'">red</xsl:when>
						<xsl:otherwise>black</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<td align="center">
					<xsl:element name="span">
							<xsl:attribute name="style">
								<xsl:text>cursor:pointer;</xsl:text>
							</xsl:attribute>
						<xsl:attribute name="onClick">
							<xsl:text>showDateDialog(&quot;</xsl:text>
							<xsl:value-of select="../../name" />
							<xsl:text>&quot;,</xsl:text>
							<xsl:value-of select="day" />
							<xsl:if test="string(holidayName)">
								<xsl:text>,true);</xsl:text>
							</xsl:if>
							<xsl:if test="not(string(holidayName))">
								<xsl:text>,false);</xsl:text>
							</xsl:if>
						</xsl:attribute>
					<xsl:element name="font">
						<xsl:attribute name="color">
							<xsl:value-of select="$color" />
						</xsl:attribute>
					<xsl:value-of select="day" />
					</xsl:element>
					</xsl:element>
				</td>
				<td align="center">
					<xsl:element name="input">
						<xsl:attribute name="data-dojo-type">
							<xsl:text>dijit.form.CheckBox</xsl:text>
						</xsl:attribute>
						<xsl:attribute name="type">
							<xsl:text>checkbox</xsl:text>
						</xsl:attribute>
						<xsl:attribute name="name">
							<xsl:text>bulkSelect</xsl:text>
						</xsl:attribute>
						<xsl:attribute name="class">
							<xsl:text>bulkSelect</xsl:text>
						</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="../../name" />
							<xsl:text>&#58;</xsl:text>
							<xsl:value-of select="day" />
							<xsl:if test="string(holidayName)">
								<xsl:text>&#58;true</xsl:text>
							</xsl:if>
							<xsl:if test="not(string(holidayName))">
								<xsl:text>&#58;false</xsl:text>
							</xsl:if>
						</xsl:attribute>
					</xsl:element>
				</td>
				<td><xsl:value-of select="service_time" /></td>
				<td align="center">
 					<xsl:if test="string(played)">
						<xsl:element name="span">
							<xsl:attribute name="title">
								<xsl:value-of select="played" />
							</xsl:attribute>
							<img src="images/checkmark3.gif" />
						</xsl:element>
					</xsl:if>
				</td>
				<td>
 					<xsl:if test="string(details)">
						<xsl:element name="span">
							<xsl:attribute name="style">
								<xsl:text>cursor:pointer;</xsl:text>
							</xsl:attribute>
							<xsl:attribute name="onClick">
								<xsl:text>showLocationPieces(</xsl:text>
								<xsl:value-of select="locationId" />
								<xsl:text>);</xsl:text>
							</xsl:attribute>
					<xsl:element name="font">
						<xsl:attribute name="color">
							<xsl:value-of select="$color" />
						</xsl:attribute>
					<xsl:value-of select="details" />
					</xsl:element>
						</xsl:element>
					</xsl:if>
				</td>
				<td align="center">
					<xsl:if test="tentative = 'true'">
					<xsl:element name="span">
							<xsl:attribute name="style">
								<xsl:text>cursor:pointer;</xsl:text>
							</xsl:attribute>
						<xsl:attribute name="onClick">
							<xsl:text>toggleTentativeOff(&quot;</xsl:text>
							<xsl:value-of select="../../name" />
							<xsl:text>&quot;,</xsl:text>
							<xsl:value-of select="day" />
							<xsl:if test="string(holidayName)">
								<xsl:text>,true);</xsl:text>
							</xsl:if>
							<xsl:if test="not(string(holidayName))">
								<xsl:text>,false);</xsl:text>
							</xsl:if>
						</xsl:attribute>
						<img alt="Tentative" src="images/qm_sm.jpg" />
					</xsl:element>
					</xsl:if>
				</td>
				<td align="center">
					<xsl:if test="string(details)">
						<xsl:element name="span">
							<xsl:attribute name="style">
								<xsl:text>cursor:pointer;</xsl:text>
							</xsl:attribute>
							<xsl:attribute name="onClick">
								<xsl:text>deleteSchedule(</xsl:text>
								<xsl:value-of select="id" />
								<xsl:text>);</xsl:text>
							</xsl:attribute>
							<img border="0" alt="delete" src="images/delete_sm.png" />
						</xsl:element>
					</xsl:if>
				</td>
				<xsl:if test="string(holidayName)">
					<td><font color="red" size="-2">
						<xsl:value-of select="holidayName" />
					</font></td>
				</xsl:if>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>


<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<table border="1" rules="none" cellspacing="5" cellpadding="5"
			align="center">
			<tr>
				<td />
				<td>
					<i>
						<font size="+2">
							<xsl:value-of select="organist/fullName" />
						</font>
					</i>
				</td>
			</tr>
			<tr>
				<th align="right">Short Name</th>
				<td>
					<xsl:value-of select="organist/shortName" />
				</td>
			</tr>
			<tr>
				<th align="right">Weekend Availability</th>
				<td>
					<xsl:if test="organist/availableSaturday = 'Yes'">
						<xsl:if test="organist/availableSunday = 'Yes'">
							Both Saturdays and Sundays
						</xsl:if>
						<xsl:if test="organist/availableSunday = 'No'">
							Saturdays only
						</xsl:if>
					</xsl:if>
					<xsl:if test="organist/availableSaturday = 'No'">
						<xsl:if test="organist/availableSunday = 'Yes'">
							Sundays only
						</xsl:if>
						<xsl:if test="organist/availableSunday = 'No'">
							Not available for services
						</xsl:if>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<th align="right" valign="top">Weddings/Funerals</th>
				<td>
					<xsl:value-of select="organist/wf" />
				</td>
			</tr>
			<tr>
				<th align="right" valign="top">Telephone</th>
				<td>
					<xsl:apply-templates select="organist/phone" />
				</td>
			</tr>
			<tr>
				<th align="right" valign="top">Email</th>
				<td>
					<xsl:apply-templates select="organist/email" />
				</td>
			</tr>

			<tr>
				<th align="right" valign="top">Travel Area</th>
				<td width="500">
					<xsl:value-of select="organist/travel" />
				</td>
			</tr>
			<tr>
				<th align="right" valign="top">Information</th>
				<td width="500">
					<xsl:value-of select="organist/details" />
				</td>
			</tr>
			<tr>
				<th align="right" valign="top">Show in Organists List</th>
				<td width="500">
					<xsl:value-of select="organist/activeUser" />
				</td>
			</tr>
			<tr>
				<th align="right" valign="top">Show in Calendar</th>
				<td width="500">
					<xsl:value-of select="organist/showInCalendar" />
				</td>
			</tr>
		</table>
	</xsl:template>

	<xsl:template match="organist/phone">
		<xsl:value-of select="number" />
		<br />
	</xsl:template>

	<xsl:template match="organist/email">
		<xsl:element name="a">
			<xsl:attribute name="href">
        <xsl:text>mailto:</xsl:text>
        <xsl:choose>
        <xsl:when test="contains(address, '[Preferred]')">
          <xsl:value-of select="substring-before(address, '[Preferred]')" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="address" />
        </xsl:otherwise>
        </xsl:choose>
        </xsl:attribute>
		</xsl:element>
		<xsl:value-of select="address" />
		<br />
	</xsl:template>

</xsl:stylesheet>


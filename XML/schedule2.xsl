<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<table align="center" border="0" cellspacing="10"
			cellpadding="5">
			<xsl:apply-templates select="months/month"/>
		</table>
	</xsl:template>

	<xsl:template match="months/month">
		<tr>
			<th align="right">
				<i>
					<xsl:value-of select="name" />
				</i>
			</th>
			<xsl:apply-templates select="weekend" />
		</tr>
	</xsl:template>

	<xsl:template match="weekend">
		<td align="center">
			<xsl:if test="position() = 1">
				<xsl:if test="not(name(./*) = 'saturday')">
					<font color="black" size="+1">
						<b>-</b>
					</font>
				</xsl:if>
			</xsl:if>
			<xsl:for-each select="*">
				<xsl:element name="span">
					<xsl:attribute name="title">
						<xsl:if test="string(holidayName)">
							<xsl:value-of select="holidayName" />
							<xsl:text>:</xsl:text>
						</xsl:if>
						<xsl:value-of select="details" />
					</xsl:attribute>
				
				<xsl:if test="contains('saturdaysunday', name())">
					<xsl:if test="string(details)">
						<font size="+1" color="green">
							<b>
								<i>
									<xsl:value-of select="day" />
								</i>
							</b>
						</font>
					</xsl:if>
					<xsl:if test="not(string(details))">
						<font color="red">
							<xsl:value-of select="day" />
						</font>
					</xsl:if>
				</xsl:if>
				<xsl:if test="name()='holiday'">
					<xsl:if test="string(details)">
						<font size="+1" color="purple">
							<b>
								<i>
									<xsl:value-of select="day" />
								</i>
							</b>
						</font>
					</xsl:if>
					<xsl:if test="not(string(details))">
						<font color="red">
							<xsl:value-of select="day" />
						</font>
					</xsl:if>
				</xsl:if>
				<xsl:if test="not(position()=last())">
					<font color="black" size="+1">
						<b>-</b>
					</font>
				</xsl:if>
				</xsl:element>
			</xsl:for-each>
		</td>
	</xsl:template>

</xsl:stylesheet>


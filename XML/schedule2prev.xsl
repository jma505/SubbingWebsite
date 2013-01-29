<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="months">
		<table align="center" border="0" cellspacing="5" cellpadding="5">
			<xsl:apply-templates />
		</table>
	</xsl:template>

	<xsl:template match="month">
		<tr>
			<th align="right">
				<i>
					<xsl:value-of select="name" />
				</i>
			</th>
			<xsl:apply-templates select="./weekend" />
		</tr>
	</xsl:template>

	<xsl:template match="weekend">
		<td align="center">
			<xsl:element name="span">
				<xsl:attribute name="title">
					<xsl:value-of select="saturday/details" />
				</xsl:attribute>
			</xsl:element>
			<xsl:if test="string(saturday/details)">
				<font size="+1" color="green">
					<b>
						<i>
							<xsl:value-of select="saturday/day" />
						</i>
					</b>
				</font>
			</xsl:if>
			<xsl:if test="not(string(saturday/details))">
				<font color="red">
					<xsl:value-of select="saturday/day" />
				</font>
			</xsl:if>
			<font color="black" size="+1">
				<b>-</b>
			</font>
			<xsl:element name="span">
				<xsl:attribute name="title">
					<xsl:value-of select="sunday/details" />
				</xsl:attribute>
			</xsl:element>
			<xsl:if test="string(sunday/details)">
				<font size="+1" color="green">
					<b>
						<i>
							<xsl:value-of select="sunday/day" />
						</i>
					</b>
				</font>
			</xsl:if>
			<xsl:if test="not(string(sunday/details))">
				<font color="red">
					<xsl:value-of select="sunday/day" />
				</font>
			</xsl:if>
		</td>
	</xsl:template>

</xsl:stylesheet>


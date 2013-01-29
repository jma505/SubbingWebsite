<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<table align="center" border="0" cellspacing="10"
			cellpadding="5">
			<xsl:apply-templates select="months/month" />
		</table>
	</xsl:template>

	<xsl:template match="months/month">
		<tr>
			<th align="right">
				<i>
					<xsl:value-of select="name" />
				</i>
			</th>
			<xsl:apply-templates select="weekend">
			  <xsl:with-param name="pMonth" select="position()" />
			</xsl:apply-templates>
		</tr>
	</xsl:template>

	<xsl:template match="weekend">
	  <xsl:param name="pMonth" />
	  <xsl:variable name="pWeekend" select="position()" />
	  
		<td align="center">
			<xsl:if test="position() = 1">
				<xsl:if test="not(name(./*) = 'saturday')">
					<font color="black" size="+1">
						<b>-</b>
					</font>
				</xsl:if>
			</xsl:if>
			<xsl:for-each select="*">
				<xsl:if test="contains('saturdaysunday', name())">
					<xsl:if test="string(details)">
						<font size="+1" color="green">
							<b>
								<i>
									<xsl:element name="div">
					<xsl:attribute name="id">
					    <xsl:value-of select="$pMonth" />
					    <xsl:value-of select="$pWeekend" />
					    <xsl:text>-</xsl:text>
					    <xsl:value-of select="day"/>
					</xsl:attribute>
				<xsl:value-of select="day" />
				</xsl:element>
								</i>
							</b>
						</font>
					</xsl:if>
					<xsl:if test="not(string(details))">
						<font color="red">
							<xsl:element name="div">
					<xsl:attribute name="id">
					    <xsl:value-of select="$pMonth" />
					    <xsl:value-of select="$pWeekend" />
					    <xsl:text>-</xsl:text>
					    <xsl:value-of select="day"/>
					</xsl:attribute>
				<xsl:value-of select="day" />
				</xsl:element>
						</font>
					</xsl:if>
				</xsl:if>
				<xsl:if test="name()='holiday'">
					<xsl:if test="string(details)">
						<font size="+1" color="purple">
							<b>
								<i>
									<xsl:element name="div">
					<xsl:attribute name="id">
					    <xsl:value-of select="$pMonth" />
					    <xsl:value-of select="$pWeekend" />
					    <xsl:text>-</xsl:text>
					    <xsl:value-of select="day"/>
					</xsl:attribute>
				<xsl:value-of select="day" />
				</xsl:element>
								</i>
							</b>
						</font>
					</xsl:if>
					<xsl:if test="not(string(details))">
						<font color="red">
							<xsl:element name="div">
					<xsl:attribute name="id">
					    <xsl:value-of select="$pMonth" />
					    <xsl:value-of select="$pWeekend" />
					    <xsl:text>-</xsl:text>
					    <xsl:value-of select="day"/>
					</xsl:attribute>
				<xsl:value-of select="day" />
				</xsl:element>
						</font>
					</xsl:if>
				</xsl:if>
				<xsl:if test="not(position()=last())">
					<font color="black" size="+1">
						<b>-</b>
					</font>
				</xsl:if>
				<xsl:element name="span">
				    <xsl:attribute name="data-dojo-type">
				        <xsl:text>dijit.Tooltip</xsl:text>
				    </xsl:attribute>
				    <xsl:attribute name="data-dojo-props">
				        <xsl:text>connectId:&apos;</xsl:text>
				        <xsl:value-of select="$pMonth" />
				        <xsl:value-of select="$pWeekend" />
					    <xsl:text>-</xsl:text>
					    <xsl:value-of select="day"/>
				        <xsl:text>&apos;</xsl:text>
				    </xsl:attribute>
				    <xsl:element name="font">
				    <xsl:attribute name="size">
				    <xsl:text>-1</xsl:text>
				    </xsl:attribute>
				    <xsl:if test="string(holidayName)">
				        <xsl:element name="em">
				        <xsl:value-of select="holidayName" />
				        <xsl:text>: </xsl:text>
				        </xsl:element>
				    </xsl:if>
				    <xsl:value-of select="details" />
				    </xsl:element>
				</xsl:element>
			</xsl:for-each>
		</td>
	</xsl:template>

</xsl:stylesheet>


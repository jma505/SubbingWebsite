<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<center>
			<b>Current Email Addresses:</b>
			<p />
			<table rules="none" border="1" cellspacing="5" cellpadding="5"
				width="300" align="center">
				<xsl:apply-templates select="organist/email" />
			</table>
		</center>
	</xsl:template>

	<xsl:template match="organist/email">
		<tr>
			<td>
				<xsl:element name="span">
					<xsl:attribute name="onClick">
			<xsl:text>deleteEmail(</xsl:text>
			<xsl:value-of select="id" />
			<xsl:text>);</xsl:text>
		</xsl:attribute>
				</xsl:element>
				<span title="Delete this email address">
					<img border="0" alt="delete" src="images/delete_sm.png" />
				</span>
			</td>
			<td>
				<xsl:element name="div">
				<xsl:attribute name="style">
				<xsl:text>cursor:pointer</xsl:text>
				</xsl:attribute>
					<xsl:attribute name="onClick">
              <xsl:text>moveEmailToRight(&quot;</xsl:text>
              <xsl:value-of select="em" />
              <xsl:text>&quot;,</xsl:text>
              <xsl:value-of select="pr" />
              <xsl:text>,</xsl:text>
              <xsl:value-of select="id" />
              <xsl:text>);</xsl:text>
            </xsl:attribute>
				<xsl:value-of select="address" />
				</xsl:element>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>


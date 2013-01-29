<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<center>
			<b>Current Phone Numbers:</b>
			<p />
			<table rules="none" border="1" cellspacing="5" cellpadding="5"
				width="300" align="center">
				<xsl:apply-templates select="organist/phone" />
			</table>
		</center>
	</xsl:template>

	<xsl:template match="organist/phone">
		<tr>
			<td>
				<xsl:element name="span">
				<xsl:attribute name="style">
				<xsl:text>cursor:pointer</xsl:text>
				</xsl:attribute>
					<xsl:attribute name="onClick">
			<xsl:text>deletePhone(</xsl:text>
			<xsl:value-of select="id" />
			<xsl:text>);</xsl:text>
		</xsl:attribute>
				</xsl:element>
				<span title="Delete this phone number">
					<img border="0" alt="delete" src="images/delete_sm.png" />
				</span>
			</td>
			<td>
				<xsl:element name="div">
					<xsl:attribute name="onClick">
              <xsl:text>movePhoneToRight(&quot;</xsl:text>
              <xsl:value-of select="pac" />
              <xsl:text>&quot;,&quot;</xsl:text>
              <xsl:value-of select="pp" />
              <xsl:text>&quot;,&quot;</xsl:text>
              <xsl:value-of select="pe" />
              <xsl:text>&quot;,&quot;</xsl:text>
              <xsl:value-of select="pt" />
              <xsl:text>&quot;,</xsl:text>
              <xsl:value-of select="id" />
              <xsl:text>);</xsl:text>
            </xsl:attribute>
				<xsl:value-of select="number" />
				</xsl:element>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>


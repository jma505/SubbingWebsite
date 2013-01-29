<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<center>
			<b>Current Locations (sorted by State - City - Name)</b>
		</center>
		<p />
		<div class="table">
			<table align="center" border="1" rules="none" cellspacing="5"
				cellpadding="5">
				<xsl:apply-templates select="locations/location" />
			</table>
		</div>
	</xsl:template>

	<xsl:template match="locations/location">
	<xsl:if test="@id != '999999'">
		<tr>
			<td>
				<xsl:element name="div">
							<xsl:attribute name="style">
								<xsl:text>cursor:pointer;</xsl:text>
							</xsl:attribute>
					<xsl:attribute name="onClick">
          <xsl:text>moveLocationToRight(</xsl:text>
          <xsl:value-of select="@id" />
          <xsl:text>,&quot;</xsl:text>
          <xsl:value-of select="name" />
          <xsl:text>&quot;,&quot;</xsl:text>
          <xsl:value-of select="address" />
          <xsl:text>&quot;,&quot;</xsl:text>
          <xsl:value-of select="city" />
          <xsl:text>&quot;,&quot;</xsl:text>
          <xsl:value-of select="state" />
          <xsl:text>&quot;,&quot;</xsl:text>
          <xsl:value-of select="zip" />
          <xsl:text>&quot;,&quot;</xsl:text>
          <xsl:value-of select="notes" />
          <xsl:text>&quot;,&quot;</xsl:text>
          <xsl:value-of select="usernotes" />
          <xsl:text>&quot;)</xsl:text>
        </xsl:attribute>
					<xsl:attribute name="title">
         <xsl:if test="string(notes)">
          <xsl:text>[NOTES:] </xsl:text>
          <xsl:value-of select="notes" />
          <xsl:text>    </xsl:text>
        </xsl:if>
        <xsl:if test="string(usernotes)">
          <xsl:text>[PRIVATE:] </xsl:text>
          <xsl:value-of select="usernotes" />
        </xsl:if>
          <xsl:if test="not(string(notes)) and not(string(usernotes))">
            <xsl:text>(no attached notes)</xsl:text>
          </xsl:if>
        </xsl:attribute>
					<xsl:value-of select="city" />
					<xsl:text>, </xsl:text>
					<xsl:value-of select="state" />
					<xsl:text> - </xsl:text>
					<xsl:value-of select="name" />
				</xsl:element>
			</td>
		</tr>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>


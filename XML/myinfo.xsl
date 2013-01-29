<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="sessid" />

	<xsl:template match="organist">
		<center>
			<p>
				<b>Telephone Numbers:</b>
			</p>
			<table rules="none" border="1" cellspacing="5" cellpadding="5"
				width="300" align="center">
				<xsl:for-each select="phone">
					<tr>
						<td>
							<xsl:value-of select="number" />
						</td>
					</tr>
				</xsl:for-each>
				<tr>
					<td align="center">
						<p>
							<xsl:element name="a">
								<xsl:attribute name="href"><xsl:text>/subbing/getAllPhones.do;jsessionid=</xsl:text><xsl:value-of
									select="$sessid" /></xsl:attribute>
							</xsl:element>
							Edit Phone Numbers
						</p>
					</td>
				</tr>
			</table>
			<p></p>
			<p>
				<b>Email Addresses:</b>
			</p>
			<table rules="none" border="1" cellspacing="5" cellpadding="5"
				width="400" align="center">
				<xsl:for-each select="email">
					<tr>
						<td>
							<xsl:value-of select="address" />
						</td>
					</tr>
				</xsl:for-each>
				<tr>
					<td align="center">
						<p>
							<xsl:element name="a">
								<xsl:attribute name="href"><xsl:text>/subbing/getAllEmails.do;jsessionid=</xsl:text><xsl:value-of
									select="$sessid" /></xsl:attribute>
							</xsl:element>
							Edit Email Addresses
						</p>
					</td>
				</tr>
			</table>
		</center>
	</xsl:template>

</xsl:stylesheet>


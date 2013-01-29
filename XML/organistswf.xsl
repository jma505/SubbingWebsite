<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="organists">
  <table border="1" rules="none" cellspacing="5" cellpadding="5" align="center">
    <xsl:for-each select="organist"><xsl:if test="string(wf)">
      <tr>
      	<td/>
        <td><i><font size="+2"><u><xsl:value-of select="shortName"/></u></font><font size="+1">  (<xsl:value-of select="fullName"/>)</font></i></td>
      </tr>
      <tr><th align="right">Weekend Availability</th><td>
      <xsl:if test="availableSaturday = 'Yes'">
		<xsl:if test="availableSunday = 'Yes'">
		  Both Saturdays and Sundays
		</xsl:if>
		<xsl:if test="availableSunday = 'No'">
		  Saturdays only
		</xsl:if>
	  </xsl:if>
	  <xsl:if test="availableSaturday = 'No'">
	    <xsl:if test="availableSunday = 'Yes'">
	      Sundays only
	    </xsl:if>
	    <xsl:if test="availableSunday = 'No'">
	      Not available for services
	    </xsl:if>
	  </xsl:if>
      </td></tr>
        <tr>
          <th align="right" valign="top">Weddings/Funerals</th>
          <td><xsl:value-of select="wf"/></td>
        </tr>
      <tr>
        <th align="right" valign="top">Telephone</th>
        <td>
<xsl:for-each select="phone">
     <xsl:if test="position() > 1"><br/></xsl:if>
     <xsl:value-of select="number"/>
</xsl:for-each>
    </td>
      </tr>
      <xsl:if test="string(email)">
      <tr>
        <th align="right" valign="top">Email</th>
        <td>
        <xsl:for-each select="email">
        <xsl:if test="position() > 1"><br/></xsl:if>
    <xsl:element name="a">
        <xsl:attribute name="href">
        <xsl:text>mailto:</xsl:text>
        <xsl:choose>
        <xsl:when test="contains(address, '[Preferred]')">
          <xsl:value-of select="substring-before(address, '[Preferred]')"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="address"/>
        </xsl:otherwise>
        </xsl:choose>
        </xsl:attribute>
        </xsl:element>
        <xsl:value-of select="address"/>
        </xsl:for-each>
    </td>
      </tr>
      </xsl:if>
    <xsl:if test="string(travel)">
      <tr>
        <th align="right" valign="top">Travel Area</th>
        <td width="500"><xsl:value-of select="travel"/></td>
      </tr>
    </xsl:if>
    <tr>
      <th align="right" valign="top">Information</th>
      <td width="500"><xsl:value-of select="details"/></td>
    </tr>
<xsl:if test="not(position() = last())">
    <tr><td colspan="2"><hr/></td></tr>
</xsl:if>
</xsl:if>
  </xsl:for-each>
  </table> 
</xsl:template>

</xsl:stylesheet>


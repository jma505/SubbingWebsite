<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="months">
  <table align="center" border="1" cellspacing="5" cellpadding="5">
  <tr>
    <th>Month</th>
    <th>Sunday</th>
    <th>Available Organists</th>
  </tr>
  <xsl:apply-templates/>
  </table>
</xsl:template>

<xsl:template match="month">
  <xsl:apply-templates select="./sunday"/>
</xsl:template>

<xsl:template match="sunday">
  <tr>
    <xsl:if test="day &lt; 8">
      <th align="right" valign="top">
        <xsl:attribute name="rowspan">
          <xsl:value-of select="../@sundays"/>
        </xsl:attribute>
        <xsl:value-of select="../name"/>
      </th>
    </xsl:if>
    <td>
      <xsl:value-of select="day"/>
    </td>
    <td>
      <xsl:value-of select="details"/>
    </td>
  </tr>
</xsl:template>

</xsl:stylesheet>


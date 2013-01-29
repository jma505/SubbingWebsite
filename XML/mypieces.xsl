<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="months">
  <table align="center" border="1" cellspacing="5" cellpadding="5" frame="void" rules="rows">
  <tr>
    <th width="100" align="right">Sunday</th>
    <th align="left">Pieces Played</th>
    <th align="left">Location</th>
  </tr>
  <xsl:apply-templates/>
  </table>
</xsl:template>

<xsl:template match="month">
  <xsl:apply-templates select="./weekend"/>
</xsl:template>

<xsl:template match="weekend">
  <tr>
    <th align="right">
      <xsl:value-of select=".//day"/>
      <xsl:text> </xsl:text>
      <xsl:value-of select="../name"/>
    </th>
    <td align="left">
      <xsl:value-of select=".//played"/>
    </td>
    <td align="left">
      <xsl:value-of select=".//details"/>
    </td>
  </tr>
</xsl:template>

</xsl:stylesheet>


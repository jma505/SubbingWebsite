<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
 <center>
  <b>Current Users:</b><p/>
  <table rules="none" border="1" cellspacing="5" cellpadding="5" align="center">
    <tr>
      <td/>
        <th align="left">Name</th>
        <th align="left">Last Login</th>
        <th align="left">Status</th>
        <th align="left">Comments</th>
        <td/>
      </tr>
      <xsl:apply-templates select="users"/>
    </table>
  </center>
</xsl:template>
      
    <xsl:template match="users">  
     <xsl:for-each select="*">
       <tr>
        <td>
          <xsl:element name="span">
							<xsl:attribute name="style">
								<xsl:text>cursor:pointer;</xsl:text>
							</xsl:attribute>
							<xsl:attribute name="onClick">
								<xsl:text>deleteUser(&quot;</xsl:text>
								<xsl:value-of select="username" />
								<xsl:text>&quot;);</xsl:text>
							</xsl:attribute>
          <img border="0" alt="delete" src="images/delete_sm.png"/>
          </xsl:element>
        </td>
        <td>
          <xsl:element name="span">
          	<xsl:attribute name="style">
          		<xsl:text>cursor:pointer;</xsl:text>
          	</xsl:attribute>
            <xsl:attribute name="onClick">
              <xsl:text>moveUserToRight(&quot;</xsl:text>
              <xsl:value-of select="username"/>
              <xsl:text>&quot;,&quot;</xsl:text>
              <xsl:value-of select="nickname"/>
              <xsl:text>&quot;,&quot;</xsl:text>
              <xsl:value-of select="fullname"/>
              <xsl:text>&quot;,&quot;</xsl:text>
              <xsl:value-of select="comments"/>
              <xsl:text>&quot;);</xsl:text>
            </xsl:attribute>
          <xsl:value-of select="username"/>
          <xsl:text> - </xsl:text>
          <xsl:value-of select="nickname"/>
          <xsl:text> - </xsl:text>
          <xsl:value-of select="fullname"/>
          </xsl:element></td>
          <td><xsl:value-of select="lastlogin"/></td>
          <td><xsl:value-of select="status"/></td>
          <td><xsl:value-of select="comments"/></td>
          <td><xsl:element name="span">
          	<xsl:attribute name="style">
          		<xsl:text>cursor:pointer;</xsl:text>
          	</xsl:attribute>
            <xsl:attribute name="onClick">
              <xsl:text>resetPassword(&quot;</xsl:text>
              <xsl:value-of select="username"/>
              <xsl:text>&quot;);</xsl:text>
            </xsl:attribute>
          	<u>ResetPwd</u>
          </xsl:element></td>
          <td><xsl:element name="span">
          	<xsl:attribute name="style">
          		<xsl:text>cursor:pointer;</xsl:text>
          	</xsl:attribute>
            <xsl:attribute name="onClick">
              <xsl:text>setActive(&quot;</xsl:text>
              <xsl:value-of select="username"/>
              <xsl:text>&quot;);</xsl:text>
            </xsl:attribute>
          	<u>SetActive</u>
          </xsl:element></td>
          <td><xsl:element name="span">
          	<xsl:attribute name="style">
          		<xsl:text>cursor:pointer;</xsl:text>
          	</xsl:attribute>
            <xsl:attribute name="onClick">
              <xsl:text>setInactive(&quot;</xsl:text>
              <xsl:value-of select="username"/>
              <xsl:text>&quot;);</xsl:text>
            </xsl:attribute>
          	<u>SetInact</u>
          </xsl:element></td>
          <td><xsl:element name="span">
          	<xsl:attribute name="style">
          		<xsl:text>cursor:pointer;</xsl:text>
          	</xsl:attribute>
            <xsl:attribute name="onClick">
              <xsl:text>setLocked(&quot;</xsl:text>
              <xsl:value-of select="username"/>
              <xsl:text>&quot;);</xsl:text>
            </xsl:attribute>
          	<u>SetLocked</u>
          </xsl:element></td>
      </tr>
    </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>


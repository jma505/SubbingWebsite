  function doResize() {
    var tabcontainer = dojo.widget.byId("tabcontainer");
    tabcontainer.onResized();
  }
  function getOrganist(org) {
    DataPreparer.getOrganistAsXML(org, displayOrganist);
  }
  function displayOrganist(xmlIn) {
    var xslt = xmlParse(dojo.byId('xsl').value);
    var xmlt = xmlParse(xmlIn);
    var html = xsltProcess(xmlt, xslt);
    dojo.byId('organist').innerHTML = html;
  }
  
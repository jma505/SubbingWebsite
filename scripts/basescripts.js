function doResize() {
	var tabcontainer = dojo.widget.byId("tabcontainer");
	tabcontainer.onResized();
}
function getOrganist(org) {
	DataPreparer.getOrganistAsXML(org, displayOrganist);
}
function displayOrganist(xmlIn) {
	require([ "dojo/dom" ], function(dom) {
		var xslt = xmlParse(dom.byId('xsl').value);
		var xmlt = xmlParse(xmlIn);
		var html = xsltProcess(xmlt, xslt);
		dom.byId('organist').innerHTML = html;
	});
}

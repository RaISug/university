jQuery.sap.declare("Bootstrap");

getBaseUrl = function() {
	var url = window.location.pathname;
	if (url.indexOf("/") !== -1) {
		return url.substring(0, url.indexOf("/"));
	}
	
	return "";
};

loadApplication = function() {
	jQuery.sap.registerModulePath("hdlm.services", getBaseUrl() + "services");
	
	var requiredModules = [
	     "hdlm.services.Application",
	     "hdlm.services.Bootstrap",
	     "hdlm.services.Utils",
    ];
	
	$.each(requiredModules, function(index, modul) {
		jQuery.sap.require(modul);
	});
	
	new Application().startApplication();
};
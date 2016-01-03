com.intercity.Model = function() { };

com.intercity.Model.prototype._oData = { }

com.intercity.Model.prototype.requestXsrfToken = function() {
	$.ajaxSetup({
		headers: { 'X-XSRF-TOKEN': 'Fetch' }
	});
	var oInput = {
		url: "/api/v1/xsrf/endpoint",
		contentType: "application/json",
		success: function(responseData) {
			$.ajaxSetup({
				headers: { 'X-XSRF-TOKEN': responseData.xsrfToken }
			});
			com.intercity.Model.prototype._loadResources();
		}
	};
	com.intercity.Utils.prototype.GET(oInput);
};

com.intercity.Model.prototype._loadResources = function() {
	var fileReader = new XMLHttpRequest();
	fileReader.onreadystatechange = jQuery.proxy(function() {
		if(fileReader.readyState === 4) {
            if(fileReader.status === 200 || fileReader.status == 0) {
                this._parseResponse(fileReader.responseText);
                this._loadRouter();
                this._loadNavigationBar();
            }
        }
	}, this);
	fileReader.open("GET", "/resources/resourceBundle.properties", true);
	fileReader.send();
};

com.intercity.Model.prototype._parseResponse = function(responseText) {
	var lines = responseText.split('\n');
	
	jQuery.each(lines, jQuery.proxy(function(index, line) {
		var lineElements = line.split('=');
		this._oData[lineElements[0]] = lineElements[1];
	}, this));
};

com.intercity.Model.prototype.getModel = function() {
	return this._oData;
};

com.intercity.Model.prototype._oData.getProperty = function(property) {
	return this[property];
};

com.intercity.Model.prototype._oData.setProperty = function(property, value) {
	this[property] = value;
};

com.intercity.Model.prototype._loadRouter = function() {
	var oInput = {
		url: "/resources/Router.js",
		dataType: "script",
		success: function() {
			var hash = window.location.hash.substring(window.location.hash.lastIndexOf("/") + 1);
			var oRouter = new com.intercity.Router(hash);
			oRouter.startListening();
		}
	};
	com.intercity.Utils.prototype.GET(oInput);
};

com.intercity.Model.prototype._loadNavigationBar = function() {
	var oInput = {
		url: "/resources/Navigation.js",
		dataType: "script",
		success: function() {
			var oNavigation = new com.intercity.Navigation();
			oNavigation.loadContent();
		}
	};
	com.intercity.Utils.prototype.GET(oInput);
};

//@ sourceURL=Model.js
var viewArray = [
	{
		path : "registerVehicle",
		data: {
			viewName : "Vehicle.js",
			constructor : function() {
				return new com.intercity.Vehicle();
			}
		},
		isLoaded : false
	},
	{
		path: "registerRoute",
		data: {
			viewName: "Route.js",
			constructor: function() {
				return new com.intercity.Route();
			}
		},
		isLoaded : false
	},
	{
		path: "registerDriver",
		data: {
			viewName: "Driver.js",
			constructor: function() {
				return new com.intercity.Driver();
			}
		},
		isLoaded : false
	}
];

com.intercity.Router = function(hash) {
	if (hash !== '') {
		com.intercity.Router.prototype._navigate(hash);
	}
};

com.intercity.Router.prototype.startListening = function() {
	window.addEventListener("hashchange", jQuery.proxy(function(oEvent){
		var newUrl = oEvent.newURL;
		this._navigate(newUrl.substring(newUrl.lastIndexOf("/") + 1));
	}, this), false);
};

com.intercity.Router.prototype._navigate = function(hash) {
	var oModel = com.intercity.Model.prototype.getModel();
	var viewData = {};
	$.each(viewArray, function(index, value) {
		if (value.path.match(hash) !== null) {
			viewData = value;
		}
	});
	
	if (typeof viewData.isLoaded !== 'undefined') {
		$(oModel.getProperty('container')).empty();
		if (viewData.isLoaded === false) {
			this._loadView(viewData);
		} else {
			var oView = viewData.data.constructor();
			oView.loadContent();
		}
	}
};

com.intercity.Router.prototype._loadView = function(oViewData) {
	var isScriptLoadedSuccessfully = true;
	var oInput = {
		url: "/resources/" + oViewData.data.viewName,
		dataType: "script",
		success: function() {
			var oView = oViewData.data.constructor();
			oView.loadContent();
		},
		error: function() {
			isScriptLoadedSuccessfully = false;
		}
	};
	com.intercity.Utils.prototype.GET(oInput);
	oViewData.isLoaded = isScriptLoadedSuccessfully;
		
}
//@ sourceURL=Router.js
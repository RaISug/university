var com = { 
	intercity : {
		
	} 
}

com.intercity.Bootstrap = function() {};

com.intercity.Bootstrap.prototype.loadApplication = function() {
	$.ajax({
		url: "/resources/Utils.js",
		dataType: "script",
		success: function() {
			var oInput = {
				url: "/resources/Model.js",
				dataType: "script",
				success: function() {
					var oModel = new com.intercity.Model();
					oModel.requestXsrfToken();
				}
			};
			com.intercity.Utils.prototype.GET(oInput);
		}
	});
};

//@ sourceURL=Bootstrap.js
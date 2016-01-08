(function() {

	var ValidationUtil = {
		validateUrl: function(oData) {
			if (!oData.url || oData.url === "") {
				throw new Error("Cant invoke such type of request without specifying the url.");
			};
		},
		
		validateGETMethodType: function(oData) {
			if (oData.method && oData.method.toUpperCase() !== "GET") {
				throw new Error("Method type different than \"GET\", can't be passed.");
			}
		},
		
		validatePOSTMethodType: function(oData) {
			if (oData.method && oData.method.toUpperCase() !== "POST") {
				throw new Error("Method type different than \"POST\", can't be passed.");
			}
		},
		
		validatePUTMethodType: function(oData) {
			if (oData.method && oData.method.toUpperCase() !== "PUT") {
				throw new Error("Method type different than \"PUT\", can't be passed.");
			}
		},
		
		validateDELETEMethodType: function(oData) {
			if (oData.method && oData.method.toUpperCase() !== "DELETE") {
				throw new Error("Method type different than \"DELETE\", can't be passed.");
			}
		}
	
	};
	
	var RestUtil = function($http) {
		
		var GETRequest = function(oData, onSuccess, onError) {
			ValidationUtil.validateUrl(oData);
			ValidationUtil.validateGETMethodType(oData);
			$http(oData).then(onSuccess, onError);
		};
		
		var POSTRequest = function(oData, onSuccess, onError) {
			ValidationUtil.validateUrl(oData);
			ValidationUtil.validatePOSTMethodType(oData);
			$http(oData).then(onSuccess, onError);
		};

		var PUTRequest = function(oData, onSuccess, onError) {
			ValidationUtil.validateUrl(oData);
			ValidationUtil.validatePUTMethodType(oData);
			$http(oData).then(onSuccess, onError);
		};
		
		var DELETERequest = function(oData, onSuccess, onError) {
			ValidationUtil.validateUrl(oData);
			ValidationUtil.validateDELETEMethodType(oData);
			$http(oData).then(onSuccess, onError);
		};
		
		return {
			GET: GETRequest,
			POST: POSTRequest,
			PUT: PUTRequest,
			DELETE: DELETERequest
		};
	};

	var module = angular.module("ApplicationController");
	module.factory("RestUtil", ["$http", RestUtil]);
})();
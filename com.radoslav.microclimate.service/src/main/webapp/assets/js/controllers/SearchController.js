(function() {

	var module = angular.module("ApplicationController");
	
	var SearchController = function($scope, $location, RestUtil, Destinations) {
		$scope.weather = 0;
		$scope.url = $location.path();
		$scope.buttonText = "<span class='glyphicon glyphicon-search'></span>";
		$scope.googleMap = {
			center: {
				latitude: 43,
				longitude: 25
			},
			zoom: 6
		};
		
		// ==== Define model functions ====
		$scope.executeRequest = jQuery.proxy(executeBackendRequest, $scope, RestUtil, Destinations);
	};
	
	module.controller("SearchController", ["$scope", "$location", "RestUtil", "Destinations", SearchController]);
	
	var executeBackendRequest = function(RestUtil, Destinations, oData) {
		var requestData = prepareRequestData(oData, Destinations);
		RestUtil.GET(requestData, jQuery.proxy(onSuccess, this), jQuery.proxy(onError, this));
	};
	
	var prepareRequestData = function(oData, Destinations) {
		var path = "1=1";
		
		for (key in oData) {
			if (typeof oData[key] !== "undefined" && oData[key] !== "" && oData[key] !== null) {
				if (key === "date") {
					var date = oData[key].getUTCDate() + "." + (oData[key].getUTCMonth() + 1) + "." + oData[key].getUTCFullYear();
					path += "&" + key + "=" + date;
				} else {
					path += "&" + key + "=" + oData[key];
				}
			}
		}
		
		return {
			method: "GET",
			url: Destinations.getStatisticRetrievingEndpoint() + "?" + path
		};
	};
	
	var onSuccess = function(xhrResponse) {
		this.statisticEntries = Array.isArray(xhrResponse.data) ? xhrResponse.data : [xhrResponse.data];
	};
	
	var onError = function(xhrResponse) {
		this.title = "Неуспешен запис";
		this.text = "Данните не бяха успешно записани." +
						"Статус на грешката: [" + 
							xhrResponse.data.statusCode
						+ "], хвърлена грешка: [" + 
							xhrResponse.data.exceptionDescription
						+ "].";
		
		$("#request-execution-result-dialog").modal({ keyboard: true });
	};
})();
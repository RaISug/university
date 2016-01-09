(function() {

	var module = angular.module("ApplicationController");
	
	var SearchController = function($scope, $location, RestUtil, Destinations) {
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
		$scope.executeRequest = jQuery.proxy(executeBackendRequest, $scope, $location, RestUtil, Destinations);
	};
	
	module.controller("SearchController", ["$scope", "RestUtil", "Destinations", SearchController]);
	
	var executeBackendRequest = function(RestUtil, Destinations, oData) {
		var requestData = prepareRequestData(oData, Destinations);
		RestUtil.POST(requestData, jQuery.proxy(onSuccess, this), jQuery.proxy(onError, this));
	};
	
	var prepareRequestData = function(oData, Destinations) {
		var path = "";
		
		for (key in oData) {
			if (typeof oData[key] !== "undefined" && oData[key] !== "") {
				path += key + "=" + oData[key] + "&";
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
							xhrResponse.status
						+ "], хвърлена грешка: [" + 
							xhrResponse.statusText
						+ "].Информация от сървъра: [" + 
							(typeof xhrResponse.headers()["X-Request-Result"] === "undefined" ? "Няма информация" : xhrResponse.headers()["X-Request-Result"])
						+ "]";
		
		$("#request-execution-result-dialog").modal({ keyboard: true });
	};
})();
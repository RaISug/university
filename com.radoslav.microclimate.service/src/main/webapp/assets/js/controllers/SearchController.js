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
				} else if (key === "weather") {
					if (isValidWeatherValue()) {
						path += "&" + key + "=" + $("#weather")[0].options[$("#weather")[0].selectedIndex].value;
					}
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
	
	var isValidWeatherValue = function() {
		var weather = $("#weather")[0].options[$("#weather")[0].selectedIndex].value;
		if (weather.toLowerCase() === "sunny") {
			return true;
		} else if (weather.toLowerCase() === "cloudy") {
			return true;
		} else if (weather.toLowerCase() === "rainy") {
			return true;
		} else if (weather.toLowerCase() === "snowy") {
			return true;
		} else {
			return false;
		}
		
	};
	
	var onSuccess = function(xhrResponse) {
		if (Array.isArray(xhrResponse.data) && xhrResponse.data.length === 0) {
			this.title = "Резултат";
			this.text = "Не успяхме да намерим търсения от вас продукт.";
			$("#request-execution-result-dialog").modal({ keyboard: true });
			return;
		}
		this.statisticEntries = Array.isArray(xhrResponse.data) ? xhrResponse.data : [xhrResponse.data];
	};
	
	var onError = function(xhrResponse) {
		this.title = "Неуспешно намерен запис";
		this.text = "Данните не бяха намерени поради възникнала грешка, моля да ни извините." +
						"Статус на грешката: [" + 
							xhrResponse.data.statusCode
						+ "], хвърлена грешка: [" + 
							xhrResponse.data.exceptionDescription
						+ "].";
		
		$("#request-execution-result-dialog").modal({ keyboard: true });
	};
})();
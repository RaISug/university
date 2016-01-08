(function() {
	
	var module = angular.module("ApplicationController");
	
	var CreateController = function($scope, RestUtil, Destinations) {
		$scope.buttonText = "Добави";
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
	
	module.controller("CreateController", ["$scope", "RestUtil", "Destinations", CreateController]);
	
	var executeBackendRequest = function(RestUtil, Destinations, oData) {
		var requestData = prepareRequestData(oData, Destinations);
		RestUtil.POST(requestData, jQuery.proxy(onSuccess, this), jQuery.proxy(onError, this));
	};
	
	var prepareRequestData = function(oData) {
		var data = {
			temperature: oData.temperature,
			rainfall: oData.rainfall,
			humidity: oData.humidity,
			snowCover: oData.snowCover,
			windSpeed: oData.windSpeed,
			weather: oData.weather,
			latitude: oData.latitude,
			longitude: oData.longitude,
            date: oData.date
		};
		
		return {
			method: "POST",
			headers: "application/json",
			data: JSON.stringify(data),
			url: Destinations.getStatisticCreationEndpoint()
		};
	};
	
	var onSuccess = function(xhrResponse) {
		this.title = "Успешен запис";
		this.text = "Благодаря ви за отделеното време, данните които" +
						" предоставихте бяха успешно записани. Моля " +
						"изчакайте докато страницата бъде презаредена";
		
		$("#request-execution-result-dialog").modal({
			backdrop: "static"
		});
		
		setTimeout(function() {
			location.reload();
		}, 1500);
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
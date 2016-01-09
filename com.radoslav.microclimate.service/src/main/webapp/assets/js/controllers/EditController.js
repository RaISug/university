(function() {

	var HTTP_BAD_REQUEST = 400;
	
	var module = angular.module("ApplicationController");
	
	var EditController = function($scope, $routeParams, RestUtil, Destinations) {
		$scope.buttonText = "Редактирай";
		loadStatisticEntryById($scope, $routeParams.id, RestUtil, Destinations);
		$scope.executeRequest = jQuery.proxy(executeBackendRequest, $scope, RestUtil, Destinations);
	};
	
	module.controller("EditController", ["$scope", "$routeParams", "RestUtil", "Destinations", EditController]);
	
	// =========== Loading of entity by ID =============
	
	var loadStatisticEntryById = function($scope, entryId, RestUtil, Destinations) {
		var requestData = {
			method : "GET",
			url : Destinations.getStatisticRetrievingEndpoint()+ "?id=" + entryId
		};
		
		RestUtil.GET(requestData, jQuery.proxy(onSuccessfullyLoadedEntry, $scope), jQuery.proxy(onErrorOfLoadingEntry, $scope));
	};
	
	var onSuccessfullyLoadedEntry = function(xhrResponse) {
		if (Array.isArray(xhrResponse.data) && xhrResponse.data.length === 0) {
			this.notFound = "Не успяхме да намерим търсения от вас продукт.";
			return;
		}
		
		this.id = xhrResponse.data[0].id;
		this.temperature = xhrResponse.data[0].temperature;
		this.rainfall = xhrResponse.data[0].rainfall;
		this.humidity = xhrResponse.data[0].humidity;
		this.snowCover = xhrResponse.data[0].snowCover;
		this.windSpeed = xhrResponse.data[0].windSpeed;
		this.weather = xhrResponse.data[0].weather;
		this.latitude = xhrResponse.data[0].latitude;
		this.longitude = xhrResponse.data[0].longitude;
		this.gatheredDate = xhrResponse.data[0].gatheredOn;
	};
	
	var onErrorOfLoadingEntry = function(xhrResponse) {
		if (xhrResponse.status === HTTP_BAD_REQUEST) {
			this.errorMessage = "Не успяхме да заредим данните за този продукт, поради грешка възникнала " +
				"при валидацията на входните данни, моля опитайте пак." +
				"Статус на грешката: [" + 
					xhrResponse.data.statusCode
				+ "], хвърлена грешка: [" + 
					xhrResponse.data.exceptionDescription
				+ "].";
		} else {
			this.errorMessage = "Възникна неочаквана грешка при опит за извличане на информация за " +
				"статистическите данни, моля опитайте пак." +
				"Статус на грешката: [" +
					xhrResponse.data.statusCode
				+ "], хвърлена грешка: [" + 
					xhrResponse.data.exceptionDescription
				+ "].";
		}
	};
	
	// =========== Executing of update request =============
	
	var executeBackendRequest = function(RestUtil, Destinations, oData) {
		var requestData = prepareRequestData(oData, Destinations, this.id);
		RestUtil.PUT(requestData, jQuery.proxy(onSuccess, this), jQuery.proxy(onError, this));
	};
	
	var prepareRequestData = function(oData, Destinations, entryId) {
		var gatherDate = oData.date.getUTCDate() + "." + (oData.date.getUTCMonth() + 1) + "." + oData.date.getUTCFullYear();
		
		var data = {
			temperature: oData.temperature,
			rainfall: oData.rainfall,
			humidity: oData.humidity,
			snowCover: oData.snowCover,
			windSpeed: oData.windSpeed,
			weather: oData.weather,
			latitude: oData.latitude,
			longitude: oData.longitude,
            date: gatherDate
		};
		
		return {
			method: "PUT",
			headers: "application/json",
			data: JSON.stringify(data),
			url: String.format(Destinations.getStatisticUpdateEndpoint(), entryId)
		};
	};
	
	var onSuccess = function(xhrResponse) {
		this.title = "Успешен редактиране";
		this.text = "Благодаря ви за отделеното време, данните бяха" +
						" успешно редактирани. Моля изчакайте докато страницата бъде презаредена";
		
		$("#request-execution-result-dialog").modal({
			backdrop: "static"
		});
		
		setTimeout(function() {
			location.hash = "#/edit";
		}, 1500);
	};
	
	var onError = function(xhrResponse) {
		this.title = "Неуспешено редактиране";
		this.text = "Данните не бяха успешно редактирани." +
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
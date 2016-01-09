(function() {

	var module = angular.module("ApplicationController");
	
	var DeleteController = function($scope, $routeParams, RestUtil, Destinations) {
		$scope.executeRequest = jQuery.proxy(executeBackendRequest, $scope, $routeParams, RESTUtil, DestinationUtil);
	};
	
	module.controller("DeleteController", ["$scope", "$routeParams", "RestUtil", "Destinations", DeleteController]);
	
	var executeBackendRequest = function($routeParams, RestUtil, Destinations) {
		var requestData = prepareRequestData($routeParams, Destinations);
		RestUtil.DELETE(requestData, jQuery.proxy(onSuccess, this), jQuery.proxy(onError, this));
	};
	
	var prepareRequestData = function($routeParams, Destinations) {
		var entryId = routeParams.id;
		
		return {
			method : "DELETE",
			url : String.format(Destinations.getStatisticDeleteEdnpoint(), entryId)
		};
	};
	
	var onSuccess = function(xhrResponse) {
		this.title = "Успешно изтрити данни.";
		this.resultMessage = "Успешно изтрити данни. Моля изчакайте страницата да бъде презаредена.";
		
		$('#delete-products-result-modal').modal({
			backdrop: "static"
		});
		
		setTimeout(function() {
			location.hash = "#/delete";
		}, 1500);
	};
	
	var onError = function(xhrResponse) {
		this.title = "Неуспешно изтриване.";
		this.resultMessage = "Данните не бяха изтрити." +
			"Статус на грешката: [" + xhrResponse.status + "], хвърлена грешка: [" + xhrResponse.statusText + "]." +
			"Информация от сървъра: [" 
				+ (typeof xhrResponse.headers()["X-Request-Result"] === "undefined" ? "Няма информация" : xhrResponse.headers()["X-Request-Result"]) 
			+ "]";
		
		$('#delete-products-result-modal').modal({ keyboard: true });
	};
	
})();
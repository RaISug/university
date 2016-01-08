(function() {

	var module = angular.module("ApplicationController");
	
	var EditController = function($scope, $routeParams) {
		$scope.buttonText = "Редактирай";
	};
	
	var loadStatisticEntryById = function($scope, id) {
		
	};
	
	module.controller("EditController", ["$scope", "$routeParams", EditController]);
	
})();
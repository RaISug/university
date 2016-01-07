(function() {
	
	var module = angular.module("ApplicationController");
	
	var CreateController = function($scope) {
		$scope.buttonText = "Добави";
	};
	
	module.controller("CreateController", ["$scope", CreateController]);
	
})();
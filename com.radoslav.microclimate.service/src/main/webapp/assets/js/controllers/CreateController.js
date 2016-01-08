(function() {
	
	var module = angular.module("ApplicationController");
	
	var CreateController = function($scope) {
		$scope.buttonText = "Добави";
		$scope.googleMap = {
			center: {
				latitude: 43,
				longitude: 25
			},
			zoom: 6
		};
	};
	
	module.controller("CreateController", ["$scope", CreateController]);
	
})();
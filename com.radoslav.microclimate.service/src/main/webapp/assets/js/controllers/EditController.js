(function() {

	var module = angular.module("ApplicationController");
	
	var EditController = function($scope) {
		$scope.buttonText = "Редактирай";
	};
	
	module.controller("EditController", ["$scope", EditController]);
	
})();
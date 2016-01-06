(function() {
var application = angular.module("ApplicationController", ["ngRoute", "ui.bootstrap"]);
	
	application.config(function($routeProvider) {
		$routeProvider
			.when("/", {
				templateUrl: "assets/html/home.html"
			})
			.when("/create", {
				templateUrl: "assets/html/creation.html",
				controller: "CreateController"
			})
			.when("/edit", {
				templateUrl: "assets/html/edit.html",
				controller: "EditController"
			})
			.when("/delete", {
				templateUrl: "assets/html/delete.html",
				controller: "DeleteController"
			})
			.when("/search", {
				templateUrl: "assets/html/search.html",
				controller: "SearchController"
			})
			.when("/info", {
				templateUrl: "assets/html/info.html"
			})
			.otherwise({ redirectTo: "/" });
	});
	
})();

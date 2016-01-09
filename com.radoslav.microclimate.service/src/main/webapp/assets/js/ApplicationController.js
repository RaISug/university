(function() {
var application = angular.module("ApplicationController", ["ngRoute", "uiGmapgoogle-maps"]);
	
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
				templateUrl: "assets/html/search.html",
				controller: "SearchController"
			})
			.when("/edit/:id", {
				templateUrl: "assets/html/edit.html",
				controller: "EditController"
			})
			.when("/delete", {
				templateUrl: "assets/html/search.html",
				controller: "SearchController"
			})
			.when("/delete/:id", {
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
	
	application.config(function(uiGmapGoogleMapApiProvider) {
	    uiGmapGoogleMapApiProvider.configure({
	        v: '3.20',
	        libraries: 'weather,geometry,visualization'
	    });
	});
	
})();

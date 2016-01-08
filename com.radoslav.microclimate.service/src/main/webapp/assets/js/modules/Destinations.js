(function() {
	var Destinations = function() {
		var SERVER_URL = "http://localhost:8080/microclimate.service/api/v1/";
		
		var STATISTIC_CREATION_ENDPOINT = SERVER_URL + "statistics";
		var STATISTIC_UPDATE_ENDPOINT = SERVER_URL + "statistics";
		var STATISTIC_DELETE_ENDPOINT = SERVER_URL + "statistics";
		var GET_STATISTIC_ENTRIES_ENDPOINT = SERVER_URL + "statistics";
		
		return {
			
			getStatisticCreationEndpoint : function() {
				return STATISTIC_CREATION_ENDPOINT;
			},
			
			getStatisticUpdateEndpoint : function() {
				return STATISTIC_UPDATE_ENDPOINT;
			},
			
			getStatisticDeleteEdnpoint: function() {
				return STATISTIC_DELETE_ENDPOINT;
			},
			
			getStatisticRetrievingEndpoint: function() {
				return GET_STATISTIC_ENTRIES_ENDPOINT;
			}
		};
	};
	
	var module = angular.module("ApplicationController");
	module.factory("Destinations", [Destinations]);
})();
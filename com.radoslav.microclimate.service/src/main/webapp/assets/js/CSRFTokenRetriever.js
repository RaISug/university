var X_XSRF_TOKEN;


(function() {
	$.ajax({
		url: "http:\//localhost:8080/microclimate.service/api/v1/csrf",
        type: "GET",
        cache: false,
        async: false,
        dataType: 'json',
        headers: {
        	"X-XSRF-TOKEN" : "Fetch"
        },
        processData: false,
	})
	.success(function(data, textStatus, jqXhr) {
		X_XSRF_TOKEN = data.csrfToken;
		$("#request-execution-result-dialog").modal({
			keyboard: true
		});
	})
	.error(function(jqXhr, textStatus, errorThrown) {
		$("#error-dialog").modal({
			backdrop: "static"
		});
	});
})();
com.intercity.Utils = function() {

};

com.intercity.Utils.prototype.POST = function(oInput) {
	$.ajax({
		type: "POST",
		url: oInput.url,
		dataType: typeof oInput.dataType === 'undefined' ? 'json' : oInput.dataType,
		async: typeof oInput.async === 'undefined' ? true : oInput.async,
		data: typeof oInput.data === 'undefined' ? '' : JSON.stringify(oInput.data),
		headers: typeof oInput.headers === 'undefined' ? {} : oInput.headers,
		beforeSend: typeof oInput.header !== 'undefined' ? oInput.beforeSend : function(request){
		},
		contentType: typeof oInput.contentType === 'undefined' ? 'application/x-www-form-urlencoded; charset=UTF-8' : oInput.contentType,
		success: oInput.success,
		error: function(jqXHR, textStatus, errorThrown) {
			alert('jqXHR : ' + jqXHR + ' textStatus : ' + textStatus + ' errorThrown : ' + errorThrown);
		}
	});
};

com.intercity.Utils.prototype.GET = function(oInput) {
	$.ajax({
		type: "GET",
		url: oInput.url,
		dataType: typeof oInput.dataType === 'undefined' ? 'json' : oInput.dataType,
		async: typeof oInput.async === 'undefined' ? true : oInput.async,
		success: oInput.success,
		error: typeof oInput.error === 'function' ? oInput.error : function(jqXHR, textStatus, errorThrown) {
			alert('jqXHR : ' + jqXHR + ' textStatus : ' + textStatus + ' errorThrown : ' + errorThrown);
		}
	});
};

//@ sourceURL=Utils.js
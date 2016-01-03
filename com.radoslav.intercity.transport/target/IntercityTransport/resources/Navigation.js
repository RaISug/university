com.intercity.Navigation = function() {};

com.intercity.Navigation.prototype.loadContent = function() {
	var oModel = com.intercity.Model.prototype.getModel();
	
	$(oModel.getProperty('content')).append("<ul class='navbar'>" +
				"<a href='#/registerVehicle'>Регистрация на превозни средства</a>" +
				"<a href='#/registerRoute'>Регистрация на маршрути</a>" +
				"<a href='#/registerDriver'>Регистрация на шофьори</a>" +
				"<a href='#/logout' class='logout'>Изход</a>" +
			"</ul>");
	
	$(oModel.getProperty('content')).append("<div class='container'></div>");
};

//@ sourceURL=Navigation.js
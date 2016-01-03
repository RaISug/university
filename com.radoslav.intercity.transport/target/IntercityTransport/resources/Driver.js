com.intercity.Driver = function() {};

com.intercity.Driver.prototype.loadContent = function() {
	var oModel = com.intercity.Model.prototype.getModel();
	var container = oModel.getProperty('container');
	
	this._addForm(container);
	this._addFormListener();
};

com.intercity.Driver.prototype._addForm = function(container) {
	$(container).append(
		"<div align='center'>" +
			"<p>Регистрирайте вашите шофьори : </p>" +
			"<form class='route-registration-form' method='POST'>" +
				"<table>" +
					"<tr>" +
						"<td>Модел : </td>" +
						"<td><input type='text' name='model'/></td>" +
					"</tr>" +
					"<tr>" +
						"<td>Капацитет : </td>" +
						"<td><input type='number' name='capacity' min='1' max='51' placeholder='Капацитет' value='1'/></td>" +
					"</tr>" +
					"<tr>" +
						"<td>Вид : </td>" +
						"<td>" +
							"<select>" +
								"<option value=0>Изберете опция</option>" +
								"<option value=1>Автобус</option>" +
							"</select>" +
						"</td>" +
					"</tr>" +
					"<tr>" +
						"<td>Описание : </td>" +
						"<td><textarea name='capacity' cols='40' rows='5' placeholder='Описание'></textarea></td>" +
					"</tr>" +
					"<tr>" +
						"<td></td>" +
						"<td><input type='submit' value='Регистрирай'/></td>" +
					"</tr>" +
				"</table>" +
			"</form>" +
		"</div>"
	);
};

com.intercity.Driver.prototype._addFormListener = function() {
	$(".route-registration-form").submit(function(oEvent) {
		alert('Hello From Driver');
		oEvent.preventDefault();
	});
};

//@ sourceURL=Driver.js
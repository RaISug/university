com.intercity.Vehicle = function() {};

com.intercity.Vehicle.prototype.loadContent = function() {
	var oModel = com.intercity.Model.prototype.getModel();
	var container = oModel.getProperty('container');
	
	this._addForm(container);
	this._addFormListener();
};

com.intercity.Vehicle.prototype._addForm = function(container) {
	$(container).append(
		"<div align='center'>" +
			"<p>Регистрирайте вашето превозно средство : </p>" +
			"<form class='vehicle-registration-form' method='POST'>" +
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
							"<select name='type'>" +
								"<option value=0>Изберете опция</option>" +
								"<option value=1>Автобус</option>" +
							"</select>" +
						"</td>" +
					"</tr>" +
					"<tr>" +
						"<td>Описание : </td>" +
						"<td><textarea name='description' cols='40' rows='5' placeholder='Описание'></textarea></td>" +
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

com.intercity.Vehicle.prototype._addFormListener = function() {
	$(".vehicle-registration-form").submit(function(oEvent) {
		var inputFieldValues = {};
		
		$.each($('.vehicle-registration-form').serializeArray(), function(index, field) {
			inputFieldValues[field.name] = field.value;
		});
		
		var oInput = {
			url: "api/v1/management/vehicle",
			data: JSON.stringify(inputFieldValues),
			contentType: 'application/json',
			success: function(responseData) {
				alert(responseData);
			}
		};
		com.intercity.Utils.prototype.POST(oInput);
		oEvent.preventDefault();
	});
}

//@ sourceURL=Vehicle.js
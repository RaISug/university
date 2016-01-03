sap.ui.controller("services.UserList", {

	onInit: function() {
		var that = this;
		that.oData = {
				model : {},
		};
		that.updateModel();
	},
	updateModel : function() {
		var that = this;
		
		$.ajax({
			method : 'GET',
			url : '/RestService/rest/api/v1/users',
			async : false,
			success : function(oData) {
				console.log("success");
				jQuery.extend(that.oData.model, oData);
				that._setModel(oData);
			},
			error : function() {
				console.log("error");
			}
		});
		
	},
	_setModel : function(oData) {
		var oModel = new sap.ui.model.json.JSONModel(oData);
		this.getView().setModel(oModel);
	},
	onListItemPress: function() {
		console.log("2");
	},
	onBack: function() {
		PageUtils.navToPreviousPage();
	},
	onSelectedUser : function(oEvent) {
		console.log(1);
	},
});
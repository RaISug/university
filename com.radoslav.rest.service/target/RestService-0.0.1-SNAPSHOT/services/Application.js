jQuery.sap.declare("Application");

Application = function() {};


Application.prototype.startApplication = function() {
	var app = new sap.m.App();
	window.app = app;
	
	var oPage = this._createDashboard();
	
	app.addPage(oPage);
	
	var oShell = new sap.m.Shell({
		app : app,
		title : 'WebService'
	});
	
	oShell.placeAt("content");
	app.to(oPage.getId());
};

Application.prototype._createDashboard = function() {
	var that = this;
	
	that.oTileContainer = new sap.m.TileContainer({
		height : "50%",
		tiles : new sap.m.StandardTile({
			title : 'User List',
			icon : 'sap-icon://list',
			press : function() {
				var oView = sap.ui.view({viewName: 'services.UserList', type:sap.ui.core.mvc.ViewType.JS});
				window.app.addPage(oView);
				window.app.to(oView.getId());
			},
		}),
	});
	
	var oPage = new sap.m.Page({
		enableScrolling : false,
		title : 'Web Services',
		content : [that.oTileContainer]
	});
	
	return oPage;
}
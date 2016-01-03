jQuery.sap.declare("Utils");

PageUtils = {
	navToPreviousPage : function() {
		var app = window.app;
		var oPage = app.getCurrentPage();

		app.back();
		app.removePage(oPage);
		oPage.destroy();
	}
};
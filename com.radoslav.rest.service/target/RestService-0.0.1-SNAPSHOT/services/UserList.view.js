sap.ui.jsview("services.UserList", {

	getControllerName : function() {
		return "services.UserList";
	},
	
	createContent : function(oController) {
		var that = this;
		
		that.oListItemTemplate = new sap.m.ObjectListItem({
			title: 'User Properties',
			attributes: [
				new sap.m.ObjectAttribute({
					title: 'ID',
					text: '{id}',
				}),
				new sap.m.ObjectAttribute({
					title: 'User Name',
					text: '{userName}',
				}),
				new sap.m.ObjectAttribute({
					title: 'Real Name',
					text: '{realName}',
				}),
				new sap.m.ObjectAttribute({
					title: 'Faculty',
					text: '{faculty}',
				}),
				new sap.m.ObjectAttribute({
					title: 'Speciality',
					text: '{speciality}',
				}),	
			],
			press : function(){
				var oCtx = this.getBindingContext();
				oController.onSelectedUser(oCtx.getObject());
			}, 
		});
		
		that.oList = new sap.m.List({
            growing : true,
            growingScrollToLoad : true,
		});
		
		that.oList.bindItems('/', that.oListItemTemplate);
		
 		return new sap.m.Page({
			title: "User List",
			content: [
			     that.oList
			],
			showNavButton: true,
			navButtonPress: [oController.onBack, oController],
		});
	}

});
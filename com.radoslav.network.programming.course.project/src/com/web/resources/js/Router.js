var com = {

};

com.Router = function() {};

com.Router.prototype.start = function() {
    window.
    window.addEventListener("hashchange", function(oEvent) {
        var sURL = oEvent.newURL;

        this._navigate(sURL.substring(sURL.lastIndexOf('#') + 1));
    }.bind(this));

    if (window.location.hash) {
        var temp = window.location.hash;
        window.location.hash = "#/";
        window.location.hash = temp;
    }
};

com.Router.prototype._navigate = function(sPath) {
    for (var i = 0 ; i < views.length ; i++) {
        if (views[i].match(sPath))  {
            var oView = views[i].createInstance();

            oView.loadContent();
        }
    }
};

var views = [
    {
        path: '/login',
        match: function(sPath) {
            return this.path === sPath;
        },
        createInstance: function() {
            return new com.Login();
        }
    },
    {
        path: '/connections',
        match: function(sPath) {
            return this.path === sPath;
        },
        createInstance: function() {
            return new com.Connections();
        }
    },
    {
        path: '/home',
        match: function(sPath) {
            return this.path === sPath;
        },
        createInstance: function() {
            return new com.Home();
        }
    },
    {
        path: '/chat',
        match: function(sPath) {
            var aResult = sPath.match(/\/chat\/(.*)?\/(.*)/);
            if (!aResult) {
                return false;
            }

            this.identifier = aResult[1];
            this.username = aResult[2];

            return this.identifier;
        },
        createInstance: function() {
            return new com.Chat(this.username, this.identifier);
        }
    }
]
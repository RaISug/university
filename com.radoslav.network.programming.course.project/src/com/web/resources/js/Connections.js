com.Connections = function() {};

com.Connections.prototype.loadContent = function() {
    this._loadConnections();
};

com.Connections.prototype._loadConnections = function() {
    var executor = new com.Executor();

    executor.GET("/connections", this._renderContent);
};

com.Connections.prototype._renderContent = function(oResponse) {
    var sContent = "<table>";
    sContent += "<tr><th>User</th><th></th></tr>";

    var oConnections = JSON.parse(oResponse.responseText);

    for (var i = 0 ; i < oConnections.length ; i++) {
        sContent += "<tr>";
        sContent += "<td>" + oConnections[i].username + "</td>";
        sContent += "<td><a href=\"#/chat/" + oConnections[i].identifier + "/" + oConnections[i].username + "\">Start Chat</a></td>";
        sContent += "</tr>";
    }

    sContent += "</table>";

    var oElement = com.Common.getContainer();

    oElement.innerHTML = sContent;
};

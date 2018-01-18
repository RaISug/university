com.Executor = function() {};

com.Executor.prototype.GET = function(sPath, fCallback) {
    var request = this._createRequest(fCallback);

    request.open("GET", "http://localhost:9889" + sPath, true);
    request.send(null);
};

com.Executor.prototype.POST = function(sPath, fCallback, oBody) {
    var request = this._createRequest(fCallback);

    request.open("POST", "http://localhost:9889" + sPath, true);
    request.send(oBody);
};

com.Executor.prototype._createRequest = function(fCallback) {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if (request.readyState == 4) {
            fCallback(request);
        }
    };

    return request;
};
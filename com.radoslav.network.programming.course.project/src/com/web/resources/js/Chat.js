com.Chat = function(sUsername, sIdentifier) {
    com.Chat.username = sUsername;
    com.Chat.userIdentifier = sIdentifier;
};

com.Chat.prototype.loadContent = function() {
    this._fetchMessages();
    this._renderDefaultContent();
    this._cleanTextAreaOnMessageSend();
    this._poolMessages();
    this._scrollOnBottomOfChatArea();
};

com.Chat.prototype._fetchMessages = function() {
    var executor = new com.Executor();

    executor.GET("/chat?user=" + com.Chat.userIdentifier, this._renderMessages);
};

com.Chat.prototype._renderDefaultContent = function() {
    var sContent = "<div style='text-align: center;'><b>User:</b> " + com.Chat.username + "</div>";
    sContent += "<div id='conversation-messages' class='conversation'></div>";
    sContent += "<input id='message-area' type='textarea' class='messageArea'/>";
    sContent += "<br>";
    sContent += "<button id='send-message-button' class='messageButton' onclick='com.Chat.prototype.send()'>Send</button>";

    var element = com.Common.getContainer();
    element.innerHTML = sContent;
};

com.Chat.prototype._cleanTextAreaOnMessageSend = function() {
    var textArea = document.getElementById("message-area");

    var sendMessageButton = document.getElementById("send-message-button");
    sendMessageButton.addEventListener("click", function() {
        textArea.value = "";
    });

    document.onkeypress = function(event) {
        if (event.which == 13 || event.keyCode == 13) {
            sendMessageButton.click();
            textArea.value = "";
        }
    };
};

com.Chat.prototype._poolMessages = function() {
    setInterval(function() {
        this._fetchMessages();
    }.bind(this), 200);
};

com.Chat.prototype._scrollOnBottomOfChatArea = function() {
    setInterval(function() {
        var element = document.getElementById("conversation-messages");
        element.scrollTop = element.scrollHeight;
    }, 50);
};

com.Chat.prototype._renderMessages = function(oResponse) {
    var element = document.getElementById("conversation-messages");

    element.innerHTML = oResponse.responseText;
};

com.Chat.prototype.send = function() {
    var oTextArea = document.getElementById("message-area");

    var executor = new com.Executor();

    executor.POST("/chat?user=" + com.Chat.userIdentifier, function() {}, oTextArea.value);
};
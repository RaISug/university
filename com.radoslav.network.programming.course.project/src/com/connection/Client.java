package com.connection;

import com.google.gson.annotations.Expose;

import java.util.*;

public class Client {

    @Expose
    private String username;
    @Expose
    private String identifier;

    private Map<String, List<Message>> messages;

    public Client(String username, String identifier) {
        this.username = username;
        this.identifier = identifier;

        this.messages = new HashMap<>();
    }

    public String username() {
        return username;
    }

    public String identifier() {
        return identifier;
    }

    public void send(Client to, String message) {
        List<Message> clientMessages = getMessagesFrom(to);
        clientMessages.add(new Message(this, message));

        to.receive(this, message);
    }

    private List<Message> getMessagesFrom(Client client) {
        List<Message> clientMessages = messages.get(client.identifier());
        if (clientMessages == null) {
            clientMessages = new LinkedList<>();

            messages.put(client.identifier(), clientMessages);
        }

        return clientMessages;
    }

    private void receive(Client from, String message) {
        List<Message> clientMessages = getMessagesFrom(from);
        clientMessages.add(new Message(from, message));
    }

    public String getMessagesFromClientAsHtml(Client client) {
        StringBuilder builder = new StringBuilder();

        List<Message> clientMessages = getMessagesFrom(client);
        for (Message message : clientMessages) {
            builder.append(message.toHtml());
        }

        return builder.toString();
    }

    private class Message {

        private Client client;
        private String message;

        public Message(Client client, String message) {
            this.client = client;
            this.message = message;
        }

        public String toHtml() {
            StringBuilder builder = new StringBuilder();

            builder.append("<div class='");
            builder.append(identifier.equals(client.identifier()) ? "senderMessage" : "recipientMessage");
            builder.append("'>");
            builder.append("<b>");
            builder.append(identifier.equals(client.identifier()) ? "me" : client.username());
            builder.append(" says:</b>");
            builder.append("<br/>").append(message);
            builder.append("</div>");

            return builder.toString();
        }
    }
}

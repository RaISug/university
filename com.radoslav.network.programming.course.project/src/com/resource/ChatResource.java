package com.resource;

import com.connection.ActiveConnections;
import com.connection.Client;
import com.exception.ApplicationException;
import com.request.Request;
import com.resource.method.Method;
import com.response.Response;

import java.util.LinkedList;
import java.util.List;

public class ChatResource implements Resource {

    private static final String PATH = "/chat";

    private List<Method> methods;

    public ChatResource() {
        methods = new LinkedList<>();

        methods.add(new Get());
        methods.add(new Post());
    }

    @Override
    public boolean match(Request request) {
        return PATH.equals(request.path());
    }

    @Override
    public void process(Request request) {
        for (Method method : methods) {
            if (method.match(request.method())) {
                method.process(request);

                return;
            }
        }

        throw new ApplicationException("Method not matched.");
    }

    public class Get implements Method {

        @Override
        public boolean match(String method) {
            return "GET".equals(method);
        }

        @Override
        public void process(Request request) {
            ActiveConnections connections = new ActiveConnections();

            Client current = connections.getClient(request.getCurrentUserIdentifier());
            Client from = connections.getClient(request.getQueryParam("user"));

            String responseBody = current.getMessagesFromClientAsHtml(from);

            request.respondWith(Response.Builder.respondWith().statusOk().build(responseBody));
        }
    }

    public class Post implements Method {

        @Override
        public boolean match(String type) {
            return "POST".equals(type);
        }

        @Override
        public void process(Request request) {
            ActiveConnections connections = new ActiveConnections();

            Client from = connections.getClient(request.getCurrentUserIdentifier());
            Client to = connections.getClient(request.getQueryParam("user"));

            String message = request.body();
            from.send(to, message);

            request.respondWith(Response.Builder.respondWith().statusOk().build());
        }
    }
}

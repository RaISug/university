package com.resource;

import com.connection.ActiveConnections;
import com.connection.Client;
import com.exception.ApplicationException;
import com.request.Request;
import com.resource.method.Method;
import com.response.Response;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class LoginResource implements Resource {

    private List<Method> methods;

    public LoginResource() {
        methods = new LinkedList<>();

        methods.add(new Get());
        methods.add(new Post());
    }

    @Override
    public boolean match(Request request) {
        return "/login.html".equals(request.path());
    }

    @Override
    public void process(Request request) {
        if (request.hasActiveSession()) {
            redirectToHomePage(request);
            return;
        }

        for (Method method : methods) {
            if (method.match(request.method())) {
                method.process(request);

                return;
            }
        }

        throw new ApplicationException("Method not matched.");
    }

    private void redirectToHomePage(Request request) {
        Response response = Response.Builder
                .respondWith()
                .statusPermanentRedirect()
                .redirectTo("index.html").build();

        request.respondWith(response);
    }

    private static class Get implements Method {

        @Override
        public boolean match(String type) {
            return "GET".equals(type);
        }

        @Override
        public void process(Request request) {
            request.respondWith(Response.Builder.respondWith().contentTypeTextHtml().build(request.path()));
        }
    }

    private static class Post implements Method {

        @Override
        public boolean match(String type) {
            return "POST".equals(type);
        }

        @Override
        public void process(Request request) {
            String username = request.getFormParam("username");
            String identifier = UUID.randomUUID().toString();

            ActiveConnections connections = new ActiveConnections();
            connections.connect(new Client(username, identifier));

            System.out.println("User with name: " + username + " successfully logged in.");

            request.respondWith(createResponse(identifier));
        }

        private Response createResponse(String identifier) {
            return Response.Builder
                    .respondWith()
                    .statusPermanentRedirect()
                    .addCookie("User-Identifier", identifier)
                    .redirectTo("index.html").build();
        }
    }
}

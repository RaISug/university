package com.resource;

import com.connection.ActiveConnections;
import com.connection.Client;
import com.exception.ApplicationException;
import com.request.Request;
import com.resource.method.Method;
import com.response.Response;

import java.util.LinkedList;
import java.util.List;

public class ConnectionResource implements Resource {

    private static final String PATH = "/connections";

    private List<Method> methods;

    public ConnectionResource() {
        methods = new LinkedList<>();

        methods.add(new Get());
        methods.add(new Delete());
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

            request.respondWith(createResponse(connections.getAllExcept(request.getCurrentUserIdentifier())));
        }

        private Response createResponse(List<Client> clients) {
            return Response.Builder
                    .respondWith().statusOk()
                    .contentTypeApplicationJson().build(clients);
        }
    }

    public class Delete implements Method {

        @Override
        public boolean match(String type) {
            return "DELETE".equals(type);
        }

        @Override
        public void process(Request request) {
            ActiveConnections connections = new ActiveConnections();

            connections.disconnect(connections.getClient(request.getCurrentUserIdentifier()));
        }
    }
}

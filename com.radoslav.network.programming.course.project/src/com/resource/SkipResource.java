package com.resource;

import com.request.Request;
import com.response.Response;

public class SkipResource implements Resource {

    @Override
    public boolean match(Request request) {
        return "/favicon.ico".equals(request.path());
    }

    @Override
    public void process(Request request) {
        request.respondWith(Response.Builder.respondWith().statusNotFound().build());
    }

}

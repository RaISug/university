package com.resource;

import com.request.Request;
import com.response.Response;

public class CssResource implements Resource {

    @Override
    public boolean match(Request request) {
        return request.path().endsWith(".css");
    }

    @Override
    public void process(Request request) {
        request.respondWith(Response.Builder.respondWith().contentTypeTextHtml().build(request.path()));
    }
}

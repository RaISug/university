package com.resource;

import com.request.Request;
import com.response.Response;

public class JsResource implements Resource {

    @Override
    public boolean match(Request request) {
        return request.path().endsWith(".js");
    }

    @Override
    public void process(Request request) {
        request.respondWith(Response.Builder.respondWith().contentTypeTextHtml().build(request.path()));
    }
}

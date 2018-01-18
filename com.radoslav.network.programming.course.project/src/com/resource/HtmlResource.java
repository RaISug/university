package com.resource;

import com.request.Request;
import com.resource.Resource;
import com.response.Response;

public class HtmlResource implements Resource {

    @Override
    public boolean match(Request request) {
        return endsWithHtml(request) && isNotLoginPage(request);
    }

    private boolean endsWithHtml(Request request) {
        return request.path().endsWith(".html");
    }

    private boolean isNotLoginPage(Request request) {
        return "/login.html".equals(request.path()) == false;
    }

    @Override
    public void process(Request request) {
        request.respondWith(Response.Builder.respondWith().contentTypeTextHtml().build(request.path()));
    }
}

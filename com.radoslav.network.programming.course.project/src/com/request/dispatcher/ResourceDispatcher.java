package com.request.dispatcher;

import com.exception.ApplicationException;
import com.request.Request;
import com.resource.*;

import java.util.LinkedList;
import java.util.List;

public class ResourceDispatcher {

    private static ResourceDispatcher dispatcher;

    private List<Resource> resources;

    private ResourceDispatcher() {
        resources = new LinkedList<>();

        resources.add(new ConnectionResource());
        resources.add(new HtmlResource());
        resources.add(new JsResource());
        resources.add(new SkipResource());
        resources.add(new ChatResource());
        resources.add(new CssResource());
        resources.add(new LoginResource());
    }

    public static ResourceDispatcher getInstance() {
        if (dispatcher == null) {
            dispatcher = new ResourceDispatcher();
        }

        return dispatcher;
    }

    public void dispatch(Request request) {
        for (Resource resource : resources) {
            if (resource.match(request)) {
                System.out.println("Request matched.");

                resource.process(request);

                return;
            }
        }

        throw new ApplicationException("Resourse not matched.");
    }
}

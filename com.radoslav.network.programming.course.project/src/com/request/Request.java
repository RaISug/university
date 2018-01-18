package com.request;

import com.response.Response;

public interface Request {

    void serve();

    String path();

    String method();

    void respondWith(Response response);

    void close();

    boolean hasActiveSession();

    boolean hasNoActiveSession();

    String body();

    String getCurrentUserIdentifier();

    String getQueryParam(String paramName);

    String getFormParam(String paramName);
}

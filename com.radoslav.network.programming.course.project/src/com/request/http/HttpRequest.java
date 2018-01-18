package com.request.http;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.connection.ActiveConnections;
import com.exception.ApplicationException;
import com.response.Response;
import com.request.Request;
import com.request.dispatcher.ResourceDispatcher;

public class HttpRequest implements Request {

    private final Socket socket;
    private final ResourceDispatcher dispatcher;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    private String body;
    private String path;
    private String method;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private Map<String, String> formParams;

    public HttpRequest(Socket socket) {
        this.socket = socket;
        this.dispatcher = ResourceDispatcher.getInstance();

        try {
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new ApplicationException("Failed to aquire input or ouput stream.", e);
        }
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public String method() {
        return method;
    }

    public String body() {
        return body;
    }

    @Override
    public void serve() {
        parseRequest();

        if (isNotLoginPage() && hasNoActiveSession()) {
            redirectToLoginPage();
            return;
        }

        dispatcher.dispatch(this);
    }

    private void parseRequest() {
        try {
            parseRequest(new BufferedReader(new InputStreamReader(inputStream)));
        } catch (IOException e) {
            throw new ApplicationException("Failed to read whole request.", e);
        }
    }

    private void parseRequest(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new ApplicationException("Empty line.");
        }

        this.method = extractMethod(requestLine);
        this.path = extractPath(requestLine);
        this.headers = extractHeaders(reader);
        this.queryParams = extractQueryParams(requestLine);

        if (isFormRequest()) {
            this.formParams = extractFormParams(reader);
        } else if (isPostRequest()) {
            this.body = extractBody(reader);
        }

        System.out.println(toString());
    }

    private boolean isFormRequest() {
        return "application/x-www-form-urlencoded".equals(headers.get("Content-Type"));
    }

    private Map<String,String> extractFormParams(BufferedReader reader) throws IOException {
        String[] params = extractBody(reader).split("&");

        Map<String, String> formParams = new HashMap<>();
        for (String param : params) {
            String key = param.split("=")[0];
            String value = param.split("=")[1];

            formParams.put(key, value);
        }

        return formParams;
    }

    private String extractMethod(String requestLine) {
        return requestLine.split(" ")[0];
    }

    private String extractPath(String requestLine) {
        String fullPath = requestLine.split(" ")[1];
        if (fullPath.contains("?")) {
            return fullPath.split("\\?")[0];
        }

        return fullPath;
    }

    private Map<String, String> extractHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String requestLine;
        while ((requestLine = reader.readLine()) != null) {
            if (requestLine.isEmpty()) {
                break;
            }

            String headerKey = extractHeaderKey(requestLine);
            String headerValue = extractHeaderValue(requestLine);

            headers.put(headerKey, headerValue);
        }

        return headers;
    }

    private String extractHeaderKey(String requestLine) {
        return requestLine.split(":")[0];
    }

    private String extractHeaderValue(String requestLine) {
        return requestLine.split(":")[1].substring(1);
    }

    private Map<String, String> extractQueryParams(String requestLine) {
        Map<String, String> queryParams = new HashMap<>();

        String fullPath = requestLine.split(" ")[1];
        if (fullPath.contains("?")) {
            String[] params = fullPath.split("\\?")[1].split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");

                queryParams.put(keyValue[0], keyValue[1]);
            }
        }

        return queryParams;
    }

    private boolean isPostRequest() {
        return method.equals("POST");
    }

    private String extractBody(BufferedReader reader) throws IOException {
        int contentLength = getContentLength();

        char[] body = new char[contentLength];
        reader.read(body, 0, contentLength);

        return new String(body);
    }

    private int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    private void redirectToLoginPage() {
        respondWith(Response.Builder.respondWith().statusPermanentRedirect().redirectTo("login.html").build());
    }

    @Override
    public boolean hasActiveSession() {
        return hasNoActiveSession() == false;
    }

    @Override
    public boolean hasNoActiveSession() {
        String cookieHeader = headers.get("Cookie");
        if (cookieHeader == null) {
            return true;
        }

        String[] cookies = cookieHeader.split(";");
        for (String cookie : cookies) {
            String key = cookie.split("=")[0];
            String value = cookie.split("=")[1];

            if (key.equals("User-Identifier")) {
                return new ActiveConnections().contains(value) == false;
            }
        }

        return true;
    }

    private boolean isNotLoginPage() {
        return "/login.html".equals(path) == false;
    }

    @Override
    public String getCurrentUserIdentifier() {
        return getCookieValue("User-Identifier");
    }

    @Override
    public String getQueryParam(String paramName) {
        return queryParams.get(paramName);
    }

    @Override
    public String getFormParam(String paramName) {
        return formParams.get(paramName);
    }

    private String getCookieValue(String cookieName) {
        String cookieHeader = headers.get("Cookie");
        if (cookieHeader == null) {
            return null;
        }

        String[] cookies = cookieHeader.split(";");
        for (String cookie : cookies) {
            String key = cookie.split("=")[0];
            String value = cookie.split("=")[1];

            if (key.equals(cookieName)) {
                return value;
            }
        }

        return null;
    }

    @Override
    public void respondWith(Response response) {
        response.writeTo(new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)));
    }

    @Override
    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("HttpRequest:[\n");
        builder.append("\tPath: ").append(method).append(" ").append(path).append("\n");

        builder.append("\tHeaders: ").append("\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder.append("\t\t").append(header.getKey()).append(": ").append(header.getValue()).append("\n");
        }

        builder.append("\tQuery params: ").append(queryParams).append("\n");
        builder.append("\tForm params: ").append(formParams).append("\n");
        builder.append("\tBody: ").append(body).append("\n");
        builder.append("]");

        return builder.toString();
    }
}

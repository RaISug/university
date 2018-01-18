package com.response;

import com.parser.JsonParser;
import com.reader.FileReader;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Response<T> {
    private T response;

    private String status;
    private Map<String, String> headers;

    private Response(T response, String status, Map<String, String> headers) {
        this.response = response;
        this.status = status;
        this.headers = headers;
    }

    public void addCookie(String name, String value) {
        headers.put("Set-Cookie", name + "=" + value);
    }

    public void writeTo(PrintWriter writer) {
        writer.println("HTTP/1.1 " + status);

        writeHeaders(writer);

        if (response != null) {
            String responseBody = toString(response);
            writer.println("Content-Length: " + responseBody.length());
            writer.println();
            writer.println(responseBody);
        }

        writer.flush();
    }

    private void writeHeaders(PrintWriter writer) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            writer.println(header.getKey() + ": " +header.getValue());
        }
    }

    private String toString(T response) {
        if (isTextHtmlResponseType()) {
            return new FileReader((String) response).readContent();
        } else if (isApplicationJsonResponseType()) {
            return toJson(response);
        }

        return (String) response;
    }

    private boolean isTextHtmlResponseType() {
        return headers.get("Content-Type") != null && headers.get("Content-Type").startsWith("text/html");
    }

    private boolean isApplicationJsonResponseType() {
        return headers.get("Content-Type") != null && headers.get("Content-Type").startsWith("application/json");
    }

    private String toJson(T object) {
        return JsonParser.toJson(object);
    }

    public static class Builder {

        private String status;
        private Map<String, String> headers;

        public Builder() {
            headers = new HashMap<>();
        }

        public static Builder respondWith() {
            return new Builder();
        }

        public Builder statusNotFound() {
            this.status = "404 Not Found";

            return this;
        }

        public Builder statusOk() {
            this.status = "200 OK";

            return this;
        }

        public Builder statusUnauthorized() {
            this.status = "401 Unauthorized";

            return this;
        }

        public Builder statusPermanentRedirect() {
            this.status = "301 Permanent Redirect";

            return this;
        }

        public Builder contentTypeApplicationJson() {
            headers.put("Content-Type", "application/json; charset=UTF-8");

            return this;
        }

        public Builder contentTypeTextHtml() {
            headers.put("Content-Type", "text/html; charset=UTF-8");

            return this;
        }

        public Builder addCookie(String name, String value) {
            headers.put("Set-Cookie", name + "=" + value);

            return this;
        }

        public Builder putHeader(String key, String value) {
            headers.put(key, value);

            return this;
        }

        public Builder redirectTo(String fileName) {
            headers.put("Location", "http://localhost:9889/" + fileName);

            return this;
        }

        public <T> Response<T> build(T response) {
            return new Response<T>(response, status, headers);
        }

        public <T> Response<T> build() {
            return new Response<T>(null, status, headers);
        }
    }
}

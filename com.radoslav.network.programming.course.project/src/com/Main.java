package com;

import com.server.http.HttpServer;

public class Main {

    public static void main(String[] arguments) {
        HttpServer server = HttpServer.start();

        System.out.println("System started listening on port: " + 9889);

        server.startHandlers();
        server.listen();
    }
}

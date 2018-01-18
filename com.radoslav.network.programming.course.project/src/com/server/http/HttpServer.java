package com.server.http;

import com.exception.ApplicationException;
import com.exception.ServerException;
import com.request.http.HttpRequest;
import com.request.pool.RequestPool;
import com.thread.pool.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private static final int PORT = 9889;
    private static final int THREAD_POOL_CAPACITY = 5;

    private final ServerSocket serverSocket;
    private final RequestPool pool;

    private HttpServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.pool = RequestPool.getInstance();
    }

    public static HttpServer start() {
        try {
            return new HttpServer(new ServerSocket(PORT));
        } catch (IOException e) {
            throw new ServerException("Failed to start server on port: " + PORT);
        }
    }

    public void startHandlers() {
        new ThreadPool(THREAD_POOL_CAPACITY).startHandlers();
    }

    public void listen() {
        try {
            listenForIncommingRequests();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new ServerException("Failed to close socket");
            }
        }
    }

    private void listenForIncommingRequests() {
        while (true) {
            Socket socket = accept();

            System.out.println("New connection accepted");

            pool.add(new HttpRequest(socket));
        }
    }

    private Socket accept() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new ApplicationException("Failed to accept connection", e);
        }
    }
}

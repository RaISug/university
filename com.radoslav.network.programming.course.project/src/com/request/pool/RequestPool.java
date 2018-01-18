package com.request.pool;

import com.request.Request;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestPool {

    public final BlockingQueue<Request> pool;

    private static RequestPool instance;

    private RequestPool() {
        pool = new LinkedBlockingQueue<>();
    }

    public static RequestPool getInstance() {
        if (instance == null) {
            instance = new RequestPool();
        }

        return instance;
    }

    public void add(Request request) {
        pool.add(request);
    }

    public Request take() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread was interrupted.");
        }
    }
}

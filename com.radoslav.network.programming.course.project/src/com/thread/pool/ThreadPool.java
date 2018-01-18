package com.thread.pool;

import com.request.pool.RequestPool;
import com.thread.handler.RequestHandler;

public class ThreadPool {

    private int capacity;

    public ThreadPool(int capacity) {
        this.capacity = capacity;
    }

    public void startHandlers() {
        for (int i = 0 ; i < capacity ; i++) {
            new Thread(new RequestHandler(RequestPool.getInstance())).start();
        }
    }
}

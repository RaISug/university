package com.thread.handler;

import com.request.Request;
import com.request.pool.RequestPool;

public class RequestHandler implements Runnable {

    private final RequestPool pool;

    public RequestHandler(RequestPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {
            Request request = null;
            try {
                request = pool.take();

                System.out.println("Request accepted for processing.");

                request.serve();
            } catch (Exception exception) {
                System.out.println("Exception occured: " + exception);

                exception.printStackTrace();
            } finally {
                if (request != null) {
                    request.close();
                }
            }
        }
    }

}

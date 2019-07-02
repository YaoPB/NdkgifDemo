package com.yaopb.lightingimage;

import java.util.concurrent.LinkedBlockingDeque;

public class RequestManager {
    private static RequestManager requestManager = new RequestManager();
    private LinkedBlockingDeque<BitmapRequest> resquestQuqe = new LinkedBlockingDeque<>();

    /*
     * 定义一个线程数组
     */
    private BitmapDispatcher[] bitmapDispatchers;

    public RequestManager() {
        start();
    }

    private void start() {
        stop();
        startAllDispatcher();
    }

    private void stop() {
        if (bitmapDispatchers != null && bitmapDispatchers.length > 0) {
            for (BitmapDispatcher bitmapDispatcher : bitmapDispatchers) {
                if (bitmapDispatcher.isInterrupted()) {
                    bitmapDispatcher.isInterrupted();
                }
            }
        }
    }

    private void startAllDispatcher() {
        //获取手机支持支持的单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(resquestQuqe);
            bitmapDispatcher.start();

            //将所有的dispatcher存放到数组中，方便统一管理
            bitmapDispatchers[i] = bitmapDispatcher;
        }
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    //将图片请求添加队列
    public void addBitMapRequest(BitmapRequest bitmapRequest) {
        if (bitmapRequest == null) {
            return;
        }
        if (!resquestQuqe.contains(bitmapRequest)) {
            resquestQuqe.add(bitmapRequest);
        }
    }
}

package com.yaopb.gifdemo;

import android.graphics.Bitmap;

public class GifHandler {
    static {
        System.loadLibrary("pl_droidsonroids_gif");
    }

    // 加载GIF
    private volatile long gifinfo;

    public GifHandler(String path) {
        gifinfo = openFile(path);
    }

    public synchronized int getWidth() {
        return getWidth(gifinfo);
    }


    public synchronized int getHeight() {
        return getHeight(gifinfo);
    }

    public long renderFrame(Bitmap bitmap) {
        return renderFrame(gifinfo, bitmap);
    }

    public native long openFile(String path);

    public native int getWidth(long gifinfo);

    public native int getHeight(long gifinfo);

    public native long renderFrame(long gifinfo, Bitmap bitmap);

}

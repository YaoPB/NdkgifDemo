package com.yaopb.lightingimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 处理图片请求
 */
public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };
    /*
     * 定义一个队列
     */
    private LinkedBlockingDeque<BitmapRequest> resquestQueue;

    /**
     * 传入队列，并不负责创建队列，该方法为队列的接受方法
     */
    public BitmapDispatcher(LinkedBlockingDeque<BitmapRequest> resquestQueue) {
        this.resquestQueue = resquestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                BitmapRequest br = resquestQueue.take();
                //先设置占位图片
                showLoadingImg(br);
                //加载图片
                Bitmap bitmap = findBitMap(br);
                //把图片显示到imageView
                showImgView(br, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void showImgView(BitmapRequest br, final Bitmap bitmap) {
        if (bitmap != null && br.getImageView() != null && br.getUrlMD5().equals(br.getImageView().getTag())
        ) {
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

        }

    }

    private Bitmap findBitMap(BitmapRequest br) {
        //查找缓存

        //从网络加载
        Bitmap bitmap = null;
        bitmap = downloadBitmap(br.getUrl());
        return bitmap;
    }

    /*
    支持自定义传入cache
     */
//    private Cache cache;
//
//    public void setCacheListener(Cache cache) {
//        this.cache = cache;
//    }

    private Bitmap downloadBitmap(String imgurl) {
        //下载bitmap
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imgurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void showLoadingImg(BitmapRequest br) {
        if (br.getResId() > 0 && br.getImageView() != null) {
            final int resId = br.getResId();
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });

        }
    }
}

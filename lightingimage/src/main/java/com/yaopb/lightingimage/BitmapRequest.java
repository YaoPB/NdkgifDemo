package com.yaopb.lightingimage;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * 图片请求
 * shdpsin@gmail.com
 */
public class BitmapRequest {

    //请求路径
    private String url;

    //上下文
    private Context context;

    //需要加载图片的空间;软引用，及时回收图片
    private SoftReference<ImageView> imageView;

    //占位图
    private int resId;

    //下载完图片的回调
    private RequestListener requestListener;

    //图片标识  用MD5码标识图片，避免http://xxxxxx很长的情况；标识图片，避免viewholder图片展示错位
    private String urlMD5;


    public BitmapRequest(Context context) {
        this.context = context;
    }


    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMD5 = MD5Utils.toMD5(this.url);
        return this;
    }

    public BitmapRequest loading(int resId) {
        this.resId = resId;
        return this;

    }

    public BitmapRequest setListener(RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public void into(ImageView imageView) {
        imageView.setTag(this.urlMD5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitMapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public String getUrlMD5() {
        return urlMD5;
    }

    public void setUrlMD5(String urlMD5) {
        this.urlMD5 = urlMD5;
    }
}

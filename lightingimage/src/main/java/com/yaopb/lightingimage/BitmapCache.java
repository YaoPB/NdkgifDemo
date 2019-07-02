package com.yaopb.lightingimage;

import android.graphics.Bitmap;

public interface BitmapCache {

    void put(BitmapRequest br, Bitmap bitmap);

    void get(BitmapRequest br, Bitmap bitmap);

    void remove(BitmapRequest br, Bitmap bitmap);

}

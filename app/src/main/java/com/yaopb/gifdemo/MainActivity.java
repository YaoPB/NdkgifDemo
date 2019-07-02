package com.yaopb.gifdemo;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {


    private ImageView glideImageView;
    private ImageView ndkImageView;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "demo.gif";
    private GifHandler gifHandler;
    private Bitmap bitmap;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            long nextFrameTime = gifHandler.renderFrame(bitmap);
            ndkImageView.setImageBitmap(bitmap);
            handler.sendEmptyMessageDelayed(1, nextFrameTime);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        glideImageView = findViewById(R.id.glide_imageview);
        ndkImageView = findViewById(R.id.ndk_imageview);


        String[] permission = new String[]{Manifest.permission.INTERNET};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission, 1);
        } else {
            findViewById(R.id.glideload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    glideImageView.setVisibility(View.VISIBLE);
                    ndkImageView.setVisibility(View.GONE);
                    Glide.with(MainActivity.this).load("http://img.soogif.com/vpHDaC0fZXTY8gL4axcTTI9fV4Oj55nq.gif").into(glideImageView);
                }
            });

            findViewById(R.id.ndkload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    glideImageView.setVisibility(View.GONE);
                    ndkImageView.setVisibility(View.VISIBLE);
                    gifHandler = new GifHandler(path);
                    int width = gifHandler.getWidth();
                    int height = gifHandler.getHeight();
                    long nextFrameTime = gifHandler.renderFrame(bitmap);
                    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    ndkImageView.setImageBitmap(bitmap);
                    if (handler != null) {
                        handler.sendEmptyMessageDelayed(1, nextFrameTime);
                    }
                }
            });
        }
    }
}

package com.example.myapplication;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FloatService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private Button button;

    View mFloatingLayout;


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("🚀=====onCreate");
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = 500;
        layoutParams.height = 400;
        layoutParams.x = 300;
        layoutParams.y = 500;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("🚀=====onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本
            if (Settings.canDrawOverlays(this)) {
                System.out.println("🚀=====addView222");
//                button = new Button(getApplicationContext());
//                button.setText("我是个button窗口2222");
//                button.setWidth(700);
//                button.setHeight(100);
//                button.setTextColor(Color.WHITE);
//                button.setBackgroundColor(Color.BLACK);
//                windowManager.addView(button, layoutParams);
//              button.setOnTouchListener(new FloatingOnTouchListener());
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                mFloatingLayout = inflater.inflate(R.layout.float_rtc_modal, null);
                mFloatingLayout.setBackground(null);
                windowManager.addView(mFloatingLayout,layoutParams);
            }
        } else {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View inflate = inflater.inflate(R.layout.float_rtc_modal, null);
            windowManager.addView(inflate,layoutParams);
        }
    }
}

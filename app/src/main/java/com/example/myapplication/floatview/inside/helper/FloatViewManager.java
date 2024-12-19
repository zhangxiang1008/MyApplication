package com.example.myapplication.floatview.inside.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


import com.example.myapplication.floatview.inside.component.FloatView;
import com.example.myapplication.floatview.inside.interfaces.IFloatView;

public class FloatViewManager implements IFloatView {

    private FloatView floatView;

    private WindowManager windowManager;

    @SuppressLint("StaticFieldLeak")
    private static FloatViewManager manager;

    private WindowManager.LayoutParams floatBallParams;

    private Context context;


    public boolean isShow = false;

    private FloatViewManager(Context context) {
        this.context = context;
        init();
    }

    public static synchronized FloatViewManager getInstance(Context context) {
        if (manager == null) {
            manager = new FloatViewManager(context);
        }
        return manager;
    }

    private void init() {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        floatView = new FloatView(context);

        floatView.setOnTouch(new FloatV2ViewListener() {
            float startX;
            float startY;
            float tempX;
            float tempY;

            @Override
            public void onTouchClose() {
                hideFloatView();
            }
            @Override
            public Boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();

                        tempX = event.getRawX();
                        tempY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getRawX() - startX;
                        float y = event.getRawY() - startY;
                        //计算偏移量，刷新视图
                        floatBallParams.x += x;
                        if (floatBallParams.x < 0) {
                            floatBallParams.x = 0;
                        }
                        if (floatBallParams.x > getScreenWidth() - floatView.getLayoutWidth()) {
                            floatBallParams.x = getScreenWidth() - floatView.getLayoutWidth();
                        }
                        floatBallParams.y += y;
                        windowManager.updateViewLayout(floatView, floatBallParams);
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getRawX();
                        float endY = event.getRawY();

//                        if(floatBallParams.x < 10){
//                            floatView.fold(true);
//                        }else if(floatBallParams.x > getScreenWidth()-floatView.getLayoutWidth()-10){
//                            floatView.fold(false);
//                        }
                        //如果初始落点与松手落点的坐标差值超过6个像素，则拦截该点击事件
                        //否则继续传递，将事件交给OnClickListener函数处理
                        if (Math.abs(endX - tempX) > 6 && Math.abs(endY - tempY) > 6) {
                            return true;
                        }
                        break;
                }
                return true;
            }

            @Override
            public Boolean onFoldTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getRawY();
                        tempY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getRawY() - startY;
                        //计算偏移量，刷新视图
                        floatBallParams.y += y;
                        windowManager.updateViewLayout(floatView, floatBallParams);
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endY = event.getRawY();
                        //如果初始落点与松手落点的坐标差值超过6个像素，则拦截该点击事件
                        //否则继续传递，将事件交给OnClickListener函数处理
                        if (Math.abs(endY - tempY) > 6) {
                            return true;
                        }
                        break;
                }
                return false;
            }

            @Override
            public void onClick(boolean isFold, int width) {
            }

            @Override
            public void fold(boolean isLeft, int minWidth) {
                if (!isShow) {
                    return;
                }
                floatBallParams.width = minWidth;
                floatBallParams.x = isLeft ? 0 : (getScreenWidth() - minWidth);
                windowManager.updateViewLayout(floatView, floatBallParams);

            }

            @Override
            public Boolean isLeft(int width) {
                return floatBallParams.x < (getScreenWidth() - width) / 2;
            }


        });
    }

    public void showFloatView() {
        if (isShow) {
            return;
        }
        if (floatBallParams == null) {
            floatBallParams = new WindowManager.LayoutParams();
            floatBallParams.width = floatView.getLayoutWidth();
            floatBallParams.height = floatView.getLayoutHeight();
            floatBallParams.gravity = Gravity.TOP | Gravity.START;
            floatBallParams.x = (getScreenWidth() - floatView.getLayoutWidth()) / 2;
            floatBallParams.y = floatView.getToTopHeight();
            floatBallParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            floatBallParams.format = PixelFormat.RGBA_8888;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                floatBallParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                floatBallParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }
        windowManager.addView(floatView, floatBallParams);
        isShow = true;
        floatView.onShow();
    }


    public void hideFloatView() {
        if (!isShow) {
            return;
        }
        windowManager.removeView(floatView);
        isShow = false;
    }

    private int getScreenWidth() {
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    //获取屏幕高度
    private int getScreenHeight() {
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }


    @Override
    public Integer getPriority() {
        return null;
    }

    @Override
    public void hide() {

    }

    private void douAn() {
       floatView.douAn();
    }

    private void autoCloseByTimeout() {
        long l = System.currentTimeMillis();
        while (System.currentTimeMillis() - l > 30000){
            hideFloatView();
        }
    }
}

package com.example.myapplication.component.PieChart;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;

import androidx.annotation.NonNull;

public class PieGestureListener extends GestureDetector.SimpleOnGestureListener {

    public  interface  onOnCallBack{
        void postInvalidateCall();
    }

    private onOnCallBack callBack;

    public void setCallBack(onOnCallBack callBack) {
        this.callBack = callBack;
    }

    private Scroller scroller;


    public void setScroller(Scroller scroller) {
        this.scroller = scroller;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        int SCALE = 10000;
        scroller.fling(scroller.getCurrX(),scroller.getCurrY(),(int) velocityX/ SCALE,(int)velocityY/ SCALE,0,10000,0,10000);
        return true;
    }
}

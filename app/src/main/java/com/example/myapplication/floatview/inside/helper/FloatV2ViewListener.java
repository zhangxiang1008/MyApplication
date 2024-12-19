package com.example.myapplication.floatview.inside.helper;

import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.floatview.inside.interfaces.FloatViewListener;

public class FloatV2ViewListener implements FloatViewListener {
    @Override
    public Boolean onTouch(View v, MotionEvent event) {
        return null;
    }

    @Override
    public Boolean onFoldTouch(View v, MotionEvent event) {
        return null;
    }

    @Override
    public void onClick(boolean isFold, int width) {

    }

    @Override
    public void fold(boolean isLeft, int minWidth) {

    }

    @Override
    public void autoMove(boolean isLeft, int width) {

    }

    @Override
    public Boolean isLeft(int width) {
        return null;
    }

    @Override
    public void onTouchClose() {

    }
}

package com.example.myapplication.floatview.inside.interfaces

import android.view.MotionEvent
import android.view.View

interface FloatViewListener {
    fun onTouch(v: View?, event: MotionEvent?): Boolean?
    fun onFoldTouch(v: View?, event: MotionEvent?): Boolean?
    fun onClick(isFold: Boolean, width: Int)
    fun fold(isLeft: Boolean, minWidth: Int)
    fun autoMove(isLeft: Boolean, width: Int)
    fun isLeft(width: Int): Boolean?
    fun onTouchClose()
}
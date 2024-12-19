package com.example.myapplication.floatview.inside.component

import android.animation.Animator
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.floatview.inside.interfaces.FloatViewListener


class FloatView(context: Context) : FrameLayout(context) {
    private var avatar: View
    private var title: TextView
    private var callButton: ImageView
    private var callOffButton: ImageView
    private var layout: View
    private val mediaPlayer = MediaPlayer()
    private var screenWidth = 0

    var layoutWidth = 0
    var layoutHeight = 0
    var toTopHeight = 0
    var padding = 0


    init {
        inflate(context, R.layout.float_rtc_modal, this)
        layout = findViewById(R.id.float_unfold)
        avatar = findViewById(R.id.avatar);
        title = findViewById(R.id.float_title)
        callButton = findViewById(R.id.float_button_call)
        callOffButton = findViewById(R.id.float_button_callOff)
        screenWidth =
            (getContext().getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.width
                ?: 360
        val scale = screenWidth * 1.0f / 360
        layoutWidth = (356 * scale).toInt()
        layoutHeight = (128 * scale).toInt()
        toTopHeight = (100 * scale).toInt()
        padding = (10 * scale).toInt()
        initClickListener()
        mediaPlayer.setDataSource("https://imagecdn.ymm56.com/ymmfile/static/image/dududud.mp3")
        mediaPlayer.prepare()
        mediaPlayer.isLooping = true

    }

    var mListener: FloatViewListener? = null

    fun setOnTouch(listener: FloatViewListener) {
        mListener = listener
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initClickListener() {
        callButton.setOnClickListener {
            closeAn(null);
        }

        layout.setOnTouchListener { v, event ->
            mListener?.onTouch(v, event) ?: false
        }
    }

    private fun reset() {
    }

    fun onShow() {
        // 铃声播放
        douAn()
        mediaPlayer.start()
       val thread = object : Thread() {
            public override fun run() {
                val handler: Handler = object : Handler(Looper.getMainLooper()) {
                }
                handler.postDelayed(Runnable {
                    closeAn(null)
                }, 10000)
            }
        }
        thread.start()
    }

    fun douAn() {
        //展开横幅
        val scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(
            SCALE_Y,
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.25f, 0.25f),
            Keyframe.ofFloat(0.5f, 0.5f),
            Keyframe.ofFloat(0.75f, 0.75f),
            Keyframe.ofFloat(1.0f, 1.0f)
        )
        val objectAnimator: ObjectAnimator =
            ObjectAnimator.ofPropertyValuesHolder(this, scaleYValuesHolder)
        objectAnimator.duration = 200
        objectAnimator.start()

        // 头像抖动
        val rotateValuesHolder = PropertyValuesHolder.ofKeyframe(
            ROTATION,
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.2f, 10.25f),
            Keyframe.ofFloat(0.4f, -0.5f),
            Keyframe.ofFloat(0.6f, -10.75f),
            Keyframe.ofFloat(0.8f, 0.75f),
            Keyframe.ofFloat(1.0f, 0f)
        )
        val rotateAnimator =
            ObjectAnimator.ofPropertyValuesHolder(avatar, rotateValuesHolder);
        rotateAnimator.duration = 200;
        rotateAnimator.repeatCount = ValueAnimator.INFINITE
        rotateAnimator.start();
    }

    private fun closeAn(jumpUrl: String?) {
        mediaPlayer.pause()
        val scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(
            SCALE_Y,
            Keyframe.ofFloat(0f, 1.0f),
            Keyframe.ofFloat(0.25f, 0.75f),
            Keyframe.ofFloat(0.5f, 0.5f),
            Keyframe.ofFloat(0.75f, 0.25f),
            Keyframe.ofFloat(1.0f, 0.0f)
        )

        val objectAnimator: ObjectAnimator =
            ObjectAnimator.ofPropertyValuesHolder(this, scaleYValuesHolder)
        objectAnimator.duration = 200
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                mListener?.onTouchClose()
                print(jumpUrl)
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })

        objectAnimator.start()
    }

}
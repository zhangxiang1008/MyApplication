package com.example.myapplication.component.PieChart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class PieChart extends View {
    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0);
        try {
            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
            textPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
        } finally {
            a.recycle();
        }
        bounds = new RectF(50, 50, 1000, 1000);
        Item item = new Item();
        item.label = "000000";
        item.value = 2;
        item.startAngle = 0;
        item.endAngle = 90;
        item.shader = new SweepGradient(500, 500, Color.GREEN, Color.GREEN);
        data.add(item);

        Item item2 = new Item();
        item2.label = "55";
        item2.value = 3;
        item2.color = 2;
        item2.startAngle = 90;
        item2.endAngle = 160;
        item2.shader = new SweepGradient(500, 500, Color.RED, Color.RED);
        data.add(item2);
        Item item3 = new Item();
        item3.label = "155";
        item3.value = 4;
        item3.startAngle = 160;
        item3.endAngle = 270;
        item3.shader = new SweepGradient(500, 500, Color.BLUE, Color.RED);
        data.add(item3);
        Item item4 = new Item();
        item4.label = "155";
        item4.value = 4;
        item4.startAngle = 270;
        item4.endAngle = 360;
        item4.shader = new SweepGradient(500, 500, Color.YELLOW, Color.YELLOW);
        data.add(item4);
        pointerX = 500;
        pointerY = 500;
        pointerRadius = 50;
        textX = 100;
        textY = 100;
        init();
        PieGestureListener listener = new PieGestureListener();
        listener.setScroller(mScroller);
        listener.setCallBack(new PieGestureListener.onOnCallBack() {
            @Override
            public void postInvalidateCall() {
                postInvalidate();
            }
        });
    }

    private final Scroller mScroller = new Scroller(getContext());
    private GestureDetector detector;
    // onDraw 画板
    private Paint textPaint;
    private Paint piePaint;
    private Paint shadowPaint;
    @ColorInt
    private int textColor;
    @Dimension
    private float textHeight;


    private int textPos;
    private boolean mShowText;

    private Boolean showText;    // Obtained from styled attributes.

    private int textWidth;       // Obtained from styled attributes.


    public int getTextPos() {
        return textPos;
    }

    public void setTextPos(int textPos) {
        this.textPos = textPos;
    }

    public boolean isShowText() {
        return mShowText;
    }

    public void setShowText(boolean mShowText) {
        this.mShowText = mShowText;
        invalidate();
        requestLayout();
    }

    private void init() {

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);
        if (textHeight == 0) {
            textHeight = textPaint.getTextSize();
        } else {
            textPaint.setTextSize(textHeight);
        }

        piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        piePaint.setStyle(Paint.Style.FILL);
        piePaint.setTextSize(textHeight);

        shadowPaint = new Paint(0);
        shadowPaint.setColor(0xff101010);
        shadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Account for padding.
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        // Account for the label.
        if (showText) xpad += textWidth;

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;

        // Figure out how big you can make the pie.
        float diameter = Math.min(ww, hh);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on your minimum.
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width is, ask for a height that lets the pie get as big as it
        // can.
        int minh = MeasureSpec.getSize(w) - (int) textWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = detector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                stopNestedScroll();
                result = true;
            }
        }
        return result;
    }

    private List<Item> data = new ArrayList<Item>();  // A list of items that are displayed.

    private RectF shadowBounds;                       // Calculated in onSizeChanged.
    private float pointerRadius;                      // Obtained from styled attributes.
    private float pointerX;                           // Calculated in onSizeChanged.
    private float pointerY;                           // Calculated in onSizeChanged.
    private float textX;                              // Calculated in onSizeChanged.
    private float textY;                              // Calculated in onSizeChanged.
    private RectF bounds;                             // Calculated in onSizeChanged.
    private int currentItem = 0;                      // The index of the currently selected item.

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the shadow.
//        canvas.drawOval(shadowBounds, shadowPaint);

        // Draw the label text.
        canvas.drawText(data.get(currentItem).label, textX, textY, textPaint);

        // Draw the pie slices.
        for (int i = 0; i < data.size(); ++i) {
            Item it = data.get(i);
            piePaint.setShader(it.shader);
            canvas.drawArc(bounds, 360 - it.endAngle, it.endAngle - it.startAngle, true, piePaint);
        }
        // Draw the pointer.
        canvas.drawLine(textX, pointerY, pointerX, pointerY, textPaint);
        canvas.drawCircle(pointerX, pointerY, pointerRadius, textPaint);
    }

    // Maintains the state for a data item.
    private class Item {
        public String label;
        public float value;
        @ColorInt
        public int color;

        // Computed values.
        public int startAngle;
        public int endAngle;

        public Shader shader;
    }


}

package com.example.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class MyDialog extends FrameLayout {
    private boolean showText;

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    private onDismissCallback callback;

    public interface onDismissCallback {
        void dismiss();
    }

    public void setCallback(onDismissCallback callback) {
        this.callback = callback;
    }

    public MyDialog(Context context) {
        this(context, null);
    }

    public MyDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.my_dialog, this, true);
        view.findViewById(R.id.dialog_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.dismiss();
                }
            }
        });
    }
}

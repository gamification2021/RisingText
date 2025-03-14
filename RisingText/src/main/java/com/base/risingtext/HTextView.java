package com.base.risingtext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public abstract class HTextView extends TextView {
    public HTextView(Context context) {
        super(context);
    }

    public HTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public HTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public abstract void setAnimationListener(AnimationListener listener);

    public abstract void setProgress(float progress);

    public abstract void animateText(CharSequence text);

}

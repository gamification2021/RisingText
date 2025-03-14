package com.base.risingtext;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class RisingTextView extends HTextView {
    private RisingText risingText ;

    public RisingTextView(Context context) {
        this(context,null);
    }

    public RisingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RisingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public RisingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setAnimationListener(AnimationListener listener) {
        risingText.setAnimationListener(listener);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        risingText = new RisingText();
        risingText.init(this, attrs, defStyleAttr);
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public void setProgress(float progress) {
        risingText.setProgress(progress);
    }

    @Override
    public void animateText(CharSequence text,boolean doAnimate) {
        risingText.animateText(text,doAnimate);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        risingText.onDraw(canvas);
    }
}

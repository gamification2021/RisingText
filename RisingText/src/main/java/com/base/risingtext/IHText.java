package com.base.risingtext;

import android.graphics.Canvas;
import android.util.AttributeSet;

public interface IHText {
    void init(HTextView hTextView, AttributeSet attrs, int defStyle);

    void animateText(CharSequence text,boolean doAnimate);

    void onDraw(Canvas canvas);

    void setAnimationListener(AnimationListener listener);
}

package com.base.risingtext;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

public class RisingText extends HText {
    float charTime = 300;
    int mostCount = 20;
    private int mTextHeight;

    private List<CharacterDiffResult> differentList = new ArrayList<>();
    private long duration;
    private ValueAnimator animator;
    private boolean hasAnimation = false;
    private float animationDuration = 1000f;

    @Override
    public void init(HTextView hTextView, AttributeSet attrs, int defStyle) {
        super.init(hTextView, attrs, defStyle);
        super.init(hTextView, attrs, defStyle);
        if (attrs != null) {
            TypedArray typedArray = hTextView.getContext().obtainStyledAttributes(attrs, R.styleable.RisingTextView);
            animationDuration = typedArray.getFloat(R.styleable.RisingTextView_rtDuration, 1000);
            typedArray.recycle();
        }
        animator = new ValueAnimator();
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new DefaultAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null && hasAnimation) {
                    animationListener.onAnimationEnd(mHTextView);
                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                mHTextView.invalidate();
            }
        });
        int n = mText.length();
        n = n <= 0 ? 1 : n;
        duration = (long) (charTime + charTime / mostCount * (n - 1));
    }

    @Override
    public void animateText(final CharSequence text,boolean doAnimate) {
        mHTextView.post(new Runnable() {
            @Override
            public void run() {
                if (mHTextView == null || mHTextView.getLayout() == null) {
                    return;
                }
                oldStartX = mHTextView.getLayout().getLineLeft(0);
                RisingText.super.animateText(text,doAnimate);
            }
        });
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void animateStart(CharSequence text,boolean doAnimate) {
        hasAnimation = doAnimate;
        int n = mText.length();
        n = n <= 0 ? 1 : n;
        duration = (long) (charTime + charTime / mostCount * (n - 1));
        animator.cancel();
        animator.setFloatValues(0, 1);
 //       animator.setDuration(doAnimate ? duration : 1);
        animator.setDuration(doAnimate ? (long)(animationDuration) : 1);
        animator.start();
    }

    @Override
    protected void animatePrepare(CharSequence text) {
        differentList.clear();
        differentList.addAll(CharacterUtils.diff(mOldText, mText));

        Rect bounds = new Rect();
        mPaint.getTextBounds(mText.toString(), 0, mText.length(), bounds);
        mTextHeight = bounds.height();
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        float startX = mHTextView.getLayout().getLineLeft(0);
        float startY = mHTextView.getBaseline();

        float offset = startX;
        float oldOffset = oldStartX;

        int maxLength = Math.max(mText.length(), mOldText.length());

        for (int i = 0; i < maxLength; i++) {

            // draw old text
            if (i < mOldText.length()) {
                //
                float pp = progress * duration / (charTime + charTime / mostCount * (mText.length() - 1));

                mOldPaint.setTextSize(mTextSize);
                int move = CharacterUtils.needMove(i, differentList);
                if (move != -1) {
                    mOldPaint.setAlpha(255);
                    float p = pp * 2f;
                    p = p > 1 ? 1 : p;
                    float distX = CharacterUtils.getOffset(i, move, p, startX, oldStartX, gapList, oldGapList);
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, distX, startY, mOldPaint);
                } else {
                    mOldPaint.setAlpha((int) ((1 - pp) * 255));
                    float y = startY - pp * mTextHeight;
                    float width = mOldPaint.measureText(mOldText.charAt(i) + "");
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, oldOffset + (oldGapList.get(i) - width) / 2, y, mOldPaint);
                }
                oldOffset += oldGapList.get(i);
            }

            // draw new text
            if (i < mText.length()) {

                if (!CharacterUtils.stayHere(i, differentList)) {

                    int alpha = (int) (255f / charTime * (progress * duration - charTime * i / mostCount));
                    alpha = alpha > 255 ? 255 : alpha;
                    alpha = alpha < 0 ? 0 : alpha;

                    mPaint.setAlpha(alpha);
                    mPaint.setTextSize(mTextSize);
                    float pp = progress * duration / (charTime + charTime / mostCount * (mText.length() - 1));
                    float y = mTextHeight + startY - pp * mTextHeight;

                    float width = mPaint.measureText(mText.charAt(i) + "");
                    canvas.drawText(mText.charAt(i) + "", 0, 1, offset + (gapList.get(i) - width) / 2, y, mPaint);
                }

                offset += gapList.get(i);
            }
        }
    }

}

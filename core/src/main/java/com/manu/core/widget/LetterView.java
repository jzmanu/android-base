package com.manu.core.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.manu.core.utils.DensityUtil;


/**
 * Powered by jzman.
 * Created on 2018/7/5 0005.
 */
public class LetterView extends View {
    private String[] letters = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private OnLetterChangeListener mOnLetterChangeListener;


    private Paint mLetterPaint;
    private Paint mLetterBackgroundPaint;
    private int mWidth;
    private int mItemHeight;
    private int mTouchIndex = -1;

    public LetterView(Context context) {
        super(context);
        init();
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnLetterChangeListener(OnLetterChangeListener mOnLetterChangeListener) {
        this.mOnLetterChangeListener = mOnLetterChangeListener;
    }

    private void init() {
        mLetterPaint = new Paint();
        mLetterPaint.setTextSize(16);
        mLetterPaint.setColor(Color.parseColor("#FF0000"));
        mLetterPaint.setAntiAlias(true);

        mLetterBackgroundPaint = new Paint();
        mLetterBackgroundPaint.setStyle(Paint.Style.FILL);
        mLetterBackgroundPaint.setColor(Color.parseColor("#333333"));
        mLetterBackgroundPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("onMeasure--->");
        System.out.println("width--->" + getMeasuredWidth());
        System.out.println("height--->" + getMeasuredHeight());

        mWidth = getMeasuredWidth();
        mItemHeight = getMeasuredHeight() / 27;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制指示器
        for (int i = 1; i < letters.length + 1; i++) {
            if (mTouchIndex == i) {
                canvas.drawCircle(0.5f * mWidth, mTouchIndex * (mItemHeight + mItemHeight / 20) - 0.5f * (mItemHeight + mItemHeight / 20), 0.5f * (mItemHeight + mItemHeight / 20), mLetterBackgroundPaint);
            }
        }
        //绘制字母
        for (int i = 1; i < letters.length + 1; i++) {
            canvas.drawText(letters[i - 1],  mItemHeight, (mItemHeight + mItemHeight / 20) * i - 0.5f * (mItemHeight + mItemHeight / 20), mLetterPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getY();
                System.out.println("--y->"+y+"-y-dp-->"+ DensityUtil.px2dp(getContext(),y));
                int index = y / mItemHeight;
                if (index != mTouchIndex && index < 27 && index > 0) {
                    mTouchIndex = index;
                    System.out.println("--mTouchIndex->" + letters[mTouchIndex - 1] + "--position->" + mTouchIndex);
                }
                if (mOnLetterChangeListener != null && mTouchIndex > 0) {
                    mOnLetterChangeListener.onLetterListener(mTouchIndex);
                }
                invalidate();
                break;
        }
        return true;
    }

    public interface OnLetterChangeListener {
        void onLetterListener(int touchIndex);
    }
}

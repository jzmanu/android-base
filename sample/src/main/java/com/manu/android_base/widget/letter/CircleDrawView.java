package com.manu.android_base.widget.letter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/4/21.
 */

public class CircleDrawView extends View {

    private Paint mCirclePaint;
    private Paint mNumPaint;

    private int centerX = 0;
    private int centerY = 0;
    private int radius = dip2px(61, getContext());
    private int padding = dip2px(10, getContext());

    private Rect mNumBound;
    private float mNumTextSize = dip2px(24, getContext());
    private int mNumTextColor = Color.WHITE;

    private float number = 60;
    private float curNumber = 0;
    private float animTime = 2 * 1000;//动画时间
    private int dt = 25;//动画刷新频率
    private float dn = number / (animTime / dt);
    private float angle = 80;
    private int alpha = 194;
    private float mCircleBold = dip2px(34, getContext());
    private int mCcircle = Color.parseColor("#4decd9");

    public CircleDrawView(Context context) {
        this(context, null);
    }

    public CircleDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr);
    }

    public void setNumber(float number) {
        this.number = number;
        postInvalidate();
    }

    public void setNumber2(float number) {
        curNumber=0;
        this.number = number;
        animTime = 500;//动画时间
        dn = number / (animTime /(float) dt);
        postInvalidate();
    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr) {
//        //自定义属性
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeIndicatorView, defStyleRes, 0);
//        mShapeHorizontalSpace = array.getInteger(R.styleable.ShapeIndicatorView_horizontalSpace, 15);
//        mShapeColor = array.getColor(R.styleable.ShapeIndicatorView_fullColor, Color.GREEN);
//        int radius = array.getInteger(R.styleable.ShapeIndicatorView_radius, 50);
//        array.recycle();
        setCirclePaint();
        setNumPaint();
    }

    private void getCenterRadius() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2 - 3;
//        radius = getWidth()>getHeight()?getHeight()/2-padding/2:getWidth()/2-padding/2;
    }

    private void setCirclePaint() {//一般
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(mCcircle);
        mCirclePaint.setStyle(Paint.Style.STROKE);
//        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStrokeWidth(mCircleBold+5);//100
        mCirclePaint.setAlpha(alpha);//194
    }

    private void setNumPaint() {
        mNumPaint = new Paint();
        mNumBound = new Rect();
        mNumPaint.setTextSize(mNumTextSize);
        mNumPaint.setAntiAlias(true);
        mNumPaint.setDither(true);
//        mNumPaint.setStrokeWidth(1.0f);
        mNumPaint.setColor(mNumTextColor);
//        mNumPaint.setColor(getResources().getColor(R.color.white));
        mNumPaint.setFakeBoldText(true);
//        mNumPaint.setTypeface(ApplicationContext.getPingFTypeFace());
        mNumPaint.getTextBounds(number + "", 0, (number + "").length(), mNumBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getCenterRadius();
        paintNum(canvas);
        paintProgress(canvas);
        curNumber += dn;

        BigDecimal b  =   new BigDecimal(curNumber);
        curNumber =  b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        if (curNumber <= number) {
            postInvalidateDelayed(dt);
        }
    }

    private void paintProgress(Canvas canvas) {
        if (curNumber <= number) {
            angle = curNumber / 100 * 360;
        } else {
            angle = number / 100 * 360;
        }
        RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(oval, -90, angle, false, mCirclePaint);
    }

    private void paintNum(Canvas canvas) {
        int intNumber = 0;
        if (curNumber <= number) {
            intNumber = (int) (curNumber + 0.5);
        } else {
            intNumber = (int) (number + 0.5);
        }
        mNumPaint.getTextBounds(intNumber + "", 0, (intNumber + "").length(), mNumBound);
        canvas.drawText(intNumber + "", centerX - mNumBound.width() / 2f, centerY + mNumBound.height() / 2f, mNumPaint);
    }

    private int px2dip(float pxValue, Context context) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    private int dip2px(float dipValue, Context context) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
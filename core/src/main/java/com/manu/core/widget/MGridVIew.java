package com.manu.core.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * 自带分割线的GridView
 * @author: jzman
 * @time: 2018/6/6 0006 10:49
 */

public class MGridVIew extends GridView {

    private int rowCount;
    private Paint paint;

    public MGridVIew(Context context) {
        super(context);
    }

    public MGridVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MGridVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        initPaint();
        //列数
        int columnCount = getNumColumns();
        //总数
        int total = getChildCount();
        //行数
        if (total % columnCount == 0){
            rowCount = total / columnCount;
        }else{
            rowCount = total / columnCount + 1;
        }

        //获得第一个View
        View firstView = getChildAt(0);
        //获得第一行的最后一个View
        View firstRowLastView = getChildAt(columnCount - 1);
        //获取第一列的最后一个View
        View firstColumnLastView = getChildAt((rowCount - 1) * columnCount);

        //绘制横线
        for(int i=1; i<rowCount; i++){
            canvas.drawLine(firstView.getLeft(),firstView.getBottom() * i,
                    firstRowLastView.getRight(),firstRowLastView.getBottom() * i,paint);
        }

        //绘制竖线
        for(int i=1; i< columnCount; i++){
            canvas.drawLine(firstView.getRight() * i,firstView.getTop(),
                    firstColumnLastView.getRight() * i, firstColumnLastView.getBottom(),paint);
        }
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#FFFD5C5C"));
    }
}

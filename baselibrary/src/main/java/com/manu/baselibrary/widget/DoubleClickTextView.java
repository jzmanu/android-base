package com.manu.baselibrary.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jzman
 * Powered by 2018/6/28 0028.
 */

public class DoubleClickTextView extends AppCompatTextView implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private OnDoubleClickListener onDoubleClickListener;

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }

    public DoubleClickTextView(Context context) {
        super(context);
        onData(context);
    }

    public DoubleClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onData(context);
    }

    public DoubleClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onData(context);
    }

    private void onData(Context context) {
        sharedPreferences = context.getSharedPreferences("click",Context.MODE_PRIVATE);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean isClick = sharedPreferences.getBoolean("click",false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isClick){
            //第二次点击，释放
            editor.putBoolean("click",false);
            if (onDoubleClickListener!=null){
                onDoubleClickListener.onDoubleClick(false);
            }
        }else{
            //第一次点击，保存点击状态
            editor.putBoolean("click",true);
            if (onDoubleClickListener!=null){
                onDoubleClickListener.onDoubleClick(true);
            }
        }
        editor.apply();
    }

    public void setClickStatus(boolean isClick){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("click",isClick);
        editor.apply();
    }


    public interface OnDoubleClickListener{
        void onDoubleClick(boolean isFirstClick);
    }
}

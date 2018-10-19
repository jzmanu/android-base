package com.manu.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.manu.core.R;

/**
 * Powered by jzman.
 * Created on 2018/7/12 0012.
 */
public class MTimePicker extends PopupWindow {
    private LayoutInflater mInflater;
    private int mYearCount;

    public MTimePicker(Context context) {
        super(context);
        onData(context);
    }

    public MTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        onData(context);
    }

    public MTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onData(context);
    }

    private void onData(Context context) {
        mInflater = LayoutInflater.from(context);
        View convertView = mInflater.inflate(R.layout.m_date_picture_layout, null);
        setContentView(convertView);
    }

    public void showMDatePicker(View v) {
        showAtLocation(v, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
    }

    private void configData() {

    }

    public static class Builder {
        private int yearCount;

        public Builder setYearCount(int yearCount) {
            this.yearCount = yearCount;
            return this;
        }

//        private void apply
    }

}

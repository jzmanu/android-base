package com.manu.baselibrary.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.Map;

/**
 * 通用PopupWindow
 *
 * @author: jzman
 * @time: 2018/6/5 0005 10:00
 */
public class MPopupWindow {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private Drawable mBackgroundDrawable;
    private boolean mOutsideTouchable;
    private int mWidth;
    private View mView;
    private int mHeight;
    private int mOffsetX;
    private int mOffsetY;
    private int mGravity;

    private MPopupWindow() {}

    public void showPopupWindow(View v, LocationType type) {
        mPopupWindow.setContentView(mView);
        mPopupWindow.setWidth(mWidth);
        mPopupWindow.setHeight(mHeight);
        mPopupWindow.setBackgroundDrawable(mBackgroundDrawable);
        mPopupWindow.setOutsideTouchable(mOutsideTouchable);

        switch (type) {
            case TOP:
                int[] locations = new int[2];
                v.getLocationOnScreen(locations);
                int left = locations[0];
                int top  =  locations[1];
                mPopupWindow.getContentView().measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                int popupHeight = mPopupWindow.getContentView().getMeasuredHeight();
                mPopupWindow.showAtLocation(v,mGravity,left,top-popupHeight);
                break;
            case RIGHT:
                break;
            case BOTTOM:
                mPopupWindow.showAsDropDown(v,mOffsetX,mOffsetY,mGravity);
                break;
            case LEFT:
                break;
        }
    }

    public static class Builder {
        private Context context;
        private View contentView;
        private PopupWindow popupWindow;
        private Drawable backgroundDrawable;
        private boolean outsideTouchable;
        private int width;
        private int height;
        private int offsetX;
        private int offsetY;
        private int gravity;

        public Builder(Context context) {
            this.context = context;
            this.popupWindow = new PopupWindow(context);
            this.outsideTouchable = true;
            this.backgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
            this.width  = WindowManager.LayoutParams.WRAP_CONTENT;
            this.height = WindowManager.LayoutParams.WRAP_CONTENT;
            this.gravity = Gravity.NO_GRAVITY;
            this.offsetX = 0;
            this.offsetY = 0;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setOutsideTouchable(boolean outsideTouchable) {
            this.outsideTouchable = outsideTouchable;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setOffsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder setOffsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public MPopupWindow build() {
            MPopupWindow popupWindow = new MPopupWindow();
            setPopupWindowConfig(popupWindow);
            return popupWindow;
        }

        private void setPopupWindowConfig(MPopupWindow window) {
            if (contentView == null) {
                throw new MException("contentView can't be null.", "1");
            } else {
                window.mView = this.contentView;
            }

            if (context == null) {
                throw new MException("context can't be null.", "2");
            } else {
                window.mContext = this.context;
            }

            window.mWidth  = this.width;
            window.mHeight = this.height;
            window.mPopupWindow = this.popupWindow;
            window.mOutsideTouchable   = this.outsideTouchable;
            window.mBackgroundDrawable = this.backgroundDrawable;
            window.mOffsetX = this.offsetX;
            window.mOffsetY = this.offsetY;
            window.mGravity = this.gravity;
        }
    }

    public enum LocationType {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }
}

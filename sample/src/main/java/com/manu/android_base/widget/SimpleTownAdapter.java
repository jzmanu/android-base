package com.manu.android_base.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.manu.android_base.samples.bean.DistractBean;
import com.manu.android_base.wheelview.adapter.BaseWheelAdapter;
import com.manu.android_base.wheelview.widget.WheelItem;


/**
 * Powered by jzman.
 * Created on 2018/7/6 0006.
 */
public class SimpleTownAdapter extends BaseWheelAdapter<DistractBean.ChildrenBeanXXX.ChildrenBeanXX.ChildrenBeanX> {
    private Context mContext;

    public SimpleTownAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new WheelItem(mContext);
        }
        WheelItem item = (WheelItem) convertView;
        item.setText(mList.get(position).getName());
        return convertView;
    }
}

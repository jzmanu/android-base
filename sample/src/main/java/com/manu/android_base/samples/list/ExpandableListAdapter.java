package com.manu.android_base.samples.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.manu.android_base.R;

/**
 * Created by jzman
 * Powered by 2018/7/22 0022.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private DataBean data;

    public ExpandableListAdapter(Context context, DataBean data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.getGroupList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.getGroupList().get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.getGroupList().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.getGroupList().get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        VhGroup vhGroup;
        if (convertView == null){
            vhGroup = new VhGroup();
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_list_group_layout, null);
            vhGroup.tvGroup = convertView.findViewById(R.id.tvGroup);
            convertView.setTag(vhGroup);
        }else{
            vhGroup = (VhGroup) convertView.getTag();
        }
        vhGroup.tvGroup.setText(data.getGroupList().get(groupPosition).getGroupName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        VhChild vhChild;
        if (convertView == null){
            vhChild = new VhChild();
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_list_child_layout, null);
            vhChild.tvChild = convertView.findViewById(R.id.tvChild);
            vhChild.cbChild = convertView.findViewById(R.id.cbChild);
            convertView.setTag(vhChild);
        }else{
            vhChild = (VhChild) convertView.getTag();
        }
        vhChild.tvChild.setText(data.getGroupList().get(groupPosition).getChildList().get(childPosition).getChildName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class VhGroup {
        TextView tvGroup;
    }

    class VhChild {
        TextView tvChild;
        CheckBox cbChild;
    }
}

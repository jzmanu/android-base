package com.manu.android_base.samples.list;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.manu.android_base.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableListViewActivity extends Activity {

    @BindView(R.id.elList)
    ExpandableListView elList;

    private ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        ArrayList<DataBean.GroupBean.ChildBean> childBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataBean.GroupBean.ChildBean childBean = new DataBean.GroupBean.ChildBean("Child" + i);
            childBeans.add(childBean);
        }

        ArrayList<DataBean.GroupBean> groupBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataBean.GroupBean groupBean = new DataBean.GroupBean("Group" + i, childBeans);
            groupBeans.add(groupBean);
        }

        DataBean bean = new DataBean();
        bean.setGroupList(groupBeans);

        adapter = new ExpandableListAdapter(this,bean);
        elList.setAdapter(adapter);
    }
}

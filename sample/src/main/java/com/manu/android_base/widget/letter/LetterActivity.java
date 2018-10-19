package com.manu.android_base.widget.letter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.manu.android_base.R;
import com.manu.core.widget.LetterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LetterActivity extends AppCompatActivity implements LetterView.OnLetterChangeListener,
        AbsListView.OnScrollListener {

    @BindView(R.id.letterView)
    LetterView letterView;
    @BindView(R.id.lvData)
    ListView lvData;
    @BindView(R.id.tvLetterHeader)
    TextView tvLetterHeader;

    private LetterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        letterView.setOnLetterChangeListener(this);
        mAdapter = new LetterAdapter(this);
        lvData.setAdapter(mAdapter);
        lvData.setOnScrollListener(this);
    }

    @Override
    public void onLetterListener(String touchIndex) {
        tvLetterHeader.setVisibility(View.VISIBLE);
        tvLetterHeader.setText(touchIndex);
        updateListView(touchIndex);
    }

    @Override
    public void onLetterDismissListener() {
        tvLetterHeader.setVisibility(View.GONE);
    }

    private void updateListView(String header) {
        ArrayList<DataBean> list = mAdapter.getDataList();
        for (int i = 0; i < list.size(); i++) {
            String head = list.get(i).getNameHeader();
            if (head.equals(header)) {
                lvData.setSelection(i);
                return;
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        letterView.setTouchIndex(mAdapter.getDataList().get(firstVisibleItem).getNameHeader());
    }
}

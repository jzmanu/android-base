package com.manu.android_base.base.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manu.android_base.R;
import com.manu.android_base.base.DataContract;
import com.manu.android_base.base.presenter.DataPresenter;
import com.manu.android_base.samples.bean.BlogBean;
import com.manu.core.base.BaseActivity;
import com.manu.core.http.bean.ResultBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpActivity extends BaseActivity<DataPresenter> implements DataContract.IDataView {
    @BindView(R.id.tvData)
    TextView tvData;
    @BindView(R.id.pbProgress)
    ProgressBar pbProgress;
    @BindView(R.id.btnGetData)
    Button btnGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);
    }

    @Override
    public void onSetUpView(ResultBean<List<BlogBean>> bean) {
        tvData.setText(bean.getResults().get(0).getDesc());
    }

    @Override
    public void onDataError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    protected DataPresenter onCreatePresenter() {
        return new DataPresenter();
    }

    @OnClick(R.id.btnGetData)
    public void onViewClicked() {
        mPresenter.getData("data/Android/5/1");
    }
}

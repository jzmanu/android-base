package com.manu.core.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.manu.core.mvp.presenter.BasePresenter;
import com.manu.core.mvp.view.BaseView;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected T mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
        if (mPresenter!=null) mPresenter.onAttachView(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter!=null) mPresenter.onDetachView();
        super.onDestroy();
    }

    protected abstract T onCreatePresenter();
}

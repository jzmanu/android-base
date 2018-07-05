package com.manu.core.mvp.presenter;

import com.manu.core.mvp.view.BaseView;

/**
 * Powered by jzman.
 * Created on 2018/7/2 0002.
 */
public abstract class BasePresenter<T extends BaseView> {
    public T mView;

    public void onAttachView(T view) {
        mView = view;
    }

    public void onDetachView() {
        mView = null;
    }

    public boolean isAttachView() {
        return mView != null;
    }
}

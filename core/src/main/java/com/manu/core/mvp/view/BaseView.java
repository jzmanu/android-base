package com.manu.core.mvp.view;

/**
 * Powered by jzman.
 * Created on 2018/7/2 0002.
 */
public interface BaseView {
    void onShowProgress();
    void onHideProgress();
    void onDataError(String error);
}

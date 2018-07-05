package com.manu.android_base.base.presenter;

import com.manu.android_base.base.DataContract;
import com.manu.android_base.base.model.DataModelImpl;
import com.manu.android_base.samples.bean.BlogBean;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * Powered by jzman.
 * Created on 2018/7/2 0002.
 */
public class DataPresenter extends BasePresenter<DataContract.IDataView> implements
        DataContract.IDataPresenter, DataContract.IDataModel.OnResultListener {
    private DataContract.IDataModel mDataModel;

    public DataPresenter() {
        this.mDataModel = new DataModelImpl();
    }

    @Override
    public void getData(String url) {
        if (isAttachView()) mView.onShowProgress();
        mDataModel.getData(url, this);
    }

    @Override
    public void onGetDataSuccess(ResultBean<List<BlogBean>> bean) {
        if (isAttachView()) {
            mView.onHideProgress();
            mView.onSetUpView(bean);
        }
    }

    @Override
    public void onFailure(String error) {
        if (isAttachView()) {
            mView.onHideProgress();
            mView.onDataError(error);
        }
    }
}

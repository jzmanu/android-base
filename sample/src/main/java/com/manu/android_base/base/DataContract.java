package com.manu.android_base.base;

import com.manu.android_base.samples.bean.BlogBean;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.mvp.model.BaseModeCallBack;
import com.manu.core.mvp.model.BaseModel;
import com.manu.core.mvp.view.BaseView;

import java.util.List;

/**
 * Powered by jzman.
 * Created on 2018/7/2 0002.
 */
public interface DataContract {

    interface IDataPresenter {
        void getData(String url);
    }

    interface IDataView extends BaseView {
        void onSetUpView(ResultBean<List<BlogBean>> bean);
    }

    interface IDataModel extends BaseModel {
        interface OnResultListener extends BaseModeCallBack {
            void onGetDataSuccess(ResultBean<List<BlogBean>> bean);
        }
        void getData(String url, OnResultListener listener);
    }
}

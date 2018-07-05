package com.manu.android_base.base.model;


import com.manu.android_base.base.DataContract;
import com.manu.android_base.samples.bean.BlogBean;
import com.manu.core.http.HttpManager;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.listener.ResponseListener;

import java.net.SocketException;
import java.util.List;

import retrofit2.Call;

/**
 * Powered by jzman.
 * Created on 2018/7/2 0002.
 */
public class DataModelImpl implements DataContract.IDataModel{
    private Call call;

    @Override
    public void getData(String url, final OnResultListener listener) {
        if (call!=null){
            call.cancel();
        }
        call = HttpManager.getInstance().get(url, null, new ResponseListener<ResultBean<List<BlogBean>>>() {
            @Override
            public void onSuccess(ResultBean<List<BlogBean>> listResultBean) {
                listener.onGetDataSuccess(listResultBean);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

}

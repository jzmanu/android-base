package com.manu.android_base.samples.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.manu.android_base.R;
import com.manu.core.http.HttpManager;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.listener.ResponseListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * RetrofitSampleActivity.java
 * Created by jzman
 * Powered by 2018/6/12 0024 15:23
 */
public class RetrofitSampleActivity extends AppCompatActivity {

    @BindView(R.id.btnGet)
    Button btnGet;
    @BindView(R.id.btnPost)
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_sample);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnGet, R.id.btnPost})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGet:
                get();
                break;
            case R.id.btnPost:

                break;
        }
    }

    /**
     * 获取某几日干货数据
     * http://gank.io/api/history/content/3/1
     */
    private void get() {
        HttpManager.getInstance().get("history1/content/3/1", null, new ResponseListener<ResultBean<Object>>() {

            @Override
            public void onSuccess(ResultBean<Object> objectResultBean) {
                System.out.println(objectResultBean);
            }

            @Override
            public void onFailure(String msg) {
                System.out.println(msg);
            }

        });
    }

}

package com.manu.android_base.samples.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.manu.android_base.R;
import com.manu.baselibrary.http.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * OkHttpSampleActivity.java
 * Created by jzman
 * Powered by 2018/5/24 0024 15:18
 */
public class OkHttpSampleActivity extends AppCompatActivity implements
        OkHttpSamplePresenter.OnGetRequestListener,
        OkHttpSamplePresenter.OnPostRequestListener,
        OkHttpSamplePresenter.OnDownLoadListener{

    public static final String TAG = "test";

    @BindView(R.id.btnGet)
    Button btnGet;
    @BindView(R.id.btnPost)
    Button btnPost;
    @BindView(R.id.btnDownload)
    Button btnDownload;
    @BindView(R.id.tvData)
    TextView tvData;

    OkHttpSamplePresenter samplePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_sample);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        samplePresenter = new OkHttpSamplePresenter();
        samplePresenter.setOnGetRequestListener(this);
        samplePresenter.setOnPostRequestListener(this);
        samplePresenter.setOnDownLoadListener(this);
    }

    @OnClick({R.id.btnGet, R.id.btnPost, R.id.btnDownload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGet:
                samplePresenter.get("http://gank.io/api/data/Android/3/1");
                break;
            case R.id.btnPost:
                Map<String,String> params = new HashMap<>();
                params.put("url","http://gank.io/api");
                params.put("desc","AIDL");
                params.put("who","I");
                params.put("type","Android");
                params.put("debug","true");
                samplePresenter.post("https://gank.io/api/add2gank",params);
                break;
            case R.id.btnDownload:
                samplePresenter.downLoadFile("http://opvs5q5qo.bkt.clouddn.com/hlj_gov-release.apk");
                break;
        }
    }

    @Override
    public void onGetRequestSuccess(Object obj) {
        LogUtil.log(TAG, obj.toString());
        tvData.setText(obj.toString());
    }

    @Override
    public void onGetRequestFailure(String error) {
        toast(error);
        tvData.setText(error);
    }

    @Override
    public void onPostRequestSuccess(Object obj) {
        LogUtil.log(TAG, obj.toString());
        tvData.setText(obj.toString());
    }

    @Override
    public void onPostRequestFailure(String error) {
        toast(error);
        tvData.setText(error);
    }

    @Override
    public void onDownLoadSuccess() {
        tvData.setText("下载成功");
    }

    @Override
    public void onDownLoadFailure(String error) {
        tvData.setText(error);
    }

    @Override
    public void onDownLoadProgress(long currentProgress, long contentLength) {
        tvData.setText(currentProgress+"/"+contentLength);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

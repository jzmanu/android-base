package com.manu.android_base.samples.core;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.manu.android_base.R;
import com.manu.core.http.HttpManager;
import com.manu.core.http.bean.ResultBean;
import com.manu.core.http.listener.ResponseListener;

import java.io.File;
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
    @BindView(R.id.btnDownLoadFile)
    Button btnDownLoadFile;
    @BindView(R.id.tvDownLoadPercent)
    TextView tvDownLoadPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_sample);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnGet, R.id.btnPost, R.id.btnDownLoadFile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGet:
                getGankData();
//                getWeather();
                break;
            case R.id.btnPost:
                post();
                break;
            case R.id.btnDownLoadFile:
                downLoadFile();
                break;
        }
    }


    /**
     * 获取某几日干货数据
     * http://gank.io/api/history/content/3/1
     */
    private void getGankData() {

        HttpManager.getInstance().get("history/content/3/1", null, new ResponseListener<ResultBean<Object>>() {

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

    /**
     * 请求地址：http://v.juhe.cn/weather/index
     * 请求参数：cityname=%E5%8F%A4%E6%B5%AA&dtype=&format=2&key=0b08838a73ca71823146e238dea51807
     * 请求方式：GET
     */
    private void getWeather() {
        Map<String, Object> params = new HashMap<>();
        params.put("cityname", "古浪");
        params.put("key", "0b08838a73ca71823146e238dea51807");
        HttpManager.mBaseUrl = "http://v.juhe.cn/";
        HttpManager.getInstance().get("weather/index", params, new ResponseListener<ResultBean<Object>>() {

            @Override
            public void onSuccess(ResultBean<Object> objectResultBean) {
                System.out.println(objectResultBean);
            }

            @Override
            public void onFailure(String msg) {
                System.out.println(msg);
            }
        });

        //路径+参数
        String url = "http://v.juhe.cn/weather/index?cityname=古浪&dtype=&format=2&key=0b08838a73ca71823146e238dea51807";
        HttpManager.getInstance().get(url, new ResponseListener<ResultBean<Object>>() {

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

    /**
     * 提交干货到审核区
     * https://gank.io/api/add2gank
     */
    private void post() {
        Map<String, Object> params = new HashMap<>();
        params.put("url", "https://gank.io");
        params.put("desc", "干货集中营");
        params.put("who", "test");
        params.put("type", "Android");
        params.put("debug", "true");
        HttpManager.getInstance().post("add2gank", params, new ResponseListener<ResultBean<Object>>() {
            @Override
            public void onSuccess(ResultBean<Object> resultBean) {
                System.out.println(resultBean);
            }

            @Override
            public void onFailure(String msg) {
                System.out.println(msg);
            }
        });
    }

    private void postFile() {

        HttpManager.getInstance().uploadFile(null, null, new ResponseListener<ResultBean<Object>>() {
            @Override
            public void onSuccess(ResultBean<Object> objectResultBean) {

            }

            @Override
            public void onFailure(String msg) {

            }
//
//            @Override
//            public void onProgress(long bytesRead, long contentLength, boolean isDownload) {
//
//            }
        });
    }

    private void downLoadFile() {
        final File apkFile = new File(Environment.getExternalStorageDirectory().getPath() +
                File.separator + "aa.apk");
        String url = "http://oowljgony.bkt.clouddn.com/baidu_search.apk";


        HttpManager.getInstance().downLoadFile(url, new ResponseListener<ResultBean<File>>() {
            @Override
            public void onSuccess(ResultBean<File> resultBean) {
                System.out.println("下载成功" + resultBean.toString());
            }

            @Override
            public void onFailure(String msg) {
                System.out.println("下载失败：" + msg);
            }

            @Override
            public String getDownLoadPath() {
                return apkFile.getPath();
            }

            @Override
            public void onDownLoadComplete() {
                System.out.println("下载完成");
            }

            @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
                System.out.println("bytesRead--->" + bytesRead+"--->contentLength--->"+contentLength+"--->done--->"+done);
                System.out.println("当前线程："+Thread.currentThread());
                tvDownLoadPercent.setText(bytesRead+"");
            }
        });
    }


}

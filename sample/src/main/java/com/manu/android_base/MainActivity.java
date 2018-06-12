package com.manu.android_base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.manu.android_base.samples.ImageSampleActivity;
import com.manu.android_base.samples.PopupWindowSampleActivity;
import com.manu.android_base.samples.core.RetrofitSampleActivity;
import com.manu.android_base.samples.http.OkHttpSampleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MainActivity.java
 * Created by jzman
 * Powered by 2018/5/24 0024 15:24
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnImageUtil)
    Button btnImageUtil;
    @BindView(R.id.btnOkHttp)
    Button btnOkHttp;
    @BindView(R.id.btnPopupWindow)
    Button btnPopupWindow;
    @BindView(R.id.btnCore)
    Button btnCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnImageUtil, R.id.btnOkHttp, R.id.btnPopupWindow,R.id.btnCore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnImageUtil:
                startActivity(new Intent(this, ImageSampleActivity.class));
                break;
            case R.id.btnOkHttp:
                startActivity(new Intent(this, OkHttpSampleActivity.class));
                break;
            case R.id.btnPopupWindow:
                startActivity(new Intent(this, PopupWindowSampleActivity.class));
                break;
            case R.id.btnCore:
                startActivity(new Intent(this, RetrofitSampleActivity.class));
                break;
        }
    }

}

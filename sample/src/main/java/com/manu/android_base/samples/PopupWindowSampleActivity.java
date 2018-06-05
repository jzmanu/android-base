package com.manu.android_base.samples;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.manu.android_base.R;
import com.manu.baselibrary.widget.MPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: jzman
 * @time: 2018/6/5 0005 13:59
 */
public class PopupWindowSampleActivity extends AppCompatActivity {

    @BindView(R.id.btnTop)
    Button btnTop;
    @BindView(R.id.btnRight)
    Button btnRight;
    @BindView(R.id.btnBottom)
    Button btnBottom;
    @BindView(R.id.btnLeft)
    Button btnLeft;

    View contentView;
    @BindView(R.id.btnCenter)
    Button btnCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window_sample);
        ButterKnife.bind(this);
        contentView = LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null);
    }

    @OnClick({R.id.btnTop, R.id.btnRight, R.id.btnBottom, R.id.btnLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnTop:
                showPopupWindow(MPopupWindow.LocationType.TOP);
                break;
            case R.id.btnRight:
                break;
            case R.id.btnBottom:
                showPopupWindow(MPopupWindow.LocationType.BOTTOM);
                break;
            case R.id.btnLeft:
                break;
        }
    }

    private void showPopupWindow(MPopupWindow.LocationType type) {
        MPopupWindow popupWindow = new MPopupWindow
                .Builder(this)
                .setContentView(contentView)
                .setBackgroundDrawable(new ColorDrawable(Color.GRAY))
                .build();
        popupWindow.showPopupWindow(btnCenter, type);
    }

}

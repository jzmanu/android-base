package com.manu.android_base.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.manu.android_base.R;
import com.manu.android_base.samples.bean.DistractBean;
import com.manu.android_base.wheelview.adapter.ArrayWheelAdapter;
import com.manu.android_base.wheelview.widget.WheelView;
import com.manu.core.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WheelActivity extends AppCompatActivity implements
        WheelView.OnWheelItemClickListener<String> {

    @BindView(R.id.wvCity)
    WheelView wvCity;
    @BindView(R.id.wvCounty)
    WheelView wvCounty;
    @BindView(R.id.wvTown)
    WheelView wvTown;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    private DistractBean bean;
    private List<String> mCityList;
    private HashMap<String, List<String>> mCountyMap;
    private HashMap<String, List<String>> mTownMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        ButterKnife.bind(this);
        initData();
        initWheel();
    }

    private void initData() {
        String json = Util.getOriginalFundData(this, "area.json");
        Gson gson = new Gson();
        bean = gson.fromJson(json, DistractBean.class);

        mCityList = new ArrayList<>();
        mCountyMap = new HashMap<>();
        mTownMap = new HashMap<>();

        for (int i = 0; i < bean.getChildren().size(); i++) {
            String cityName = bean.getChildren().get(i).getName();
            mCityList.add(cityName);
            List<String> countyList = new ArrayList<>();
            for (int j = 0; j < bean.getChildren().get(i).getChildren().size(); j++) {
                countyList.add(bean.getChildren().get(i).getChildren().get(j).getName());
                String countyName = bean.getChildren().get(i).getChildren().get(j).getName();
                List<String> townList = new ArrayList<>();
                int townCount = bean.getChildren().get(i).getChildren().get(j).getChildren().size();
                if (townCount == 0) {
                    townList.add("无");
                } else {
                    for (int m = 0; m < bean.getChildren().get(i).getChildren().get(j).getChildren().size(); m++) {
                        townList.add(bean.getChildren().get(i).getChildren().get(j).getChildren().get(m).getName());
                    }
                }
                System.out.println(countyName + "--->" + townList);
                mTownMap.put(countyName, townList);
            }
            System.out.println(cityName + "--city->" + countyList);
            mCountyMap.put(cityName, countyList);
        }

        wvCity.setOnWheelItemClickListener(this);
        wvCounty.setOnWheelItemClickListener(this);
        wvTown.setOnWheelItemClickListener(this);
    }

    private void initWheel() {

        wvCity.setWheelAdapter(new ArrayWheelAdapter(this));
        wvCity.setSkin(WheelView.Skin.Holo);
        wvCity.setWheelData(mCityList);

        wvCounty.setWheelAdapter(new ArrayWheelAdapter(this));
        wvCounty.setSkin(WheelView.Skin.Holo);
        wvCounty.setWheelData(mCountyMap.get(mCityList.get(wvCity.getSelection())));
        wvCity.join(wvCounty);
        wvCity.joinDatas(mCountyMap);

        wvTown.setWheelAdapter(new ArrayWheelAdapter(this));
        wvTown.setSkin(WheelView.Skin.Holo);
        String currentCity = mCityList.get(wvCity.getSelection());
        String currentCounty = mCountyMap.get(currentCity).get(wvCounty.getSelection());
        wvTown.setWheelData(mTownMap.get(currentCounty));
        wvCounty.join(wvTown);
        wvCounty.joinDatas(mTownMap);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, int position, String s) {
        System.out.println("---------------------->" + s);
        switch (parent.getId()) {
            case R.id.wvCity:
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                break;
            case R.id.wvCounty:
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                break;
            case R.id.wvTown:
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnClick({R.id.btnConfirm, R.id.tvAddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnConfirm:
                String address = (String)wvCity.getSelectionItem()
                        +wvCounty.getSelectionItem()
                        +wvTown.getSelectionItem();
                tvAddress.setText(address);
                break;
            case R.id.tvAddress:
                break;
        }
    }
}

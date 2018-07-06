package com.manu.android_base.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.manu.android_base.R;
import com.manu.android_base.samples.bean.DistractBean;
import com.manu.android_base.wheelview.adapter.ArrayWheelAdapter;
import com.manu.android_base.wheelview.widget.WheelView;
import com.manu.core.utils.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WheelActivity extends AppCompatActivity{

    @BindView(R.id.wvCity)
    WheelView wvCity;
    @BindView(R.id.wvCounty)
    WheelView wvCounty;
    @BindView(R.id.wvTown)
    WheelView wvTown;

    private DistractBean bean;
    private List<String> mCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        ButterKnife.bind(this);
        initData();
        initWheel();
    }

    private void initData() {
        String json = Util.getOriginalFundData(this,"area.json");
        Gson gson = new Gson();
        bean = gson.fromJson(json, DistractBean.class);
//        initCountyMap();
    }

    private void initWheel() {

        wvCity.setWheelAdapter(new ArrayWheelAdapter(this));
        wvCity.setSkin(WheelView.Skin.Holo);
        wvCity.setWheelData(createMainDatas());

        wvCounty.setWheelAdapter(new ArrayWheelAdapter(this));
        wvCounty.setSkin(WheelView.Skin.Holo);
        //获得当前已经选择的城市
//        String currentCity = bean.getChildren().get(wvCity.getSelection()).getName();
        wvCounty.setWheelData(createSubDatas().get(createMainDatas().get(wvCity.getSelection())));
//        wvCounty.setWheelData(bean.getChildren().get(wvCity.getSelection()).getChildren());
        wvCity.join(wvCounty);
        wvCity.joinDatas(createSubDatas());
//
        wvTown.setWheelAdapter(new ArrayWheelAdapter(this));
        wvTown.setSkin(WheelView.Skin.Holo);
        //获得当前已经选择的城市
        String currentCity = createMainDatas().get(wvCity.getSelection());
        String currentCounty = createSubDatas().get(currentCity).get(wvCounty.getSelection());
        wvTown.setWheelData(createChildDatas().get(currentCounty));
        wvCounty.join(wvTown);
        wvCounty.joinDatas(createChildDatas());
    }

    private List<String> createMainDatas() {
        String[] strings = {"黑龙江", "吉林", "辽宁"};
        return Arrays.asList(strings);
    }

    private HashMap<String, List<String>> createSubDatas() {
        HashMap<String, List<String>> map = new HashMap<>();
        String[] strings = {"黑龙江", "吉林", "辽宁"};
        String[] s1 = {"哈尔滨", "齐齐哈尔", "大庆"};
        String[] s2 = {"长春", "吉林"};
        String[] s3 = {"沈阳", "大连", "鞍山", "抚顺"};
        String[][] ss = {s1, s2, s3};
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }

//    private void initCountyMap(){
//        mCountyMap = new HashMap<>();
//        for (int i=0; i<bean.getChildren().size(); i++){
//            mCountyMap.put(bean.getChildren().get(i).getName(),bean.getChildren().get(i).getChildren());
//        }
//    }

    private HashMap<String, List<String>> createChildDatas() {
        HashMap<String, List<String>> map = new HashMap<>();
        String[] strings = {"哈尔滨", "齐齐哈尔", "大庆", "长春", "吉林", "沈阳", "大连", "鞍山", "抚顺"};
        String[] s1 = {"道里区", "道外区", "南岗区", "香坊区"};
        String[] s2 = {"龙沙区", "建华区", "铁锋区"};
        String[] s3 = {"红岗区", "大同区"};
        String[] s11 = {"南关区", "朝阳区"};
        String[] s12 = {"龙潭区"};
        String[] s21 = {"和平区", "皇姑区", "大东区", "铁西区"};
        String[] s22 = {"中山区", "金州区"};
        String[] s23 = {"铁东区", "铁西区"};
        String[] s24 = {"新抚区", "望花区", "顺城区"};
        String[][] ss = {s1, s2, s3, s11, s12, s21, s22, s23, s24};
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }
}

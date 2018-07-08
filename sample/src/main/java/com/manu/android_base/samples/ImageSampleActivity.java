package com.manu.android_base.samples;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.manu.android_base.R;
import com.manu.baselibrary.image.PicassoUtils;
import com.manu.baselibrary.widget.DoubleClickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ImageSampleActivity.java
 * Created by jzman
 * Powered by 2018/5/24 0024 15:18
 */
public class ImageSampleActivity extends AppCompatActivity {

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.btnNetwork)
    Button btnNetwork;
    @BindView(R.id.btnLocal)
    Button btnLocal;
    @BindView(R.id.btnCircle)
    Button btnCircle;
    @BindView(R.id.tvClickMe)
    DoubleClickTextView tvClickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        tvClickMe.setOnDoubleClickListener(new DoubleClickTextView.OnDoubleClickListener() {
            @Override
            public void onDoubleClick(boolean isFirstClick) {
                if (isFirstClick){
                    Toast.makeText(ImageSampleActivity.this, "第一次点击", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ImageSampleActivity.this, "第二次点击", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick({R.id.ivImage, R.id.btnNetwork, R.id.btnLocal, R.id.btnCircle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivImage:
                break;
            case R.id.btnNetwork:
                String imageUrl = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3870827197,2123952459&fm=27&gp=0.jpg";
                PicassoUtils.loadNetworkImage(imageUrl, ivImage);
                break;
            case R.id.btnLocal:
                String path = Environment.getExternalStorageDirectory() + "/ic_nj.jpg";
                PicassoUtils.loadLocalImage(path, ivImage);
                break;
            case R.id.btnCircle:
                PicassoUtils.loadResourceImage(R.drawable.ic_pic, ivImage);
                break;
        }
    }
}

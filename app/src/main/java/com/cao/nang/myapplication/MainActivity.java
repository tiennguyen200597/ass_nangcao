package com.cao.nang.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cao.nang.myapplication.base.BaseActivity;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvEdu;
    private TextView tvMap;
    private TextView tvNew;
    private TextView tvSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        tvEdu.setOnClickListener(this);
        tvMap.setOnClickListener(this);
        tvNew.setOnClickListener(this);
        tvSocial.setOnClickListener(this);
    }

    private void initViews(){
        tvEdu = findViewById(R.id.tvEdu);
        tvMap = findViewById(R.id.tvMap);
        tvNew = findViewById(R.id.tvNew);
        tvSocial = findViewById(R.id.tvSocial);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tvEdu:
                navigateActivity(Student.class);
                break;
            case R.id.tvMap:
                navigateActivity(MapsActivity.class);
                break;
            case R.id.tvNew:
                navigateActivity(News.class);
                break;
            case R.id.tvSocial:
                navigateActivity(ActivityFb.class);
                break;
        }
    }
}

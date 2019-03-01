package com.cao.nang.myapplication.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public void navigateActivity(Class target){
        startActivity(new Intent(this,target));
    }

}



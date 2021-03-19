package com.smri.smarttracker.activities.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.smri.smarttracker.R;
import com.smri.smarttracker.activities.main.MainActivity;
import com.smri.smarttracker.activities.login.LoginActivity;


public class SplashActivity extends Activity implements SplashContract.View {
    Handler handler;
    SplashContract.Presenter mPresenter = new SplashPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        mPresenter.attachView(this);
    }

    @Override
    public void showMainScreen() {
        Intent intent= new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoginScreen() {
        Intent intent= new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

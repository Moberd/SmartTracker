package com.smri.smarttracker.screens.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.main.MainActivity;
import com.smri.smarttracker.screens.login.LoginActivity;


public class SplashActivity extends Activity implements SplashContract.View {
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

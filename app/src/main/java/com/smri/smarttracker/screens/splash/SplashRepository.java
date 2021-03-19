package com.smri.smarttracker.screens.splash;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashRepository implements SplashContract.Repository{
    SplashContract.Presenter mPresenter;

    @Override
    public void attachPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean checkAuthorizationStatus() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null;
    }
}

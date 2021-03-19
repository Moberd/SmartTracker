package com.smri.smarttracker.screens.main;

public class MainRepository implements MainContract.Repository {

    MainContract.Presenter mPresenter;

    @Override
    public void attachPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

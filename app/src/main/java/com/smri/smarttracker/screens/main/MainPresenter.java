package com.smri.smarttracker.screens.main;


public class MainPresenter implements MainContract.Presenter{
    private boolean viewIsAttached = false;
    MainContract.View mView = null;
    private final MainRepository mRepository = new MainRepository();

    @Override
    public void attachView(MainContract.View view) {
        viewIsAttached = true;
        mView = view;
        mRepository.attachPresenter(this);
    }

    @Override
    public void detachView() {
        viewIsAttached = false;
        mView = null;
    }
}

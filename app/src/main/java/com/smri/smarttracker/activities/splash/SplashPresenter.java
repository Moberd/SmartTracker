package com.smri.smarttracker.activities.splash;

public class SplashPresenter implements SplashContract.Presenter  {

    private boolean viewIsAttached = false;
    SplashContract.View mView = null;
    private final SplashRepository mRepository = new SplashRepository();

    @Override
    public void attachView(SplashContract.View view) {
        viewIsAttached = true;
        mView = view;
        mRepository.attachPresenter(this);
        if(mRepository.checkAuthorizationStatus()){
            mView.showMainScreen();
        } else {
            mView.showLoginScreen();
        }
    }

    @Override
    public void detachView() {
        viewIsAttached = false;
        mView = null;
    }
}

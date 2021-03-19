package com.smri.smarttracker.screens.splash;

public interface SplashContract {
    interface View{
        void showMainScreen();
        void showLoginScreen();
    }

    interface Presenter{
        void attachView(View view);
        void detachView();
    }

    interface Repository{

        void attachPresenter(Presenter presenter);
        boolean checkAuthorizationStatus();
    }
}

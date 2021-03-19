package com.smri.smarttracker.screens.main;


public interface MainContract {
    interface View{
    }

    interface Presenter{
        void attachView(MainContract.View view);
        void detachView();
    }

    interface Repository{
        void attachPresenter(MainContract.Presenter presenter);
    }
}

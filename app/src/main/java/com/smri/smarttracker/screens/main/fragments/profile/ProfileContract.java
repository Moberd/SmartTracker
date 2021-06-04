package com.smri.smarttracker.screens.main.fragments.profile;

public interface ProfileContract {
    interface View{
        void showData(String name,String email, String phone, String lab);
    }
    interface Presenter{
        void attachView( ProfileContract.View view);
        void detachView();
        void onExitBtnPressed();
        void onNameChanged(String name);
        void onEmailChanged(String email);
        void onPhoneChanged(String phone);
        void onLabChanged(String lab);
        void getDataFormRep();
        void sendDataToView(String name,String email, String phone, String lab);
    }
    interface Repository{
        void attachPresenter(ProfileContract.Presenter presenter);
        void logOut();
        void getData();
        void setName(String name);
        void setEmail(String email);
        void setPhone(String phone);
        void setLab(String lab);
    }
}

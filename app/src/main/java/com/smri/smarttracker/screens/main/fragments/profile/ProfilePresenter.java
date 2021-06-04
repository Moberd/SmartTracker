package com.smri.smarttracker.screens.main.fragments.profile;

public class ProfilePresenter implements ProfileContract.Presenter {

    private boolean viewIsAttached = false;
    ProfileContract.View mView = null;
    private ProfileRepository mRepository;

    ProfilePresenter(){
        mRepository = new ProfileRepository();
    }
    @Override
    public void attachView(ProfileContract.View view) {
        viewIsAttached = true;
        mView = view;
        mRepository.attachPresenter(this);
    }

    @Override
    public void detachView() {
        viewIsAttached = false;
        mView = null;
    }

    @Override
    public void onNameChanged(String name) {
        mRepository.setName(name);
    }

    @Override
    public void onEmailChanged(String email) {
        mRepository.setEmail(email);
    }

    @Override
    public void onPhoneChanged(String phone) {
        mRepository.setPhone(phone);
    }

    @Override
    public void onLabChanged(String lab) {
        mRepository.setLab(lab);
    }

    @Override
    public void getDataFormRep() {
        mRepository.getData();
    }

    @Override
    public void sendDataToView(String name, String email, String phone, String lab) {
        mView.showData(name,email,phone, lab);
    }

    @Override
    public void onExitBtnPressed(){
        mRepository.logOut();
    }
}

package com.smri.smarttracker.activities.login;

public class LoginPresenter implements LoginContract.Presenter {

    private boolean viewIsAttached = false;
    LoginContract.View mView = null;
    private LoginRepository mRepository;

    public LoginPresenter(LoginContract.View mView){
        this.mView = mView;
        this.mRepository = new LoginRepository(this);
    }

    @Override
    public void attachView(LoginContract.View view) {
        viewIsAttached = true;
        mView = view;
        mRepository.attachPresenter(this);
    }

    @Override
    public void detachView() {
        viewIsAttached = false;
        mView = null;
    }

    private boolean checkboxEmptiness(String loginText, String passText){
        boolean result = false;
        if (loginText.equals("")) {
            //mView.showLoginError(App.instance.getString(R.string.edit_is_empty));
            result = true;
        }
        if (passText.equals("")) {
            //mView!!.showPasswordError(App.instance.getString(R.string.edit_is_empty))
            result = true;
        }
        return result;
    }

    @Override
    public void onBtnLoginClick(String loginText, String passText) {
        if (checkboxEmptiness(loginText, passText)) return;
        mView.showLoadProgress();
        mRepository.signInAccount(loginText, passText);
    }

    @Override
    public void onBtnResetClick(String loginText) {
        if (loginText.equals("")) {
            //mView.showLoginError(App.instance.getString(R.string.edit_is_empty))
            return;
        }
        mView.showLoadProgress();
        mRepository.resetPassword(loginText);
    }

    @Override
    public void onBtnCreateAccountClick(String login, String password) {
        if (checkboxEmptiness(login, password)) return;
        mView.showLoadProgress();
        mRepository.createAccount(login, password);
    }

    @Override
    public void onLoginFinished() {
        mView.launchMainActivity();
        mView.hideLoadProgress();
    }

    @Override
    public void onRegistrationFinished() {
        mRepository.createUserDocument();
        mView.launchMainActivity();
        mView.hideLoadProgress();
    }

    @Override
    public void onResetFinished() {
        /*mView!!.showToast(
                App.instance.getString(R.string.restore_email_sent),
                ToastMode.INFO
        )*/
        mView.enterLoginMode();
        mView.hideLoadProgress();
    }

    @Override
    public void onResetFailed(String reason) {
        /*mView.showToast(
                Utils.getErrorMessage(reason),
                ToastMode.ERROR
        )*/
        mView.hideLoadProgress();
    }

    @Override
    public void onRegistrationFailed(String reason) {
        /*mView!!.showToast(
                Utils.getErrorMessage(reason),
                ToastMode.ERROR
        )*/
        mView.hideLoadProgress();
    }

    @Override
    public void onLoginFailed(String reason) {
        /*mView.showToast(
                Utils.getErrorMessage(reason),
                ToastMode.ERROR
        )*/
        mView.hideLoadProgress();
    }
}

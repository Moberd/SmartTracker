package com.smri.smarttracker.activities.login;

public interface LoginContract {
    interface View {
        void launchMainActivity();
        void showLoginError(String text);
        void showPasswordError(String text);
        void showLoadProgress();
        void hideLoadProgress();
        void enterLoginMode();
        void showToast(String message, LoginActivity.ToastMode mode);
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void onBtnLoginClick(String loginText,String  passText);
        void onBtnResetClick(String loginText);
        void onBtnCreateAccountClick(String login,String  password);
        void onLoginFinished();
        void onRegistrationFinished();
        void onResetFinished();
        void onResetFailed(String reason);
        void onRegistrationFailed(String reason);
        void onLoginFailed(String reason);
    }

    interface Repository {
        void resetPassword(String email);
        boolean checkAccount();
        void attachPresenter(Presenter presenter);
        void createAccount(String email, String password);
        void signInAccount(String email, String  password);
        void createUserDocument();
    }
}

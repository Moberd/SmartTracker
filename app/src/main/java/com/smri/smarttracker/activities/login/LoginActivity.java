package com.smri.smarttracker.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.smri.smarttracker.R;
import com.smri.smarttracker.activities.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter mPresenter;
    LayoutMode currentMode = LayoutMode.LOGIN;
    ImageButton btnBack;
    Button btnLogin;
    TextView btnCreateAccount;
    TextView btnForgot;
    TextInputLayout tilLogin;
    TextInputLayout tilPassword;
    EditText etLogin;
    EditText etPassword;
    ProgressBar pbLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);
        mPresenter = new LoginPresenter(this);
        btnBack = findViewById(R.id.btnBack);
        btnLogin = findViewById(R.id.btnLogin);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnForgot = findViewById(R.id.btnForgot);
        tilLogin = findViewById(R.id.tilLogin);
        tilPassword = findViewById(R.id.tilPassword);
        pbLoading = findViewById(R.id.pbLoading);
        setUpListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    void setUpListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentMode == LayoutMode.LOGIN){
                    mPresenter.onBtnLoginClick(
                            etLogin.getText().toString().trim(),
                            etPassword.getText().toString().trim());
                } else if(currentMode == LayoutMode.REGISTRATION){
                    mPresenter.onBtnCreateAccountClick(
                            etLogin.getText().toString().trim(),
                            etPassword.getText().toString().trim());
                } else if(currentMode == LayoutMode.RESTORING){
                    mPresenter.onBtnResetClick(etLogin.getText().toString().trim());
                }
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterRegistrationMode();
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterRestoringMode();
            }
        });

        etPassword.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty() && s.toString().trim().length() < 6) {
                    showPasswordError("Пароль должен быть больше 5 символов");
                } else {
                    btnLogin.setEnabled(true);
                    tilPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLogin.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnLogin.setEnabled(true);
                tilLogin.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void launchMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showLoginError(String text) {
        btnLogin.setEnabled(false);
        tilLogin.setError(text);
    }

    @Override
    public void showPasswordError(String text) {
        btnLogin.setEnabled(false);
        tilPassword.setError(text);
    }

    @Override
    public void showLoadProgress() {
        pbLoading.setVisibility(View.VISIBLE);
        btnLogin.setText("");
        btnLogin.setEnabled(false);
        btnBack.setEnabled(false);
    }

    @Override
    public void hideLoadProgress() {
        pbLoading.setVisibility(View.INVISIBLE);
        String newText;
        switch (currentMode){
            case LOGIN:
                newText = "Login"; //resources.getString(R.string.log_in);
                break;
            case RESTORING:
                newText = "Restore";//resources.getString(R.string.restore);
                break;
            case REGISTRATION:
                newText = "Registration"; //resources.getString(R.string.create_new_account)
                break;
            default:
                newText = "Error";
                break;
        }
        btnLogin.setText(newText);
        btnLogin.setEnabled(true);
        btnBack.setEnabled(true);
    }

    @Override
    public void enterLoginMode() {
        btnBack.setVisibility(View.GONE);
        btnLogin.setText("Login");// = resources.getString(R.string.log_in)
        btnCreateAccount.setVisibility(View.VISIBLE);
        btnForgot.setVisibility(View.VISIBLE);
        tilPassword.setVisibility(View.VISIBLE);
        currentMode = LayoutMode.LOGIN;
    }

    private void enterRegistrationMode() {
        btnBack.setVisibility(View.VISIBLE);
        btnLogin.setText("create new account");// = resources.getString(R.string.create_new_account)
        btnCreateAccount.setVisibility(View.GONE);
        btnForgot.setVisibility(View.GONE);
        tilPassword.setVisibility(View.VISIBLE);
        currentMode = LayoutMode.REGISTRATION;
    }

    private void enterRestoringMode() {
        btnBack.setVisibility(View.VISIBLE);
        btnLogin.setText("Restore");// = resources.getString(R.string.restore)
        btnCreateAccount.setVisibility(View.GONE);
        btnForgot.setVisibility(View.GONE);
        tilPassword.setVisibility(View.GONE);
        currentMode = LayoutMode.RESTORING;
    }

    public void onBackPressed() {
        if (currentMode == LayoutMode.LOGIN) {
            finish();
        } else {
            enterLoginMode();
        }
    }

    enum LayoutMode { LOGIN, REGISTRATION, RESTORING }
}

package com.smri.smarttracker.activities.login;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class LoginRepository implements LoginContract.Repository {

    private FirebaseAuth mAuth;
    private LoginContract.Presenter mPresenter;

    public LoginRepository(LoginContract.Presenter mPresenter){
        this.mPresenter = mPresenter;
    }

    @Override
    public boolean checkAccount() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public void attachPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    mPresenter.onRegistrationFinished();
                    createUserDocument();
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthException e) {
                        mPresenter.onLoginFailed(task.getException().toString());
                    } catch (FirebaseNetworkException e) {
                        mPresenter.onLoginFailed("ERROR_NETWORK");
                    } catch (Exception e) {
                        mPresenter.onLoginFailed(task.getException().toString());
                    }
                }
            }
        });
    }

    @Override
    public void signInAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthException e) {
                        mPresenter.onLoginFailed(task.getException().toString());
                    } catch (FirebaseNetworkException e) {
                        mPresenter.onLoginFailed("ERROR_NETWORK");
                    } catch (Exception e) {
                        mPresenter.onLoginFailed(task.getException().toString());
                    }
                }
            }
        });
    }

    @Override
    public void resetPassword(String email) {

    }

    @Override
    public void createUserDocument() {

    }
}

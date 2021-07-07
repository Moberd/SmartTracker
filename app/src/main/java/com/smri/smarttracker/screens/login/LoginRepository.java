package com.smri.smarttracker.screens.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
                        mPresenter.onLoginFailed(e.getLocalizedMessage());
                    } catch (FirebaseNetworkException e) {
                        mPresenter.onLoginFailed("ERROR_NETWORK");
                    } catch (Exception e) {
                        mPresenter.onLoginFailed(e.getLocalizedMessage());
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
                    mPresenter.onLoginFinished();
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthException e) {
                        mPresenter.onLoginFailed(e.getLocalizedMessage());
                    } catch (FirebaseNetworkException e) {
                        mPresenter.onLoginFailed("NETWORK ERROR");
                    } catch (Exception e) {
                        mPresenter.onLoginFailed(e.getLocalizedMessage());
                    }
                }
            }
        });
    }

    @Override
    public void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    @Override
    public void createUserDocument() {
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Empty");
        data.put("email", user.getEmail());
        data.put("laboratory","Empty");
        data.put("laboratory_number","Empty");
        data.put("phone_number","Empty");
        db.collection("users").document(mAuth.getUid()).set(data);
    }
}

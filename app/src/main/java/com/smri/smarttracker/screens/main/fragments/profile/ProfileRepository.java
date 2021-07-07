package com.smri.smarttracker.screens.main.fragments.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smri.smarttracker.utils.Chemical;

public class ProfileRepository implements ProfileContract.Repository {

    private ProfileContract.Presenter mPresenter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String userID = FirebaseAuth.getInstance().getUid();
    @Override
    public void attachPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void getData() {
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String name = documentSnapshot.getString("name");
                String email = documentSnapshot.getString("email");
                String phone = documentSnapshot.getString("phone_number");
                String lab = documentSnapshot.getString("laboratory");
                String loc = documentSnapshot.getString("laboratory_number");
                mPresenter.sendDataToView(name, email, phone, lab, loc);
            }
        });
    }

    @Override
    public void setName(String name) {
        db.collection("users").document(userID).update("name",name);
    }

    @Override
    public void setEmail(String email) {
        db.collection("users").document(userID).update("email",email);
    }

    @Override
    public void setPhone(String phone) {
        db.collection("users").document(userID).update("phone_number",phone);
    }

    @Override
    public void setLab(String lab) {
        db.collection("users").document(userID).update("laboratory",lab);
    }

    @Override
    public void setLabNumber(String loc) {
        db.collection("users").document(userID).update("laboratory_number",loc);
    }
}

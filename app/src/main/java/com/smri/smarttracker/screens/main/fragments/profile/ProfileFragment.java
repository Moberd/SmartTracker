package com.smri.smarttracker.screens.main.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.splash.SplashActivity;
import com.smri.smarttracker.utils.FabFragmentListener;

public class ProfileFragment extends Fragment {
    static boolean editMode = false;
    public FabFragmentListener listener;
    ImageView btnEditEmail;
    ImageView btnEditPhone;
    ImageView btnEditLabNumber;
    ImageView btnEditLab;
    ImageView btnEditUserName;
    TextView tvEmail;
    TextView tvPhone;
    TextView tvLab;
    TextView btnSignOut;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        btnEditEmail = view.findViewById(R.id.edit_email);
        btnEditPhone = view.findViewById(R.id.edit_phone_number);
        btnEditLabNumber = view.findViewById(R.id.edit_lab_number);
        btnEditUserName = view.findViewById(R.id.edit_username);
        btnEditLab = view.findViewById(R.id.edit_lab);
        btnSignOut = view.findViewById(R.id.singBtn);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPhone = view.findViewById(R.id.tv_phone_number);
        tvLab = view.findViewById(R.id.tv_laboratory_number);

        btnSignOut.setOnClickListener(v -> exitFirebase());
        return view;
    }

    public static boolean getEditMode() {
        return editMode;
    }

    public void editFABListener(){
        if(!editMode){
            enterEditMode();
        } else {
            exitEditMode();
        }
    }

    public void enterEditMode(){
        editMode = true;
        btnEditEmail.setVisibility(View.VISIBLE);
        btnEditPhone.setVisibility(View.VISIBLE);
        btnEditLabNumber.setVisibility(View.VISIBLE);
        btnEditUserName.setVisibility(View.VISIBLE);
        btnEditUserName.setVisibility(View.VISIBLE);
    }

    public void exitEditMode(){
        editMode = false;
        btnEditEmail.setVisibility(View.GONE);
        btnEditPhone.setVisibility(View.GONE);
        btnEditLabNumber.setVisibility(View.GONE);
        btnEditUserName.setVisibility(View.GONE);
        btnEditUserName.setVisibility(View.GONE);
    }

    public void exitFirebase(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), SplashActivity.class);
        getContext().startActivity(intent);
        getActivity().finish();
    }


}

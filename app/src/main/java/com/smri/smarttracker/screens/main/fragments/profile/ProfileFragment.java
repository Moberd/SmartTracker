package com.smri.smarttracker.screens.main.fragments.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.splash.SplashActivity;
import com.smri.smarttracker.utils.FabFragmentListener;

import es.dmoral.toasty.Toasty;

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
    TextView tvLabNumber;
    TextView tvUserName;
    TextView tvLab;
    TextView btnSignOut;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editMode = false;
        View view = inflater.inflate(R.layout.fragment_profile, null);
        btnEditEmail = view.findViewById(R.id.edit_email);
        btnEditPhone = view.findViewById(R.id.edit_phone_number);
        btnEditLabNumber = view.findViewById(R.id.edit_lab_number);
        btnEditUserName = view.findViewById(R.id.edit_username);
        btnEditLab = view.findViewById(R.id.edit_lab);
        btnSignOut = view.findViewById(R.id.singBtn);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPhone = view.findViewById(R.id.tv_phone_number);
        tvLabNumber = view.findViewById(R.id.tv_laboratory_number);
        tvLab = view.findViewById(R.id.tv_laboratory);
        tvUserName = view.findViewById(R.id.tv_user_name);
        setUpListeners();
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
        //btnEditLabNumber.setVisibility(View.VISIBLE);
        btnEditUserName.setVisibility(View.VISIBLE);
        //btnEditLab.setVisibility(View.VISIBLE);
    }

    public void exitEditMode(){
        editMode = false;
        btnEditEmail.setVisibility(View.GONE);
        btnEditPhone.setVisibility(View.GONE);
        //btnEditLabNumber.setVisibility(View.GONE);
        btnEditUserName.setVisibility(View.GONE);
        //btnEditUserLab.setVisibility(View.GONE);
    }

    void setUpListeners(){
        btnEditUserName.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.edit_dialog_layout);
            dialog.setTitle("Change Your Name");
            Button btnSave = dialog.findViewById(R.id.btnSave);
            EditText editText = dialog.findViewById(R.id.etNew);
            btnSave.setOnClickListener(dialogV -> {
                String newText = editText.getText().toString();
                tvUserName.setText(newText);
                Toasty.info(getContext(),newText).show();
                dialog.dismiss();
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(dialogV -> {
                dialog.dismiss();
            });
            dialog.show();
        });

        btnEditEmail.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.edit_dialog_layout);
            dialog.setTitle("Change Email");
            Button btnSave = dialog.findViewById(R.id.btnSave);
            EditText editText = dialog.findViewById(R.id.etNew);
            btnSave.setOnClickListener(dialogV -> {
                String newText = editText.getText().toString();
                tvEmail.setText(newText);
                Toasty.info(getContext(),newText).show();
                dialog.dismiss();
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(dialogV -> {
                dialog.dismiss();
            });
            dialog.create();
            dialog.show();
        });

        btnEditPhone.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.edit_dialog_layout);
            dialog.setTitle("Change Phone Number");
            Button btnSave = dialog.findViewById(R.id.btnSave);
            EditText editText = dialog.findViewById(R.id.etNew);
            btnSave.setOnClickListener(dialogV -> {
                String newText = editText.getText().toString();
                tvPhone.setText(newText);
                Toasty.info(getContext(),newText).show();
                dialog.dismiss();
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(dialogV -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    public void exitFirebase(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), SplashActivity.class);
        getContext().startActivity(intent);
        getActivity().finish();
    }


}

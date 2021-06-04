package com.smri.smarttracker.screens.main.fragments.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.main.fragments.database.DataBasePresenter;
import com.smri.smarttracker.screens.splash.SplashActivity;
import com.smri.smarttracker.utils.FabFragmentListener;

import es.dmoral.toasty.Toasty;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    View view;
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
    ProfileContract.Presenter mPresenter;
    ProgressBar progressBar;
    ConstraintLayout csData;
    RelativeLayout rellay1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePresenter();
        editMode = false;
        mPresenter.getDataFormRep();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        view = inflater.inflate(R.layout.fragment_profile, null);
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
        progressBar = view.findViewById(R.id.progressProfile);
        csData = view.findViewById(R.id.constraint_data);
        rellay1 = view.findViewById(R.id.rellay1);
        setUpListeners();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
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
        btnEditLab.setVisibility(View.VISIBLE);
    }

    public void exitEditMode(){
        editMode = false;
        btnEditEmail.setVisibility(View.GONE);
        btnEditPhone.setVisibility(View.GONE);
        btnEditLabNumber.setVisibility(View.GONE);
        btnEditUserName.setVisibility(View.GONE);
        btnEditLab.setVisibility(View.GONE);
    }

    void setUpListeners(){
        btnSignOut.setOnClickListener(v -> signOutBtn());

        btnEditUserName.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
            dialog.setContentView(R.layout.edit_dialog_layout);
            dialog.setTitle("Change Your Name");
            Button btnSave = dialog.findViewById(R.id.btnSave);
            EditText editText = dialog.findViewById(R.id.etNew);
            btnSave.setOnClickListener(dialogV -> {
                String newText = editText.getText().toString();
                tvUserName.setText(newText);
                mPresenter.onNameChanged(newText);
                dialog.dismiss();
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(dialogV -> {
                dialog.dismiss();
            });
            dialog.show();
        });

        btnEditEmail.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
            dialog.setContentView(R.layout.edit_dialog_layout);
            dialog.setTitle("Change Email");
            Button btnSave = dialog.findViewById(R.id.btnSave);
            EditText editText = dialog.findViewById(R.id.etNew);
            btnSave.setOnClickListener(dialogV -> {
                String newText = editText.getText().toString();
                tvEmail.setText(newText);
                mPresenter.onEmailChanged(newText);
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
            Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
            dialog.setContentView(R.layout.edit_dialog_layout);
            dialog.setTitle("Change Phone Number");
            Button btnSave = dialog.findViewById(R.id.btnSave);
            EditText editText = dialog.findViewById(R.id.etNew);
            btnSave.setOnClickListener(dialogV -> {
                String newText = editText.getText().toString();
                tvPhone.setText(newText);
                mPresenter.onPhoneChanged(newText);
                dialog.dismiss();
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(dialogV -> {
                dialog.dismiss();
            });
            dialog.show();
        });

        btnEditLab.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
            dialog.setContentView(R.layout.edit_dialog_layout);
            dialog.setTitle("Change Your Laboratory");
            Button btnSave = dialog.findViewById(R.id.btnSave);
            EditText editText = dialog.findViewById(R.id.etNew);
            btnSave.setOnClickListener(dialogV -> {
                String newText = editText.getText().toString();
                tvLab.setText(newText);
                mPresenter.onLabChanged(newText);
                dialog.dismiss();
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(dialogV -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    public void signOutBtn(){
        mPresenter.onExitBtnPressed();
        Intent intent = new Intent(getContext(), SplashActivity.class);
        getContext().startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void showData(String name, String email, String phone, String lab) {
        progressBar.setVisibility(View.GONE);
        rellay1.setVisibility(View.VISIBLE);
        csData.setVisibility(View.VISIBLE);
        tvUserName.setText(name);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvLab.setText(lab);
    }
}

package com.smri.smarttracker.screens.main.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.login.LoginActivity;
import com.smri.smarttracker.screens.splash.SplashActivity;
import com.smri.smarttracker.utils.FabFragmentListener;

import soup.neumorphism.ShapeType;

public class ProfileFragment extends Fragment {

    public FabFragmentListener listener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        TextView signBtn = view.findViewById(R.id.singBtn);
        signBtn.setOnClickListener(v -> exitFirebase()
        );
        return view;
    }

    public void editProfile(){
        Toast.makeText(getContext(), "PROFILE CLICKED", Toast.LENGTH_SHORT).show();
    }

    public void exitFirebase(){
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent(getContext(), SplashActivity.class);
        getContext().startActivity(intent);
        getActivity().finish();
    }


}

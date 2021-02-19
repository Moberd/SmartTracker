package com.smri.smarttracker.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smri.smarttracker.R;
import com.smri.smarttracker.utils.FabFragmentListener;

public class ProfileFragment extends Fragment {

    public FabFragmentListener listener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    public void editProfile(){
        Toast.makeText(getContext(), "PROFILE CLICKED", Toast.LENGTH_SHORT).show();
    }
}

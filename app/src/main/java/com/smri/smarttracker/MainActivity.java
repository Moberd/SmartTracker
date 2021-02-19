package com.smri.smarttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smri.smarttracker.fragments.database.DataBaseFragment;
import com.smri.smarttracker.fragments.profile.ProfileFragment;
import com.smri.smarttracker.utils.FabFragmentListener;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FabFragmentListener {


    FloatingActionButton fab;
    Fragment currFragment = new DataBaseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(currFragment);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(currFragment instanceof DataBaseFragment) {
                    ((DataBaseFragment) currFragment).addNewChemical();
                } else if(currFragment instanceof ProfileFragment) {
                    ((ProfileFragment) currFragment).editProfile();
                }
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.database_icon:
                currFragment = new DataBaseFragment();
                ((DataBaseFragment) currFragment).listener = this;
                fab.setImageResource(R.drawable.ic_baseline_add_24);
                break;
            case R.id.profile_icon:
                currFragment = new ProfileFragment();
                ((ProfileFragment) currFragment).listener = this;
                fab.setImageResource(R.drawable.ic_baseline_edit_24);
                break;
        }
        return loadFragment(currFragment);
    }

    @Override
    public FloatingActionButton getFab() {
        return fab;
    }
}

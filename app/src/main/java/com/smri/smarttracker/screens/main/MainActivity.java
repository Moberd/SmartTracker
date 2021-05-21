package com.smri.smarttracker.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.main.fragments.database.DataBaseFragment;
import com.smri.smarttracker.screens.main.fragments.profile.ProfileFragment;
import com.smri.smarttracker.utils.FabFragmentListener;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FabFragmentListener,MainContract.View {


    FloatingActionButton fab;
    BottomNavigationView navigation;
    Fragment currFragment = new DataBaseFragment();
    MainContract.Presenter mPresenter = new MainPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(currFragment);
        mPresenter.attachView(this);
        navigation = findViewById(R.id.bottom_nav);
        fab = findViewById(R.id.fab);
        setUpListeners();
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
                fab.setTag(R.drawable.ic_baseline_add_24);
                break;
            case R.id.profile_icon:
                currFragment = new ProfileFragment();
                ((ProfileFragment) currFragment).listener = this;
                fab.setImageResource(R.drawable.ic_baseline_edit_24);
                fab.setTag(R.drawable.ic_baseline_edit_24);
                break;
        }
        return loadFragment(currFragment);
    }

    @Override
    public FloatingActionButton getFab() {
        return fab;
    }

    public void changeFABIcon(){
        if((int)fab.getTag() == R.drawable.ic_baseline_edit_24){
            fab.setImageResource(R.drawable.ic_baseline_check_24);
            fab.setTag(R.drawable.ic_baseline_check_24);
        } else if((int)fab.getTag() == R.drawable.ic_baseline_check_24){
            fab.setImageResource(R.drawable.ic_baseline_edit_24);
            fab.setTag(R.drawable.ic_baseline_edit_24);
        }
    }

    void setUpListeners(){
        navigation.setOnNavigationItemSelectedListener(this);

        fab.setOnClickListener(v -> {
            if(currFragment instanceof DataBaseFragment) {
                ((DataBaseFragment) currFragment).addItem();
            } else if(currFragment instanceof ProfileFragment ) {
                ((ProfileFragment) currFragment).editFABListener();
                changeFABIcon();
            }
        });
    }
}

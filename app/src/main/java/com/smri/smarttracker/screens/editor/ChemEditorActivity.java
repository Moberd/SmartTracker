package com.smri.smarttracker.screens.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.main.MainActivity;
import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;

public class ChemEditorActivity extends AppCompatActivity implements ChemEditorContract.View {
    ImageButton backBtn;
    EditText nameET;
    EditText descET;
    Button saveBtn;
    ChemEditorPresenter mPresenter;
    String id = " ";
    SharedPreferences mSP;
    public static final String APP_PREFERENCES = "mysettings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_editor);

        mSP = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mPresenter = new ChemEditorPresenter(mSP);

        Intent intent = getIntent();
        if(intent != null) {
            id = getIntent().getStringExtra("CHEM_ID");
        }

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        nameET = findViewById(R.id.editName);
        descET = findViewById(R.id.editDescription);
        mPresenter.attachView(this);
        setUpListeners();
        writeInfo();
    }

    void setUpListeners(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String desc = descET.getText().toString();
                mPresenter.getChanges(id,name,desc);
            }
        });
    }
    void writeInfo(){
        if(!getIntent().getStringExtra("name").equals("new")) {
            nameET.setText(getIntent().getStringExtra("name"));
        }
        if(!getIntent().getStringExtra("description").equals("new")) {
            descET.setText(getIntent().getStringExtra("description"));
        }
    }
    public void closeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
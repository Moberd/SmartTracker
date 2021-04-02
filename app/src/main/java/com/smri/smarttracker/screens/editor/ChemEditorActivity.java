package com.smri.smarttracker.screens.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.main.MainActivity;
import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ChemEditorActivity extends AppCompatActivity implements ChemEditorContract.View {
    ImageButton backBtn;
    EditText nameET;
    EditText descET;
    Button saveBtn;
    Button deleteBtn;
    ChemEditorPresenter mPresenter;
    String id = "";
    SharedPreferences mSP;
    AlertDialog dialogAlert;
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
        deleteBtn = findViewById(R.id.deleteBtn);
        saveBtn = findViewById(R.id.saveBtn);
        nameET = findViewById(R.id.editName);
        descET = findViewById(R.id.editDescription);
        mPresenter.attachView(this);
        dialogAlert = createDialog();
        setUpListeners();
        hideDeleteBtn();
        writeInfo();
    }

    void hideDeleteBtn(){
        if(getIntent().getStringExtra("name").equals("new")){
            deleteBtn.setVisibility(View.GONE);
        }
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
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.show();
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

    AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setCancelable(true);
        builder.setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteChemical(id);
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void showToast(String message, ToastMode mode) {
        switch (mode) {
            case ERROR:
                Toasty.error(
                        this,
                        message,
                        Toast.LENGTH_SHORT,
                        true
                ).show();
                break;
            case SUCCESS:
                Toasty.success(
                        this,
                        message,
                        Toast.LENGTH_SHORT,
                        true
                ).show();
                break;
            case WARNING:
                Toasty.warning(
                        this,
                        message,
                        Toast.LENGTH_SHORT,
                        true
                ).show();
                break;
            case INFO:
                Toasty.info(
                        this,
                        message,
                        Toast.LENGTH_SHORT,
                        true
                ).show();
                break;
        }
    }

}
enum ToastMode { ERROR, SUCCESS, WARNING, INFO}
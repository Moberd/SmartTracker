package com.smri.smarttracker.screens.editor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.main.MainActivity;

import es.dmoral.toasty.Toasty;

import static android.content.ContentValues.TAG;

public class ChemEditorActivity extends AppCompatActivity implements ChemEditorContract.View {
    ImageButton backBtn;
    EditText nameET;
    EditText descET;
    Button saveBtn;
    Button deleteBtn;
    ChemEditorPresenter mPresenter;
    String id = "";
    SharedPreferences mSP;
    ImageView barCode;
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
        barCode = findViewById(R.id.elem_code);
        mPresenter.attachView(this);
        dialogAlert = createDialog();
        if(!id.equals("NEWRECORD")) setUpBarcode();
        setUpListeners();
        hideDeleteBtn();
        writeInfo();
    }

    private void setUpBarcode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.CODE_128,2000,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e(TAG, "setUpBarcode: ", e);
        }
    }

    void hideDeleteBtn(){
        if(getIntent().getStringExtra("name").equals("NEWRECORD")){
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
        if(!getIntent().getStringExtra("name").equals("NEWRECORD")) {
            nameET.setText(getIntent().getStringExtra("name"));
        }
        if(!getIntent().getStringExtra("description").equals("NEWRECORD")) {
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
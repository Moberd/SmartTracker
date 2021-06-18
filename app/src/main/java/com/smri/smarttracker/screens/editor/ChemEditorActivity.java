package com.smri.smarttracker.screens.editor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.type.DateTime;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.smri.smarttracker.R;
import com.smri.smarttracker.screens.main.MainActivity;
import com.smri.smarttracker.utils.Chemical;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import es.dmoral.toasty.Toasty;

import static android.content.ContentValues.TAG;

public class ChemEditorActivity extends AppCompatActivity implements ChemEditorContract.View {
    ImageButton backBtn;
    EditText nameET;
    EditText descET;
    EditText creatorET;
    EditText timeET;
    AutoCompleteTextView locationET;
    Button saveBtn;
    Button deleteBtn;
    ChemEditorPresenter mPresenter;
    String id = "";
    SharedPreferences mSP;
    ImageView barCode;
    AlertDialog dialogAlert;
    TextView chem_id;
    ArrayList<String> autoCompData;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;

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
        creatorET = findViewById(R.id.editCreator);
        timeET = findViewById(R.id.editTime);
        locationET = findViewById(R.id.editLocation);
        barCode = findViewById(R.id.elem_code);
        chem_id = findViewById(R.id.id_number);
        progressBar = findViewById(R.id.progressEditor);
        nestedScrollView = findViewById(R.id.editorNestedSV);
        mPresenter.attachView(this);
        dialogAlert = createDialog();
        if(!id.equals("NEWRECORD")) {
            setUpBarcode();
            mPresenter.getChemInfo(id);
        } else {
            hideLoading();
            setCreateTime();
            hideDeleteBtn();
        }
        mPresenter.getAutoCompData();
        setUpListeners();
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
        if(id.equals("NEWRECORD")){
            deleteBtn.setVisibility(View.GONE);
        }
    }

    void hideLoading(){
        progressBar.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
    }

    void setCreateTime(){
        GregorianCalendar calendar = new GregorianCalendar();
        timeET.setText(calendar.getTime().toString());
    }

    void setUpListeners(){
        backBtn.setOnClickListener(v -> finish());
        saveBtn.setOnClickListener(v -> {
            String name = nameET.getText().toString();
            String desc = descET.getText().toString();
            String loc = locationET.getText().toString();
            String creator = creatorET.getText().toString();
            String time = timeET.getText().toString();
            Chemical item = new Chemical(id,name,desc,loc,creator,time);
            mPresenter.getChanges(id,item);
        });
        deleteBtn.setOnClickListener(v -> dialogAlert.show());
    }

    @Override
    public void writeInfo(Chemical item){
        hideLoading();
        nameET.setText(item.getName());
        descET.setText(item.getDescription());
        locationET.setText(item.getLocation());
        creatorET.setText(item.getCreator());
        timeET.setText(item.getCreateTime());
        chem_id.setText(id);
    }

    @Override
    public void attachAutoCompData(ArrayList<String> data) {
        autoCompData = data;
        locationET.setAdapter(new ArrayAdapter<>(ChemEditorActivity.this, android.R.layout.simple_list_item_1, autoCompData));
    }

    public void closeActivity(){
        finish();
    }

    AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setCancelable(true);
        builder.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> mPresenter.deleteChemical(id));
        return builder.create();
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
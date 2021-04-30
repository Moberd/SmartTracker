package com.smri.smarttracker.screens.main.scanner;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.smri.smarttracker.R;

public class ScannerActivity extends AppCompatActivity {

    String scanContent;
    String scanFormat;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(ScannerActivity.this);
                scanIntegrator.setPrompt("Scan");
                scanIntegrator.setBeepEnabled(true);

                //enable the following line if you want QR code
                scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                scanIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                scanIntegrator.setOrientationLocked(true);
                scanIntegrator.setBarcodeImageEnabled(true);
                scanIntegrator.initiateScan();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }

            Toast.makeText(this, scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();

            textView.setText(scanContent + "    type:" + scanFormat);

        } else {
            Toast.makeText(this, "Nothing scanned", Toast.LENGTH_SHORT).show();
        }
    }
}
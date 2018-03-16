package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import java.util.UUID;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class ScanActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private static final String HASH_DISH = "d51f95cd19fecba66558f490554838ee";
    private static final String UUID_EXTRA = "uuid_extra";
    private ZXingScannerView mScannerView;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        builder = new AlertDialog.Builder(this);
        inflater = (this).getLayoutInflater();
        Log.e("QR", "on Create");
    }

    @Override
    public void onStart() {
        super.onStart();
        mScannerView.setResultHandler(this);// Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mScannerView.stopCameraPreview();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.e("QR", result.getText());
        String stringResult = result.getText();
        if(stringResult.contains(HASH_DISH)){
            View dialogView = inflater.inflate(R.layout.qrcore_alert_dialog, null);
            builder.setTitle(null);
            builder.setCancelable(false);
            builder.setIcon(R.drawable.search_icon);
            builder.setView(dialogView);

            Button goButton = dialogView.findViewById(R.id.go_button);
            Button cancelButton = dialogView.findViewById(R.id.cancel_button);
            String[] splitResult = stringResult.split("uuid = ");
            final UUID uuid = UUID.fromString(splitResult[splitResult.length - 1]);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ItemMenuActivity.class);
                    intent.putExtra(UUID_EXTRA, uuid);
                    startActivity(intent);
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.hide();
                    onResume();
                }
            });
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

package com.example.indormitory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Jeckk on 12.03.2018.
 */

public class ScanActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    @Override
    public void onPause() {
            super.onPause();
            mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

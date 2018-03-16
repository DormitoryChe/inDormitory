package com.example.indormitory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ростислав on 12.03.2018.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mPhoneNumberEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mVerifyPasswordEditText;
    private Button mSubmitButton;
    private ImageView mBackImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameEditText = findViewById(R.id.register_name_input);
        mPhoneNumberEditText = findViewById(R.id.register_phone_number_input);
        mEmailEditText = findViewById(R.id.register_email_input);
        mPasswordEditText = findViewById(R.id.register_password_input);
        mVerifyPasswordEditText = findViewById(R.id.register_password_input_verify);
        mSubmitButton = findViewById(R.id.register_submit_button);
        mBackImageView = findViewById(R.id.toolbar_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

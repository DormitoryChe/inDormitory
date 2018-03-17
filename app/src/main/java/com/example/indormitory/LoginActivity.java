package com.example.indormitory;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indormitory.validators.Validator;

/**
 * Created by vproh on 11.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView mRegisterTextView;
    private TextView mLoginInput;
    private TextView mPasswordInput;
    private Button mSubmitButton;
    private ImageView mBackImageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSubmitButton = findViewById(R.id.login_submit_button);
        mRegisterTextView = findViewById(R.id.register_now);
        mLoginInput = findViewById(R.id.login_email_input);
        mPasswordInput = findViewById(R.id.login_password_input);
        mBackImageView = findViewById(R.id.toolbar_back);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isLoginSuccessful = true;
                String loginField = mLoginInput.getText().toString();
                String passwordField = mPasswordInput.getText().toString();

                if(Validator.isFieldEmpty(passwordField)) {
                    isLoginSuccessful = false;
                    mPasswordInput.setError(getString(R.string.empty_field_error));
                } else if(!Validator.isPasswordLengthCorrect(passwordField)) {
                    isLoginSuccessful = false;
                    mPasswordInput.setError(getString(R.string.incorrect_password_error));
                }

                if(Validator.isFieldEmpty(loginField)) {
                    isLoginSuccessful = false;
                    mLoginInput.setError(getString(R.string.empty_field_error));
                } else if(!Validator.isEmailValid(loginField)) {
                    isLoginSuccessful = false;
                    mLoginInput.setError(getString(R.string.email_not_valid));
                }

                if(isLoginSuccessful)
                    startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });
    }
}

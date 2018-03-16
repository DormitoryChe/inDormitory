package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.indormitory.validators.LoginValidator;

/**
 * Created by vproh on 11.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView mRegisterTextView;
    private TextView mLoginInput;
    private TextView mPasswordInput;
    private Button mSubmitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSubmitButton = findViewById(R.id.login_submit_button);
        mRegisterTextView = findViewById(R.id.register_now);
        mLoginInput = findViewById(R.id.login_email_input);
        mPasswordInput = findViewById(R.id.login_password_input);

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int loginFieldLength = mLoginInput.getText().length();
                int passwordFieldLength = mPasswordInput.getText().length();

                if(LoginValidator.isPasswordFieldEmpty(passwordFieldLength)) {
                    mPasswordInput.setError(getString(R.string.empty_login_password_error));
                } else if(!LoginValidator.isPasswordLengthCorrect(passwordFieldLength)) {
                    mPasswordInput.setError(getString(R.string.incorrect_login_password_error));
                }

                if(LoginValidator.isEmailFieldEmpty(loginFieldLength)) {
                    mLoginInput.setError(getString(R.string.empty_login_email_error));
                } else if(!LoginValidator.isEmailValid(mLoginInput.getText().toString())) {
                    mLoginInput.setError(getString(R.string.email_not_valid));
                } else if(!LoginValidator.isEmailLenghtCorrect(loginFieldLength)) {
                    mLoginInput.setError(getString(R.string.incorrect_login_email_error));
                }
            }
        });
    }
}

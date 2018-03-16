package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.indormitory.validators.Validator;

import org.w3c.dom.Text;

/**
 * Created by Ростислав on 12.03.2018.
 */

public class RegisterActivity extends AppCompatActivity {
    private TextView mRegisterName;
    private TextView mRegisterPhone;
    private TextView mRegisterEmail;
    private TextView mRegisterPassword;
    private TextView mRegisterPasswordVerify;
    private Button mRegisterSubmitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterName = findViewById(R.id.register_name_input);
        mRegisterPhone = findViewById(R.id.register_phone_number_input);
        mRegisterEmail = findViewById(R.id.register_email_input);
        mRegisterPassword = findViewById(R.id.register_password_input);
        mRegisterPasswordVerify = findViewById(R.id.register_password_input_verify);
        mRegisterSubmitButton = findViewById(R.id.register_submit_button);

        mRegisterSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRegisterSuccessful = true;
                String nameField = mRegisterName.getText().toString();
                String phoneField = mRegisterPhone.getText().toString();
                String emailField = mRegisterEmail.getText().toString();
                String passwordField = mRegisterPassword.getText().toString();
                String passwordVerifyField = mRegisterPasswordVerify.getText().toString();

                if(Validator.isFieldEmpty(passwordVerifyField)) {
                    isRegisterSuccessful = false;
                    mRegisterPasswordVerify.setError(getString(R.string.empty_field_error));
                } else if (!Validator.isPasswordsEquals(passwordField, passwordVerifyField)) {
                    isRegisterSuccessful = false;
                    mRegisterPasswordVerify.setError(getString(R.string.passwords_do_not_match));
                }

                if(Validator.isFieldEmpty(passwordField)) {
                    isRegisterSuccessful = false;
                    mRegisterPassword.setError(getString(R.string.empty_field_error));
                } else if (!Validator.isPasswordLengthCorrect(passwordField)) {
                    isRegisterSuccessful = false;
                    mRegisterPassword.setError(getString(R.string.incorrect_password_error));
                }

                if(Validator.isFieldEmpty(emailField)) {
                    isRegisterSuccessful = false;
                    mRegisterEmail.setError(getString(R.string.empty_field_error));
                } else if (!Validator.isEmailValid(emailField)) {
                    isRegisterSuccessful = false;
                    mRegisterEmail.setError(getString(R.string.email_not_valid));
                }

                if(Validator.isFieldEmpty(phoneField)) {
                    isRegisterSuccessful = false;
                    mRegisterPhone.setError(getString(R.string.empty_field_error));
                } else if (!Validator.isRegisterPhoneCorrect(phoneField)) {
                    isRegisterSuccessful = false;
                    mRegisterPhone.setError(getString(R.string.phone_not_valid));
                }

                if(Validator.isFieldEmpty(nameField)) {
                    isRegisterSuccessful = false;
                    mRegisterName.setError(getString(R.string.empty_field_error));
                } else if (!Validator.isRegisterNameCorrect(nameField)) {
                    isRegisterSuccessful = false;
                    mRegisterName.setError(getString(R.string.name_not_valid));
                }

                if(isRegisterSuccessful)
                    startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });
    }
}

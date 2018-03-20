package com.example.indormitory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indormitory.validators.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by vproh on 11.03.2018.
 */

public class LoginActivity extends BaseActivity {
    private EditText mLoginInput;
    private EditText mPasswordInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginInput = findViewById(R.id.login_email_input);
        mPasswordInput = findViewById(R.id.login_password_input);

        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.register_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {

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

                if(isLoginSuccessful) {
                    mAuth.signInWithEmailAndPassword(loginField, passwordField)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Basket", "signInWithEmail:success");
                                        startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Busket", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}

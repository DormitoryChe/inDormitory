package com.example.indormitory;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indormitory.validators.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by vproh on 11.03.2018.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView mRegisterTextView;
    private TextView mLoginInput;
    private TextView mPasswordInput;
    private ImageView mBackImageView;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private Button mSignInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mRegisterTextView = findViewById(R.id.register_now);
        mLoginInput = findViewById(R.id.login_email_input);
        mPasswordInput = findViewById(R.id.login_password_input);
        mBackImageView = findViewById(R.id.toolbar_back);
        mSignInButton = findViewById(R.id.sign_in_button);

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

        mSignInButton.setOnClickListener(new View.OnClickListener() {

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

                                    // ...
                                }
                            });
                }
                    startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });
    }
}

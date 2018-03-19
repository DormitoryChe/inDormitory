package com.example.indormitory;

import android.content.Intent;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
    private ImageView mBackImageView;
    private FirebaseAuth mAuth;


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
        mBackImageView = findViewById(R.id.toolbar_back);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
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

                if(isRegisterSuccessful) {
                    mAuth.createUserWithEmailAndPassword(emailField, passwordField).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Log.e("Basket", "createUserWithEmail:successful", task.getException());
                                Toast.makeText(RegisterActivity.this, "Good", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                addUserToDatabase(user);
                                startActivity(new Intent(RegisterActivity.this, NewsActivity.class));
                            } else {
                                Log.e("Basket", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Don't good", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void addUserToDatabase(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("user_name", mRegisterName.getText().toString());
        newUser.put("user_email", mRegisterEmail.getText().toString());
        db.collection("users").document(user.getUid()).set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
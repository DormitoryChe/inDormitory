package com.example.indormitory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indormitory.validators.Validator;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

/**
 * Created by Ростислав on 19.03.2018.
 */

public class ProfileActivity extends BaseActivity {
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!FacebookSdk.isInitialized())
            FacebookSdk.sdkInitialize(ProfileActivity.this);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        builder = new AlertDialog.Builder(this);
        inflater = (this).getLayoutInflater();

        if(mCurrentUser == null)
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        findViewById(R.id.toolbar_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ShoppingCartActivity.class));
            }
        });
        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginManager.getInstance() != null)
                    LoginManager.getInstance().logOut();
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
        findViewById(R.id.change_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = inflater.inflate(R.layout.change_password_allert_dialog, null);
                builder.setCancelable(true);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                final EditText currentPasswordEditText = dialogView.findViewById(R.id.current_password);
                final EditText newPasswordEditText = dialogView.findViewById(R.id.new_password);
                final EditText confirmNewPasswordEditText = dialogView.findViewById(R.id.confirm_new_password);
                Button confirmNewPasswordButton = dialogView.findViewById(R.id.confirm_password_button);
                confirmNewPasswordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChangePasswordSuccessful = true;

                        if(Validator.isFieldEmpty(confirmNewPasswordEditText.getText().toString())) {
                            isChangePasswordSuccessful = false;
                            confirmNewPasswordEditText.setError(getString(R.string.empty_field_error));
                        } else if (!Validator.isPasswordsEquals(newPasswordEditText.getText().toString(), confirmNewPasswordEditText.getText().toString())) {
                            isChangePasswordSuccessful = false;
                            confirmNewPasswordEditText.setError(getString(R.string.passwords_do_not_match));
                        }

                        if(Validator.isFieldEmpty(currentPasswordEditText.getText().toString())) {
                            isChangePasswordSuccessful = false;
                            newPasswordEditText.setError(getString(R.string.empty_field_error));
                        } else if (!Validator.isPasswordLengthCorrect(currentPasswordEditText.getText().toString())) {
                            isChangePasswordSuccessful = false;
                            newPasswordEditText.setError(getString(R.string.incorrect_password_error));
                        }

                        if(isChangePasswordSuccessful){
                            final String email = mCurrentUser.getEmail();
                            AuthCredential credential = EmailAuthProvider.getCredential(email, currentPasswordEditText.getText().toString());
                            mCurrentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        mCurrentUser.updatePassword(newPasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
                                                    alertDialog.hide();
                                                } else if(!task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "You have entered the wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}

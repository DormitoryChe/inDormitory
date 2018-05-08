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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by vproh on 11.03.2018.
 */

public class LoginActivity extends BaseActivity {
    private static final int RC_SIGN_IN = 1;

    private EditText mLoginInput;
    private EditText mPasswordInput;
    private CallbackManager mCallbackManager;
    private LoginButton mFacebookLoginButton;
    private SignInButton mGoogleLoginButton;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FacebookSdk.isInitialized())
            FacebookSdk.sdkInitialize(LoginActivity.this);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Log.e("Basket", "default_web_client_id = " + getString(R.string.default_web_client_id));

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleLoginButton = findViewById(R.id.google_login_button);
        mLoginInput = findViewById(R.id.login_email_input);
        mPasswordInput = findViewById(R.id.login_password_input);
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton = findViewById(R.id.facebook_login_button);
        mFacebookLoginButton.setReadPermissions("email", "public_profile");
        /*
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).
                enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Connection with Google failed", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
*/
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
                                        Log.e("Basket", "signInWithEmail:success");
                                        startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.e("Basket", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, R.string.fail_login_toast,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        mFacebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                Log.e("Basket", "Facebook login good");
            }

            @Override
            public void onCancel() {
                Log.e("Basket", "Facebook cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Basket", "Facebook error", error);
            }
        });


        mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriatel
                e.printStackTrace();
                Log.e("Basket", "Exception", e);
        }
        } else
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.e("Basket", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("Basket", "signInWithCredential:success");
                            startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                            Toast.makeText(LoginActivity.this, "Authentication good with Facebook",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("Basket", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed with Facebook",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.e("Basket", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Basket", "signInWithCredential:success");
                            Toast.makeText(LoginActivity.this, "Login woth Google good", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Basket", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login woth Google fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

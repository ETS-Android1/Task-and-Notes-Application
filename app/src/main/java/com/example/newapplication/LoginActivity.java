package com.example.newapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sign In Activity
 */

public class LoginActivity extends AppCompatActivity {

    private ImageView loadLogoLogin;
    private EditText emailLogin;
    private EditText passwordLogin;
    private Button buttonLogin;
    private ImageView backLogin;
    private TextView signInLog;
    private ImageView googleLog;
    private ImageView facebookLog;
    private ImageView linkedInLog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    CallbackManager callbackManager;

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        loadLogoLogin = (ImageView) findViewById(R.id.imageLogoLogin);
        emailLogin = (EditText) findViewById(R.id.editEmailLogin);
        passwordLogin = (EditText) findViewById(R.id.editPasswordLogin);
        buttonLogin = (Button) findViewById(R.id.buttonLogIn);
        backLogin = (ImageView) findViewById(R.id.loginBack);
        signInLog = (TextView) findViewById(R.id.textSignClickLog);
        googleLog = (ImageView) findViewById(R.id.imageGoogleLogin);
        facebookLog = (ImageView) findViewById(R.id.imageFacebookLogin);
        linkedInLog = (ImageView) findViewById(R.id.imageLinkedinLogin);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Picasso.get().load(R.drawable.logo).resize(300,300).centerCrop().into(loadLogoLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskFuncLogin();
            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signInLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

        googleLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performGoogleLogin();
            }
        });

        facebookLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFacebookLogIn();
            }
        });

        //Github
        linkedInLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performGitHubLogin();
            }
        });

    }

    //Facebook
    private void performFacebookLogIn(){

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,2);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null,0);
                        }
                    }
                });
    }

    //Github
    private void performGitHubLogin(){
        if(emailLogin.getText().toString().equals(null)){
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
        }
        else{
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
            provider.addCustomParameter("login", emailLogin.getText().toString());

            List<String> scopes =
                    new ArrayList<String>() {
                        {
                            add("user:email");
                        }
                    };
            provider.setScopes(scopes);

            Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
            if (pendingResultTask != null) {
                // There's something already here! Finish the sign-in for your user.
                pendingResultTask
                        .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure.
                                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.w(TAG, "signInWithCredential:failure");
                                        finish();
                                    }
                                });
            } else {
                mAuth
                        .startActivityForSignInWithProvider(LoginActivity.this, provider.build())
                        .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user,3);
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure.
                                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.w(TAG, "signInWithCredential:failure");
                                        finish();
                                    }
                                });
            }
        }
    }

    //Google
    private void performGoogleLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("27600538955-okef0h0rt2fgo5etqanrtlp0e6eer3cs.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Google sign in failed", e);
                finish();
            }
        }

        // Pass the activity result back to the Facebook SDK
        if(requestCode == 64206) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,1);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            finish();
                        }
                    }
                });
    }

    //Launch from this Activity to Home Activity
    private void updateUI(FirebaseUser user,int num) {
        startActivity(new Intent(LoginActivity.this,HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("authType",num));
    }

    //Normal SignIn
    private void taskFuncLogin(){

        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class).putExtra("authType",0));
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
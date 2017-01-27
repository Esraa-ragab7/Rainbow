package com.example.esrae.rainbow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SiginUpActivity extends LoginActivity {
    private FirebaseAuth auth;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
   // private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private EditText mPasswordView2;
    private EditText Your_Name;
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(SiginUpActivity.this, LoginActivity.class));
        // your code.
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ActionBar actionBar = getActionBar();
      //  actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sigin_up);
        auth = FirebaseAuth.getInstance();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView2=(EditText) findViewById(R.id.password2);
        Your_Name=(EditText) findViewById(R.id.User_Name);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailView.getText().toString().trim();
                String password = mPasswordView.getText().toString().trim();
                String password2 = mPasswordView.getText().toString().trim();
                final String Name = Your_Name.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                    }
                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(getApplicationContext(), "Enter Your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                    }
                if(!password.equals(password2)){
                    Toast.makeText(getApplicationContext(), "The two Passwords are not equal !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                 Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                    }
///////////////////////////////////////////////////////////////////////////////////////////

                mProgressView.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SiginUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      //  Toast.makeText(SiginUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        mProgressView.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SiginUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            } else {
                            createNewUser(task.getResult().getUser(),Name);

                            startActivity(new Intent(SiginUpActivity.this, MainActivity.class));
                            finish();
                            }
                        }
                    });

                }
            });


    }
    private DatabaseReference mDatabaseReference=FirebaseDatabase.getInstance().getReference();
    private void createNewUser(FirebaseUser userFromRegistration,String g) {
        String username = g;
        String email = userFromRegistration.getEmail();
        String userId = userFromRegistration.getUid();
        user u = new user(username);

        mDatabaseReference.child("users").child(userId).setValue(u);
       // System.out.println(Name+"123");
    }
    protected void onResume() {
        super.onResume();
        mProgressView.setVisibility(View.GONE);
        }







}


/* Software Developer: Denis J Finkel
    Description: Allows User Login
    Project: Start Date 2/1/2019
~
*/
package com.jfinkelstudio.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity {
    // * Global Class Variables *
    private EditText inputEmail, inputPassword;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        inputEmail.requestFocus();
        loginToolbar();
        gradientBackground();
        signUp();

    }

    // Disables Back Press Event Key
    @Override
    public void onBackPressed() {

    }

    // Login Registration
    public void onLogin() {
        String userSavedEmail = inputEmail.getText().toString().trim();
        String userSavedPassword = inputPassword.getText().toString().trim();


        if (userSavedEmail.isEmpty()) {
            inputEmail.setError("Email Field Value is Empty!");
            inputEmail.requestFocus();
        }
        if ((userSavedPassword.isEmpty()) || (userSavedPassword.length() <= 6)) {
            inputPassword.setError("Password Field Value is Empty!\nOr Password size is too small");
            inputPassword.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userSavedEmail).matches()) {
            inputEmail.setError("Please Enter A Valid Email");
            inputEmail.requestFocus();
        } else {
            firebaseAuth.signInWithEmailAndPassword(userSavedEmail, userSavedPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> loginTask) {
                    if (loginTask.isSuccessful()) {

                        inputEmail.setText("");
                        inputPassword.setText("");
                        startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                        CustomIntent.customType(LoginActivity.this, "bottom-to-up");

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Registration", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Creates Login Toolbar
    public void loginToolbar() {
        setTitle("");
        Toolbar lgnToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(lgnToolbar);
    }

    // Inflates Toolbar Items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return (true);
    }

    // * Click Event On Toolbar Item Icon *
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Optional: However I Feel Like It is unnecessary to have/keep
        if (item.getItemId() == R.id.info) {
            String Login_Link = ("https://support.google.com/accounts/answer/41078?co=GENIE.Platform%3DDesktop&hl=en");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Login_Link)));
        }
        return super.onOptionsItemSelected(item);
    }


    // Animates activity_login Background
    public void gradientBackground() {
        ConstraintLayout mainConstrainLayout = findViewById(R.id.constranLayout);
        AnimationDrawable layoutDrawable = (AnimationDrawable) mainConstrainLayout.getBackground();
        layoutDrawable.setEnterFadeDuration(6000);
        layoutDrawable.setExitFadeDuration(6000);
        layoutDrawable.start();
    }

    // Opens SignUp ActivityClass
    @SuppressLint("ClickableViewAccessibility")
    public void signUp() {
        TextView txtSignUp = findViewById(R.id.txtHaveAccount);
        txtSignUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                CustomIntent.customType(LoginActivity.this, "fadein-to-fadeout");
                return (true);
            }
        });
    }

    // Opens Users Saved Activity
    public void openingUsersActivity(View view) {
        onLogin();
    }


}

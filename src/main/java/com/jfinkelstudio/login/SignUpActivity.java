/* Software Developer: Denis J Finkel
Description: Allows  User To Sign Up and Create an Account
~
*/
package com.jfinkelstudio.login;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import maes.tech.intentanim.CustomIntent;

public class SignUpActivity extends AppCompatActivity {
    // * Class Global Variables *
    public EditText inputEmail, inputPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        signUpToolbar();
        onAnimationDrawableBackground();
        backToLoginActivity();
        inputEmail.requestFocus();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        CustomIntent.customType(SignUpActivity.this, "fadein-to-fadeout");

    }

    public void errorDialog(final Context activity, final Class secondActivity, String errorMessage) {
        new AlertDialog.Builder(activity)
                .setMessage(errorMessage)
                .setNegativeButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog_Return, int returnLogin) {
                        startActivity(new Intent(activity, secondActivity));

                    }
                }).create().show();
    }

    // * Registering The User *
    public void onRegister() {

        String userPassword = inputPassword.getText().toString().trim();
        String userEmail = inputEmail.getText().toString().trim();

        if (userEmail.isEmpty()) {
            inputEmail.setError("Email Field Value is Empty!");
            inputEmail.requestFocus();

        }
        if (userPassword.isEmpty()) {
            inputPassword.setError("Password Field Value is Empty!");
            inputPassword.requestFocus();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        CustomIntent.customType(SignUpActivity.this, "right-to-left");
                    }
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        errorDialog(SignUpActivity.this, LoginActivity.class, "Your Account Has Already Been Verified");
                    } else {

                        Toast.makeText(SignUpActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        inputEmail.requestFocus();
                    }
                }
            });
        }
    }

    // * Inflates menu items *
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return (true);
    }

    // * Inflates Toolbar items *
    public void signUpToolbar() {
        setTitle("");
        Toolbar sgnToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(sgnToolbar);

    }

    // * Click Event On Toolbar Item Icon *
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.info) {
            // Optional: However I Feel Like It is unnecessary to have/keep
            String sign_inLink = ("https://accounts.google.com/ServiceLogin");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sign_inLink)));
        }
        return super.onOptionsItemSelected(item);
    }

    // * Animates The activity_sign_up Background *
    public void onAnimationDrawableBackground() {
        ConstraintLayout secondConstrainLayout = findViewById(R.id.constrantSecondLayout);
        AnimationDrawable animateSecondActivity = (AnimationDrawable) secondConstrainLayout.getBackground();
        animateSecondActivity.setEnterFadeDuration(5000);
        animateSecondActivity.setExitFadeDuration(5000);
        animateSecondActivity.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void backToLoginActivity() {
        TextView txtSingUp = findViewById(R.id.txtHaveAccount);
        txtSingUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                CustomIntent.customType(SignUpActivity.this, "fadein-to-fadeout");
                return (true);
            }
        });
    }

    public void signUpRegistration(View view) {
        onRegister();
    }
}

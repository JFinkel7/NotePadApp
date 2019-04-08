/* Software Developer: Denis J Finkel
    Description: Allows  User Create Their Note
 ~ Or View Their Saved Notes
*/
package com.jfinkelstudio.login;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;

import maes.tech.intentanim.CustomIntent;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        gradientBackground();
    }

    // * Prevents User From Going Back To Login-In Activity  *
    public void onBackPressed() {
    }

    // * Logs The User Out To Log-In Activity *
    public void user_LogOut(View view) {
        startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
        CustomIntent.customType(MainMenuActivity.this, "fadein-to-fadeout");
    }

    // * Opens Up Creating Note Activity *
    public void user_CreateNote(View view) {
        startActivity(new Intent(MainMenuActivity.this, CreateNoteActivity.class));
        CustomIntent.customType(MainMenuActivity.this, "up-to-bottom");
    }

    // * Opens Saved Note Activity *
    public void user_ViewNote(View view) {
        startActivity(new Intent(MainMenuActivity.this, ViewNoteActivity.class));
        CustomIntent.customType(MainMenuActivity.this, "bottom-to-up");

    }

    // * Animates activity activity_main_menu Background *
    public void gradientBackground() {
        GridLayout mainMenuGridLayout = findViewById(R.id.activity_main_menu_GridLayout);
        AnimationDrawable layoutDrawable = (AnimationDrawable) mainMenuGridLayout.getBackground();
        layoutDrawable.setEnterFadeDuration(6000);
        layoutDrawable.setExitFadeDuration(6000);
        layoutDrawable.start();
    }


}// End OF Class

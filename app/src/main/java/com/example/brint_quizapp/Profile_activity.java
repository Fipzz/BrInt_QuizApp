package com.example.brint_quizapp;

import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile_activity extends AppCompatActivity implements View.OnClickListener {

    TextView title, username;
    Icon profilePicture;
    Button backButton;
    Switch themeToggle;
    String themeSwitch,sharedPreference, currentTheme, theme;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);


        if (sharedPref.getBoolean(themeSwitch, false) == true){
            themeToggle.setChecked(true);
        }

        currentTheme = sharedPref.getString("current_theme", "blue_theme");
        if (currentTheme == "blue_theme"){
            setTheme(R.style.Theme_App_Blue);

        } else {
            setTheme(R.style.Theme_App_Purple);
        }

        setContentView(R.layout.profile_activity_layout);

        title = findViewById(R.id.name);

        username = findViewById(R.id.username);

        backButton = findViewById(R.id.profil_knap);
        backButton.setOnClickListener(this);

        themeToggle = findViewById(R.id.theme_switch);
        themeToggle.setOnClickListener(this);

        sharedPreference = getString(R.string.preferenceFile);
        themeSwitch = getString(R.string.themeSwitch);




    }

    @Override
    public void onClick(View v) {

        SharedPreferences.Editor edit = sharedPref.edit();

        sharedPref
                .edit()
                .putString("current_theme","purple_theme")
                .apply();
        recreate();

    }
}

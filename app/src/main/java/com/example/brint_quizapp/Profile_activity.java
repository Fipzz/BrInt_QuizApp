package com.example.brint_quizapp;

import android.content.Intent;
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



        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme == "blue_theme"){

            setTheme(R.style.Theme_App_Blue);

        } else if (currentTheme == "purple_theme") {

            setTheme(R.style.Theme_App_Purple);
        }

        setContentView(R.layout.profile_activity_layout);
        title = findViewById(R.id.name);

        username = findViewById(R.id.username);

        backButton = findViewById(R.id.profil_knap);


        themeToggle = findViewById(R.id.theme_switch);

        themeToggle.setOnClickListener(this);
        backButton.setOnClickListener(this);

        if (sharedPref.getBoolean(themeSwitch, false) == true){
            themeToggle.setChecked(true);
        }

    }



    @Override
    public void onClick(View v) {

        if (themeToggle.isChecked() == true) {

            SharedPreferences.Editor edit = sharedPref.edit();

            sharedPref
                    .edit()
                    .putString("current_theme", "purple_theme")
                    .apply();
            recreate();

            edit.putString(sharedPreference,"purple_theme");
            edit.putBoolean(themeSwitch,true);
            edit.apply();

        } else if (themeToggle.isChecked() == false) {

            SharedPreferences.Editor edit = sharedPref.edit();

            sharedPref
                    .edit()
                    .putString("current_theme", "blue_theme")
                    .apply();
            recreate();

            edit.putString(sharedPreference,"blue_theme");
            edit.putBoolean(themeSwitch,false);
            edit.apply();

        }

        if (backButton.getId() == v.getId()) {
            startActivity(new Intent(this, Homepage_activity.class));

        }

    }
}

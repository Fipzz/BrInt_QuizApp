package com.example.brint_quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.brint_quizapp.dal.dto.UserDTO;

public class Profile_activity extends AppCompatActivity implements View.OnClickListener {

    TextView title, username;
    Icon profilePicture;
    Switch themeToggle;
    String themeSwitch,sharedPreference, currentTheme, theme;
    SharedPreferences sharedPref;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Profile_activity.this, Homepage_activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.profile_activity_layout);

        /*
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

        theme = sharedPref.getString("current_theme", "blue_theme");
        if (currentTheme != theme){
            recreate();
        }

        setContentView(R.layout.profile_activity_layout);



        themeToggle = findViewById(R.id.theme_switch);
        themeToggle.setOnClickListener(this);

        sharedPreference = getString(R.string.preferenceFile);
        themeSwitch = getString(R.string.themeSwitch);

         */

        title = findViewById(R.id.name);

        username = findViewById(R.id.username);

        UserDTO user = UserSingleton.getUserSingleton().getUser();

        if(UserSingleton.getUserSingleton().getUser() != null) {
            username.setText(UserSingleton.getUserSingleton().getUser().getName());
        }

    }

    @Override
    public void onClick(View v) {

    }
}

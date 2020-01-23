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
    Button resultsButton;
    Switch themeToggle;
    String themeSwitch,sharedPreference, currentTheme;
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


        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme.equals("blue_theme")){

            setTheme(R.style.Theme_App_Blue);


        } else if (currentTheme.equals("purple_theme")) {

            setTheme(R.style.Theme_App_Purple);

        }

        setContentView(R.layout.profile_activity_layout);
        title = findViewById(R.id.name);

        username = findViewById(R.id.username);


        themeToggle = findViewById(R.id.theme_switch);

        themeToggle.setOnClickListener(this);

        if (sharedPref.getBoolean(themeSwitch, false) == true){
            themeToggle.setChecked(true);
        }

        resultsButton = findViewById(R.id.results_button);
        resultsButton.setOnClickListener(this);

        UserDTO user = UserSingleton.getUserSingleton().getUser();

        if(UserSingleton.getUserSingleton().getUser() != null) {
            username.setText(UserSingleton.getUserSingleton().getUser().getName());
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == findViewById(R.id.theme_switch).getId() ) {
            if (themeToggle.isChecked() == true) {

                SharedPreferences.Editor edit = sharedPref.edit();

                sharedPref
                        .edit()
                        .putString("current_theme", "purple_theme")
                        .apply();
                recreate();

                edit.putString(sharedPreference, "purple_theme");
                edit.putBoolean(themeSwitch, true);
                edit.apply();

            } else if (themeToggle.isChecked() == false) {

                SharedPreferences.Editor edit = sharedPref.edit();

                sharedPref
                        .edit()
                        .putString("current_theme", "blue_theme")
                        .apply();
                recreate();

                edit.putString(sharedPreference, "blue_theme");
                edit.putBoolean(themeSwitch, false);
                edit.apply();

            }

        }

       

    }
}

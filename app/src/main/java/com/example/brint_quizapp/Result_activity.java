package com.example.brint_quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Result_activity extends AppCompatActivity implements View.OnClickListener {

    TextView right, wrong;
    Button back;

    SharedPreferences sharedPref;

    String currentTheme, sharedPreference;

    int rightAnswers, wrongAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme == "blue_theme"){

            setTheme(R.style.Theme_App_Blue);

        } else if (currentTheme == "purple_theme") {

            setTheme(R.style.Theme_App_Purple);
        }

        setContentView(R.layout.result_activity_layout);

        right = findViewById(R.id.numberCorrectVar);
        wrong = findViewById(R.id.numberWrongVar);

        back = findViewById(R.id.goBack);
        back.setOnClickListener(this);

        right.setText(Integer.toString(getIntent().getExtras().getInt("right")));
        wrong.setText(Integer.toString(getIntent().getExtras().getInt("wrong")));
    }

    @Override
    public void onClick(View v) {

        Intent home = new Intent(this, Homepage_activity.class);
        startActivity(home);

    }

}


package com.example.brint_quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Result_activity extends AppCompatActivity implements View.OnClickListener {

    TextView right, wrong, quizname;
    Button back;
    SharedPreferences sharedPref;

    String currentTheme, sharedPreference;

    int rightAnswers, wrongAnswers;

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End quiz");
        builder.setMessage("You are about to end the quiz.\nYour result will not be saved.\nAre you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Result_activity.this, Homepage_activity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme.equals("blue_theme")){

            setTheme(R.style.Theme_App_Blue);


        } else if (currentTheme.equals("purple_theme")) {

            setTheme(R.style.Theme_App_Purple);

        }

        setContentView(R.layout.result_activity_layout);

        right = findViewById(R.id.numberCorrectVar);
        wrong = findViewById(R.id.numberWrongVar);

        back = findViewById(R.id.goBack);
        back.setOnClickListener(this);

        right.setText(Integer.toString(getIntent().getExtras().getInt("right")));
        wrong.setText(Integer.toString(getIntent().getExtras().getInt("wrong")));

        quizname = findViewById(R.id.quiz_navn);
        quizname.setText(getIntent().getExtras().getString("quizname"));

    }

    @Override
    public void onClick(View v) {

        Intent home = new Intent(this, Homepage_activity.class);
        startActivity(home);

    }

}


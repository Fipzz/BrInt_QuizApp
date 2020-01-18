package com.example.brint_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz_Menu extends AppCompatActivity implements View.OnClickListener {

    TextView quizName, quizCode, codeNumber;

    Button editQuiz, statistics;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.quiz_menu_activity_layout);

        quizName = (TextView) findViewById(R.id.quizNavn);

        quizCode = (TextView) findViewById(R.id.quizCode);

        codeNumber = (TextView) findViewById(R.id.codeNumber);

        editQuiz = (Button) findViewById(R.id.editQuiz); editQuiz.setOnClickListener(this);

        statistics = (Button) findViewById(R.id.statistics); statistics.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (R.id.editQuiz == v.getId()) {
            startActivity(new Intent(Quiz_Menu.this, Edit_Quiz.class));
        } else if (R.id.statistics == v.getId()) {
            startActivity(new Intent(Quiz_Menu.this, Statistics.class));
        }

    }
}

package com.example.brint_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login_screen extends AppCompatActivity implements View.OnClickListener {

    Button quiz, stats;
    EditText quiz_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        quiz = (Button) findViewById(R.id.chooseQuiz);
        quiz.setOnClickListener(this);

        stats = (Button) findViewById(R.id.statistics);
        stats.setOnClickListener(this);

        quiz_code = (EditText) findViewById(R.id.quizCode);

    }

    @Override
    public void onClick(View v) {

        if(R.id.chooseQuiz == v.getId()){

            startActivity(new Intent(Login_screen.this, Question.class));

        }

    }

}

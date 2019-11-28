package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Question extends AppCompatActivity implements View.OnClickListener {

    Button a,b,c,d;
    TextView questionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.quizz_layout);


        a = (Button) findViewById(R.id.answer1);
        a.setOnClickListener(this);

        b = (Button) findViewById(R.id.answer2);
        b.setOnClickListener(this);

        c = (Button) findViewById(R.id.answer3);
        c.setOnClickListener(this);

        d = (Button) findViewById(R.id.answer4);
        d.setOnClickListener(this);

        questionView = (TextView) findViewById(R.id.questionView);

        String[][] question = new String[2][7];
        question[0][0] = "0";
        question[0][1] = "Hvilken farve har faxe kondi flasken?";
        question[0][2] = "Grøn";
        question[0][3] = "Blå";
        question[0][4] = "Rød";
        question[0][5] = "Orange";
        question[1][0] = "1";
        question[1][1] = "Hvilken dag er den sidste dag på ugen?";
        question[1][2] = "Søndag";
        question[1][3] = "Tirsdag";
        question[1][4] = "Torsdag";
        question[1][5] = "Mandag";

        showNewQuestion(0, question, questionView, a, b, c, d);

    }

    @Override
    public void onClick(View v) {

        String[][] question = new String[2][7];
        question[0][0] = "0";
        question[0][1] = "Hvilken farve har faxe kondi flasken?";
        question[0][2] = "Grøn";
        question[0][3] = "Blå";
        question[0][4] = "Rød";
        question[0][5] = "Orange";
        question[1][0] = "1";
        question[1][1] = "Hvilken dag er den sidste dag på ugen?";
        question[1][2] = "Søndag";
        question[1][3] = "Tirsdag";
        question[1][4] = "Torsdag";
        question[1][5] = "Mandag";
        showNewQuestion(1, question, questionView, a, b, c, d);
    }

    public void showNewQuestion(int question_id, String[][] questions, TextView questionText, Button answer1Button, Button answer2Button, Button answer3Button, Button answer4Button){

        questionText.setText(questions[question_id][1]);
        answer1Button.setText(questions[question_id][2]);
        answer2Button.setText(questions[question_id][3]);
        answer3Button.setText(questions[question_id][4]);
        answer4Button.setText(questions[question_id][5]);

    }
}


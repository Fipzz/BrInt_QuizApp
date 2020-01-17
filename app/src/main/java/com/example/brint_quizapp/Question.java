package com.example.brint_quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Question extends AppCompatActivity implements View.OnClickListener {

    Button a,b,c,d;
    TextView questionView;

    Intent resultIntent;

    ArrayList<Question_item> questions = new ArrayList<Question_item>();

    int correctAnswers = 0, wrongAnswers = 0, currentQuestion = 0, isCorrect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.quiz_activity_layout);


        a = (Button) findViewById(R.id.answer1);
        a.setOnClickListener(this);


        b = (Button) findViewById(R.id.answer2);
        b.setOnClickListener(this);

        c = (Button) findViewById(R.id.answer3);
        c.setOnClickListener(this);

        d = (Button) findViewById(R.id.answer4);
        d.setOnClickListener(this);

        questionView = (TextView) findViewById(R.id.question);

        initializeQuiz();

        resultIntent = new Intent(this, Result_activity.class);

        showNextQuestion(questions.get(currentQuestion),currentQuestion);

    }

    @Override
    public void onClick(View v) {

        if (isCorrect == 1) {

            right(a);
            wrong(b);
            wrong(c);
            wrong(d);

        } else if (isCorrect == 2) {

            wrong(a);
            right(b);
            wrong(c);
            wrong(d);

        } else if (isCorrect == 3) {

            wrong(a);
            wrong(b);
            right(c);
            wrong(d);

        } else {

            wrong(a);
            wrong(b);
            wrong(c);
            right(d);

        }

        if(R.id.answer1 == v.getId()){

            if(isCorrect == 1){
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

        } else if (v.getId() == b.getId()){

            if(isCorrect == 2){
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

        } else if (v.getId() == c.getId()){

            if(isCorrect == 3){
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

        } else if (v.getId() == d.getId()){


            if(isCorrect == 4){
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

        }



        CountDownTimer showAnswers = new CountDownTimer(3000, 3000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                if(questions.size()-1 == currentQuestion) {

                    Bundle data = new Bundle();
                    data.putInt("wrong", wrongAnswers);
                    data.putInt("right", correctAnswers);
                    resultIntent.putExtras(data);

                    startActivity(resultIntent);

                } else {

                    resetButton(a);
                    resetButton(b);
                    resetButton(c);
                    resetButton(d);

                    currentQuestion++;
                    showNextQuestion(questions.get(currentQuestion),currentQuestion);
                }


            }

        }.start();

    }

    public void showNextQuestion(Question_item question, int currentQuestion){

        questionView.setText(question.getQuestion());

        a.setText(question.getAnswer1());
        b.setText(question.getAnswer2());
        c.setText(question.getAnswer3());
        d.setText(question.getAnswer4());
        isCorrect = question.getIsCorrect();

    }

    private void initializeQuiz() {

        //TODO init the whole quizz in the beginning

        questions.add(new Question_item("Hvad er 1+1?", "1", "2", "3", "4", 2));
        questions.add(new Question_item("Hvor mange stop er der på C-linjen?", "31", "5", "67000", "40", 1));
        questions.add(new Question_item("Hvad kaldes en der er født mellem 1946-1964", "Millenial", "Gen Z", "Roomba", "Boomer", 4));

    }

    private void wrong(Button a){

        a.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.redwrong));
        a.setBackground(getDrawable(R.drawable.log_in));
        a.setEnabled(false);

    }

    private void right(Button a){

        a.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.correctgreen));
        a.setBackground(getDrawable(R.drawable.log_in));
        a.setEnabled(false);
    }

    private void resetButton(Button a){

        a.setEnabled(true);
        a.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.buttonblue));
        a.setBackground(getDrawable(R.drawable.log_in));

    }
}


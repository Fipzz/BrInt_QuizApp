package com.example.brint_quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

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

import java.util.ArrayList;

public class Question extends AppCompatActivity implements View.OnClickListener {

    Button a,b,c,d;
    TextView questionView;

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

        showNextQuestion(questions.get(currentQuestion),currentQuestion);

    }

    @Override
    public void onClick(View v) {

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

        if(questions.size()-1 == currentQuestion){

            //TODO add intent to go to result activity

            //Intent mIntent = new Intent(this, .class);

        } else {

            if (isCorrect == 1) {
                Drawable buttonDrawable = a.getBackground();
                buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                //the color is a direct color int and not a color resource
                DrawableCompat.setTint(buttonDrawable, Color.GREEN);
                a.setBackground(buttonDrawable);
                //a.setBackgroundTintList(getBaseContext().getColorStateList(R.color.correctgreen));
                //a.setBackgroundColor(getColor(R.color.correctgreen));
                //b.setBackgroundColor(getColor(R.color.redwrong));
                //c.setBackgroundColor(getColor(R.color.redwrong));
                //d.setBackgroundColor(getColor(R.color.redwrong));
            } else if (isCorrect == 2) {
                a.setBackgroundColor(getColor(R.color.redwrong));
                b.setBackgroundColor(getColor(R.color.correctgreen));
                c.setBackgroundColor(getColor(R.color.redwrong));
                d.setBackgroundColor(getColor(R.color.redwrong));
            } else if (isCorrect == 3) {
                a.setBackgroundColor(getColor(R.color.redwrong));
                b.setBackgroundColor(getColor(R.color.redwrong));
                c.setBackgroundColor(getColor(R.color.correctgreen));
                d.setBackgroundColor(getColor(R.color.redwrong));
            } else {
                a.setBackgroundColor(getColor(R.color.redwrong));
                b.setBackgroundColor(getColor(R.color.redwrong));
                c.setBackgroundColor(getColor(R.color.redwrong));
                d.setBackgroundColor(getColor(R.color.correctgreen));
            }


            CountDownTimer showAnswers = new CountDownTimer(3000, 5000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    currentQuestion++;
                    showNextQuestion(questions.get(currentQuestion),currentQuestion);
                }
            }.start();


        }

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
}


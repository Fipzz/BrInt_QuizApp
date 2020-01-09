package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    ArrayList<Question_layout> questions = new ArrayList<Question_layout>();

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

            } else{

            }

        } else if (v.getId()== b.getId()){

            if(isCorrect == 2){

            } else{

            }

        } else if (v.getId()== c.getId()){

            if(isCorrect == 3){

            } else{

            }

        } else if (v.getId()== d.getId()){


            if(isCorrect == 4){

            } else{

            }

        }

        if(questions.size()-1 == currentQuestion){

            //TODO add intent to go to result activity


        } else {

            currentQuestion++;
            showNextQuestion(questions.get(currentQuestion),currentQuestion);

        }

    }

    public void showNextQuestion(Question_layout question, int currentQuestion){

        questionView.setText(question.getQuestion());

        a.setText(question.getAnswer1());
        b.setText(question.getAnswer2());
        c.setText(question.getAnswer3());
        d.setText(question.getAnswer4());
        isCorrect = question.getIsCorrect();

    }

    private void initializeQuiz() {

        //TODO init the whole quizz in the beginning

        questions.add(new Question_layout("Hvad er 1+1?", "1", "2", "3", "4", 2));
        questions.add(new Question_layout("Hvor mange stop er der på C-linjen?", "31", "5", "67000", "40", 1));
        questions.add(new Question_layout("Hvad kaldes en der er født mellem 1946-1964", "Millenial", "Gen Z", "Roomba", "Boomer", 4));

    }
}


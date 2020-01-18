package com.example.brint_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Statistics extends AppCompatActivity implements View.OnClickListener {

    TextView quizName, amountQuestions, amountQuestionsStat, amountCompletion, amountCompletionStat,
            avgCompletionTime, avgCompletionTimeStat, avgCorrect, avgCorrectStat, avgCorrectPercent, avgCorrectPercentStat;

    Button graphs, questionStats, backButton;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.statistics_activity_layout);

        quizName = (TextView) findViewById(R.id.quizNavn);


        //When one of the textviews has the Stat suffix, it's to the right of the other one and is meant to track whichever the correspending statistic is
        amountQuestions = (TextView) findViewById(R.id.amountQuestions);
        amountCompletionStat = (TextView) findViewById(R.id.amountCompletionStat);

        amountCompletion = (TextView) findViewById(R.id.amountCompletion);
        amountCompletionStat = (TextView) findViewById(R.id.amountCompletionStat);

        avgCompletionTime = (TextView) findViewById(R.id.avgCompletionTime);
        avgCompletionTimeStat = (TextView) findViewById(R.id.avgCompletionTimeStat);

        avgCompletionTime = (TextView) findViewById(R.id.avgCompletionTime);
        avgCompletionTimeStat = (TextView) findViewById(R.id.avgCompletionTimeStat);

        avgCorrect = (TextView) findViewById(R.id.avgCorrect);
        avgCorrectStat = (TextView) findViewById(R.id.avgCorrectStat);

        avgCorrectPercent = (TextView) findViewById(R.id.avgCorrectPercent);
        avgCorrectPercentStat = (TextView) findViewById(R.id.avgCorrectPercentStat);


        graphs = (Button) findViewById(R.id.graphs);

        questionStats = (Button) findViewById(R.id.questionStats);

        backButton = (Button) findViewById(R.id.backButton);

    }

    @Override
    public void onClick(View v) {
        //TODO Update intents and create their corresponding classes
        if (R.id.graphs == v.getId()) {
            //startActivity(new Intent(Statistics.this, Graph_Statistics.class));
        } else if (R.id.questionStats == v.getId()) {
            //startActivity(new Intent(Statistics.this, QuestionStats_Statistics.class));
        } else if (R.id.backButton == v.getId()) {
            startActivity(new Intent(Statistics.this, Edit_Quiz.class));
        }
    }
}

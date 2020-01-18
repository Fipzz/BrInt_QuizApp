package com.example.brint_quizapp;

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
    }

    @Override
    public void onClick(View v) {

    }
}

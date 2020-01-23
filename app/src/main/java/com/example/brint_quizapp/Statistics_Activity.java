package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brint_quizapp.dal.dao.ResultDAO;
import com.example.brint_quizapp.dal.dto.ResultDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Statistics_Activity extends AppCompatActivity {

    double numberThatTookQuiz = 0;
    double numberOfCorrect = 0;
    double numberOfQuestion = 0;
    double averageCorrectAnswers;
    String[][] averageCorrectPrQuestionArray;
    ArrayList<ResultDTO> results;
    TextView averageCorrectTextView, usersTakenTextView;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_);

        averageCorrectTextView = (TextView) findViewById(R.id.averageCorrect);

        usersTakenTextView = (TextView) findViewById(R.id.usersTaken);

        startLoading();

        GetResultsClass getStatisticsClass = new GetResultsClass();

        getStatisticsClass.setQuiz_id(getIntent().getExtras().getInt("quizId"));

        getStatisticsClass.execute();

        averageCorrectPrQuestionArray = new String[2][];

        timer = new CountDownTimer(20000,500) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(results != null) {
                    timer.cancel();
                    timer.onFinish();
                }
            }

            @Override
            public void onFinish() {
                if(results != null) {
                    if (results.size() > 0) {
                        int randomQuestionId = results.get(0).getQuestion_id();

                        for (ResultDTO result : results) {
                            if (result.getAnswer().getCorrect()) {
                                numberOfCorrect += 1;
                            }
                            if (result.getQuestion_id() == randomQuestionId) {
                                numberThatTookQuiz += 1;
                            }
                        }
                        numberOfQuestion = results.size() / numberThatTookQuiz;
                        averageCorrectAnswers = numberOfCorrect / numberOfQuestion / numberThatTookQuiz * 100;

                        DecimalFormat df = new DecimalFormat("#.00");

                        averageCorrectTextView.setText(df.format(averageCorrectAnswers));

                        int intNumberThatTookQuiz = (int) numberThatTookQuiz;

                        usersTakenTextView.setText(Integer.toString(intNumberThatTookQuiz));

                        stopLoading();
                    }
                }else{
                    averageCorrectTextView.setText("0");

                    usersTakenTextView.setText("0");
                }
            }
        }.start();

    }

    private void startLoading(){
        averageCorrectTextView.setVisibility(View.INVISIBLE);
        usersTakenTextView.setVisibility(View.INVISIBLE);
    }

    private void stopLoading(){
        averageCorrectTextView.setVisibility(View.VISIBLE);
        usersTakenTextView.setVisibility(View.VISIBLE);
    }

    private class GetResultsClass extends AsyncTask<String, Void, Void>{

        int quiz_id;
        Connection connection;
        DBconnector connectionClass = new DBconnector();

        public ArrayList<ResultDTO> getResults() {
            return results;
        }

        public void setQuiz_id(int quiz_id) {
            this.quiz_id = quiz_id;
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {
                connection = connectionClass.CONN();
                connection.setAutoCommit(false);

                ResultDAO resultDAO = new ResultDAO();
                results = resultDAO.getResultsFromQuizId(quiz_id, connection);

            }catch (SQLException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            boolean done = true;
        }
    }


}

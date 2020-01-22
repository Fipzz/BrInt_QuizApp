package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.brint_quizapp.dal.dao.ResultDAO;
import com.example.brint_quizapp.dal.dto.ResultDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Statistics_Activity extends AppCompatActivity {

    int numberThatTookQuiz = 0;
    int numberOfCorrect = 0;
    int numberOfQuestion = 0;
    int averageCorrectAnswers;
    int[][] averageCorrectPrQuestionArray;
    ArrayList<ResultDTO> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_);

        GetResultsClass getStatisticsClass = new GetResultsClass();

        getStatisticsClass.setQuiz_id(getIntent().getExtras().getInt("quizId"));

        getStatisticsClass.execute();

        this.results = getStatisticsClass.getResults();

        if(results.size() < 0) {
            int randomQuestionId = results.get(1).getQuestion_id();

            for (ResultDTO result : results) {
                if(result.getAnswer().getCorrect()){
                    numberOfCorrect++;
                }
                if (result.getQuestion_id() == randomQuestionId) {
                    numberThatTookQuiz++;
                }
            }
            numberOfQuestion = results.size()/numberThatTookQuiz;
            averageCorrectAnswers = numberOfCorrect/numberOfQuestion;
        }



    }

    private class GetResultsClass extends AsyncTask<String, Void, Void>{

        int quiz_id;

        ArrayList<ResultDTO> results;
        Connection connection;
        DBconnector connectionClass = new DBconnector();
        boolean succes;
        boolean done = false;

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
                succes = false;
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

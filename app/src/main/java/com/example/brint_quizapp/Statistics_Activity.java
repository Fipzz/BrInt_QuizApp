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

    int numberThatTookQuiz;
    int averageCorrectAnswers;
    int[][] averageCorrectPrQuestionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_);
    }

    private class GetStatisticsClass extends AsyncTask<String, Void, Void>{

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

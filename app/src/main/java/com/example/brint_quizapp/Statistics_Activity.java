package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brint_quizapp.dal.dao.ResultDAO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;
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
    TextView averageCorrectTextView, usersTakenTextView, texttext, texttext1, texttext2;
    CountDownTimer timer;
    int quiz_id;
    ArrayList<QuizDTO> userQuizzes;
    QuizDTO currentQuiz;
    ArrayList<String> listViewData;
    ListView statListView;
    SharedPreferences sharedPref;
    String currentTheme, sharedPreference;
    ColorStateList oldcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme.equals("blue_theme")){

            setTheme(R.style.Theme_App_Blue);


        } else if (currentTheme.equals("purple_theme")) {

            setTheme(R.style.Theme_App_Purple);

        }


        setContentView(R.layout.activity_statistics_);

        averageCorrectTextView = (TextView) findViewById(R.id.averageCorrect);

        usersTakenTextView = (TextView) findViewById(R.id.usersTaken);

        texttext = (TextView) findViewById(R.id.texttext);

        texttext1 = (TextView) findViewById(R.id.texttext1);

        texttext2 = (TextView) findViewById(R.id.texttext2);

        startLoading();

        listViewData = new ArrayList<>();

        GetResultsClass getStatisticsClass = new GetResultsClass();

        quiz_id = getIntent().getExtras().getInt("quizId");

        getStatisticsClass.setQuiz_id(quiz_id);

        userQuizzes = UserSingleton.getUserSingleton().getUser().getQuizzes();

        for (QuizDTO quiz : userQuizzes) {
            if(quiz.getId() == quiz_id){
                currentQuiz = quiz;
            }
        }

        getStatisticsClass.execute();

        averageCorrectPrQuestionArray = new String[2][];

        timer = new CountDownTimer(60000,500) {
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

                        averageCorrectTextView.setText(df.format(averageCorrectAnswers) + "%");

                        int intNumberThatTookQuiz = (int) numberThatTookQuiz;

                        usersTakenTextView.setText(Integer.toString(intNumberThatTookQuiz));

                        ArrayList<QuestionDTO> questions = currentQuiz.getQuestions();

                        for (QuestionDTO question : questions) {
                            String dataString = question.getText();
                            double numberCorrectOnCurrentQuestion = 0;
                            double percentCorrectOnCurrentQuestion;
                            for (ResultDTO result : results) {
                                if(result.getQuestion_id() == question.getId() && result.getAnswer().getCorrect()){
                                    numberCorrectOnCurrentQuestion += 1;
                                }
                            }
                            percentCorrectOnCurrentQuestion = numberCorrectOnCurrentQuestion / numberThatTookQuiz * 100;
                            dataString = dataString + ":\n" + df.format(percentCorrectOnCurrentQuestion) + "%";
                            listViewData.add(dataString);
                        }

                        statListView = findViewById(R.id.statListView);

                        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                                getBaseContext(),
                                android.R.layout.simple_list_item_1,
                                listViewData
                        ){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent){

                                View view = super.getView(position,convertView,parent);

                                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                ColorStateList oldcolor  = tv.getTextColors();
                                tv.setTextColor(oldcolor);
                                tv.setGravity(Gravity.CENTER);
                                tv.setPadding(0, 25, 0,25 );
                                tv.setTextSize(20);

                                return view;
                            }

                        };

                        statListView.setAdapter(listAdapter);

                        stopLoading();
                    }
                }else{
                    Intent quiz = new Intent(Statistics_Activity.this, Statistics_Activity.class);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Could not load data",
                            Toast.LENGTH_LONG);

                    toast.show();
                }
            }
        }.start();

    }

    private void startLoading(){
        averageCorrectTextView.setVisibility(View.INVISIBLE);
        usersTakenTextView.setVisibility(View.INVISIBLE);
        texttext.setVisibility(View.INVISIBLE);
        texttext1.setVisibility(View.INVISIBLE);
        texttext2.setVisibility(View.INVISIBLE);
    }

    private void stopLoading(){
        averageCorrectTextView.setVisibility(View.VISIBLE);
        usersTakenTextView.setVisibility(View.VISIBLE);
        texttext.setVisibility(View.VISIBLE);
        texttext1.setVisibility(View.VISIBLE);
        texttext2.setVisibility(View.VISIBLE);
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
            timer.cancel();
            timer.onFinish();
        }
    }


}

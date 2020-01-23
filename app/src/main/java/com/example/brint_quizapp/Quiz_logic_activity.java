package com.example.brint_quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.brint_quizapp.dal.dao.QuizDAO;
import com.example.brint_quizapp.dal.dao.ResultDAO;
import com.example.brint_quizapp.dal.dto.AnswerDTO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;
import com.example.brint_quizapp.dal.dto.ResultDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class Quiz_logic_activity extends AppCompatActivity implements View.OnClickListener {

    Button a, b, c, d;
    TextView questionView, loading, quizName;

    Intent resultIntent;

    ArrayList<AnswerDTO> answers;
    ArrayList<QuestionDTO> quizQuestions;
    ArrayList<ResultDTO> results;

    DBconnector dBconnector;
    Connection connection;

    boolean done = false;

    ResultDTO resultDTO;
    AnswerDTO answerDTO;

    int correctAnswers = 0, wrongAnswers = 0, currentQuestion = 0, isCorrect, resetColor;

    String quizId;

    CountDownTimer wait;

    QuizDTO quizDTO;
    int load = 0;

    SharedPreferences sharedPref;

    String currentTheme, sharedPreference;

    ColorStateList oldcolor;

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End quiz");
        builder.setMessage("You are about to end the quiz.\nYour result will not be saved.\nAre you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Quiz_logic_activity.this, Homepage_activity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme.equals("blue_theme")){

            setTheme(R.style.Theme_App_Blue);


        } else if (currentTheme.equals("purple_theme")) {

            setTheme(R.style.Theme_App_Purple);

        }

        setContentView(R.layout.quiz_activity_layout);


        a = (Button) findViewById(R.id.answer1);
        a.setOnClickListener(this);


        b = (Button) findViewById(R.id.answer2);
        b.setOnClickListener(this);

        c = (Button) findViewById(R.id.answer3);
        c.setOnClickListener(this);

        d = (Button) findViewById(R.id.answer4);
        d.setOnClickListener(this);

        results = new ArrayList<>();
        answerDTO = new AnswerDTO();

        questionView = (TextView) findViewById(R.id.question);
        loading = (TextView) findViewById(R.id.textView2);
        loading.setOnClickListener(this);
        quizName = (TextView) findViewById(R.id.quiz_navn);


        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        questionView.setVisibility(View.INVISIBLE);
        quizName.setVisibility(View.INVISIBLE);

        quizId = getIntent().getExtras().getString("quizcode");

        quizDTO = null;

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getApplicationContext().getTheme();
        theme.resolveAttribute(R.attr.buttonNormalColor, typedValue, true);
        resetColor = typedValue.data;

        oldcolor = a.getBackgroundTintList();


        getData getdat = new getData();

        getdat.execute();

        wait = new CountDownTimer(60000, 500) {
            @Override
            public void onTick(long l) {

                if(load == 0){
                    loading.setText("Loading\n");
                    load++;
                } else if (load == 1){
                    loading.setText("Loading\n.");
                    load++;
                } else if (load == 2){
                    loading.setText("Loading\n..");
                    load++;
                } else if (load == 3) {
                    loading.setText("Loading\n...");
                    load = 0;
                }

            }

            @Override
            public void onFinish() {

                if(quizDTO == null){

                    loading.setText("No quiz found\n click here to go back");
                    loading.setTextSize(25);

                } else {

                    initializeQuiz();

                    loading.setVisibility(View.INVISIBLE);
                    a.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                    d.setVisibility(View.VISIBLE);
                    questionView.setVisibility(View.VISIBLE);

                    showNextQuestion(currentQuestion);

                }
            }
        }.start();

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.textView2){

            resultIntent = new Intent(this, Homepage_activity.class);

            startActivity(resultIntent);

        } else {

            resultIntent = new Intent(this, Result_activity.class);

            if(answers.get(0).getCorrect()){
                right(a);
            } else{
                wrong(a);
            }

            if(answers.get(1).getCorrect()){
                right(b);
            } else{
                wrong(b);
            }

            if(answers.get(2).getCorrect()){
                right(c);
            } else{
                wrong(c);
            }

            if(answers.get(3).getCorrect()){
                right(d);
            } else{
                wrong(d);
            }

            if (v.getId() == a.getId()) {

                resultDTO = new ResultDTO();
                answerDTO = new AnswerDTO();
                answerDTO = answers.get(0);
                resultDTO.setQuiz_id(quizDTO.getId());
                resultDTO.setUser_id(UserSingleton.getUserSingleton().getUser().getId());
                resultDTO.setQuestion_id(answers.get(0).getQuestion_id());
                resultDTO.setAnswer(answerDTO);
                results.add(resultDTO);

                if (answers.get(0).getCorrect()) {

                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }

            } else if (v.getId() == b.getId()) {
                resultDTO = new ResultDTO();
                answerDTO = new AnswerDTO();
                answerDTO = answers.get(1);
                resultDTO.setQuiz_id(quizDTO.getId());
                resultDTO.setUser_id(UserSingleton.getUserSingleton().getUser().getId());
                resultDTO.setQuestion_id(answers.get(1).getQuestion_id());
                resultDTO.setAnswer(answerDTO);
                results.add(resultDTO);

                if (answers.get(1).getCorrect()) {

                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }

            } else if (v.getId() == c.getId()) {
                resultDTO = new ResultDTO();
                answerDTO = new AnswerDTO();
                answerDTO = answers.get(2);
                resultDTO.setQuiz_id(quizDTO.getId());
                resultDTO.setUser_id(UserSingleton.getUserSingleton().getUser().getId());
                resultDTO.setQuestion_id(answers.get(2).getQuestion_id());
                resultDTO.setAnswer(answerDTO);
                results.add(resultDTO);

                if (answers.get(2).getCorrect()) {
                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }

            } else if (v.getId() == d.getId()) {

                resultDTO = new ResultDTO();
                answerDTO = new AnswerDTO();
                answerDTO = answers.get(3);
                resultDTO.setQuiz_id(quizDTO.getId());
                resultDTO.setUser_id(UserSingleton.getUserSingleton().getUser().getId());
                resultDTO.setQuestion_id(answers.get(3).getQuestion_id());
                resultDTO.setAnswer(answerDTO);
                results.add(resultDTO);

                if (answers.get(3).getCorrect()) {
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

                    if (quizQuestions.size() - 1 == currentQuestion) {

                        Bundle data = new Bundle();
                        data.putInt("wrong", wrongAnswers);
                        data.putInt("right", correctAnswers);

                        dBconnector = new DBconnector();
                        connection = dBconnector.CONN();

                        ResultDAO resultDAO = new ResultDAO();
                        resultDAO.createResults(results, connection);

                        resultIntent.putExtras(data);
                        startActivity(resultIntent);

                    } else {

                        resetButton(a);
                        resetButton(b);
                        resetButton(c);
                        resetButton(d);



                        currentQuestion++;
                        showNextQuestion(currentQuestion);
                    }

                }

            }.start();

        }

    }

    public void showNextQuestion(int currentQuestion) {

        questionView.setText(quizQuestions.get(currentQuestion).getText().toString());

        answers = quizQuestions.get(currentQuestion).getAnswers();

        a.setText(answers.get(0).getText());
        b.setText(answers.get(1).getText());
        c.setText(answers.get(2).getText());
        d.setText(answers.get(3).getText());

        currentQuestion ++;

    }

    private void initializeQuiz() {

            quizQuestions = quizDTO.getQuestions();

    }

    private void wrong(Button a) {

        a.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.redwrong));
        a.setBackground(getDrawable(R.drawable.log_in));
        a.setEnabled(false);

    }

    private void right(Button a) {

        a.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.correctgreen));
        a.setBackground(getDrawable(R.drawable.log_in));
        a.setEnabled(false);
    }

    private void resetButton(Button a) {

        a.setEnabled(true);
        a.setBackgroundTintList(oldcolor);
        ColorStateList oldcolor  = a.getBackgroundTintList();
        a.setBackground(getDrawable(R.drawable.log_in));

    }

    private class getData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            Connection connection;
            DBconnector databaseconn = new DBconnector();


            try {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                connection = databaseconn.CONN();
                connection.setAutoCommit(false);

                QuizDAO quizDAO = new QuizDAO();

                quizDTO = new QuizDTO();

                quizDTO = quizDAO.getQuizByQuizId(Integer.parseInt(quizId), connection);

                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            wait.cancel();
            wait.onFinish();
        }
    }
}


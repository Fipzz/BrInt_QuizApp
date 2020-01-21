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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brint_quizapp.dal.dao.DDL;
import com.example.brint_quizapp.dal.dao.QuizDAO;
import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.AnswerDTO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Quiz_logic_activity extends AppCompatActivity implements View.OnClickListener {

    Button a, b, c, d;
    TextView questionView, loading, quiznavn;

    Intent resultIntent;

    ArrayList<AnswerDTO> answers;
    ArrayList<QuestionDTO> quizQuestions;

    int correctAnswers = 0, wrongAnswers = 0, currentQuestion = 0, isCorrect;

    String quizId;

    CountDownTimer wait;

    QuizDTO quizDTO;
    int load = 0;

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
        loading = (TextView) findViewById(R.id.textView2);
        loading.setOnClickListener(this);
        quiznavn = (TextView) findViewById(R.id.quiz_navn);


        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        questionView.setVisibility(View.INVISIBLE);
        quiznavn.setVisibility(View.INVISIBLE);

        quizId = getIntent().getExtras().getString("quizcode");

        quizDTO = null;


        getData getdat = new getData();

        getdat.execute("");

        wait = new CountDownTimer(10000, 500) {
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

                if (quizDTO != null) {
                    this.cancel();
                    this.onFinish();
                }

            }

            @Override
            public void onFinish() {

                if(quizDTO == null){

                    loading.setText("No quiz found\n click here to go back");

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

            if (R.id.answer1 == v.getId()) {

                if (answers.get(0).getCorrect()) {
                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }

            } else if (v.getId() == b.getId()) {

                if (answers.get(1).getCorrect()) {
                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }

            } else if (v.getId() == c.getId()) {

                if (answers.get(2).getCorrect()) {
                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }

            } else if (v.getId() == d.getId()) {

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
        a.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.buttonblue));
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


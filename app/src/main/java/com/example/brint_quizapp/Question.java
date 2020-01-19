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

public class Question extends AppCompatActivity implements View.OnClickListener {

    Button a, b, c, d;
    TextView questionView, loading, quiznavn;

    Intent resultIntent;

    ArrayList<Question_item> questions = new ArrayList<Question_item>();

    int correctAnswers = 0, wrongAnswers = 0, currentQuestion = 0, isCorrect;

    String quizId;

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
        quiznavn = (TextView) findViewById(R.id.quiz_navn);


        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        questionView.setVisibility(View.INVISIBLE);
        quiznavn.setVisibility(View.INVISIBLE);





        quizId = getIntent().getExtras().getString("quizcode");


        getData getdat = new getData();

        getdat.execute("");

        CountDownTimer wait = new CountDownTimer(10000, 500) {
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
                    //this.cancel();
                    //this.onFinish();
                }

            }

            @Override
            public void onFinish() {

                initializeQuiz();

                loading.setVisibility(View.INVISIBLE);
                a.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
                c.setVisibility(View.VISIBLE);
                d.setVisibility(View.VISIBLE);
                questionView.setVisibility(View.VISIBLE);

                showNextQuestion(questions.get(currentQuestion), currentQuestion);


            }
        }.start();


        resultIntent = new Intent(this, Result_activity.class);
    }

    @Override
    public void onClick(View v) {

        if (isCorrect == 1) {

            right(a);
            wrong(b);
            wrong(c);
            wrong(d);

        } else if (isCorrect == 2) {

            wrong(a);
            right(b);
            wrong(c);
            wrong(d);

        } else if (isCorrect == 3) {

            wrong(a);
            wrong(b);
            right(c);
            wrong(d);

        } else {

            wrong(a);
            wrong(b);
            wrong(c);
            right(d);

        }

        if (R.id.answer1 == v.getId()) {

            if (isCorrect == 1) {
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

        } else if (v.getId() == b.getId()) {

            if (isCorrect == 2) {
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

        } else if (v.getId() == c.getId()) {

            if (isCorrect == 3) {
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

        } else if (v.getId() == d.getId()) {


            if (isCorrect == 4) {
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

                if (questions.size() - 1 == currentQuestion) {

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
                    showNextQuestion(questions.get(currentQuestion), currentQuestion);
                }

            }

        }.start();

    }

    public void showNextQuestion(Question_item question, int currentQuestion) {

        questionView.setText(question.getQuestion());

        a.setText(question.getAnswer1());
        b.setText(question.getAnswer2());
        c.setText(question.getAnswer3());
        d.setText(question.getAnswer4());
        isCorrect = question.getIsCorrect();

    }

    private void initializeQuiz() {

        ArrayList<QuestionDTO> questionss = quizDTO.getQuestions();


        for (int i = 0; i < questionss.size(); i++) {

            ArrayList<AnswerDTO> answers = questionss.get(i).getAnswers();

            questions.add(new Question_item(questionss.get(i).getText(), answers.get(0).getText(), answers.get(1).getText(), answers.get(2).getText(), answers.get(3).getText(), 1));

        }
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
    }
}


package com.example.brint_quizapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brint_quizapp.dal.dto.QuizDTO;

import java.util.ArrayList;

public class Edit_quiz_home_activity extends AppCompatActivity implements View.OnClickListener {

    Button edit_quiz, stats;

    TextView quiz_navn;

    EditText uniqeCode;

    QuizDTO theQuiz;

    SharedPreferences sharedPref;

    String currentTheme, sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        setContentView(R.layout.quiz_menu_activity_layout);

        edit_quiz = findViewById(R.id.EditQuiz_home);
        edit_quiz.setOnClickListener(this);

        stats = findViewById(R.id.Statistics_home);
        stats.setOnClickListener(this);

        theQuiz = UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("chosenQuiz"));

        quiz_navn = (TextView) findViewById(R.id.quiz_navn);
        quiz_navn.setText(theQuiz.getName());

        uniqeCode = (EditText) findViewById(R.id.codeNumber);
        uniqeCode.setText(Integer.toString(theQuiz.getId()));
        uniqeCode.setEnabled(false);

    }

    @Override
    public void onClick(View v) {

        if (edit_quiz.getId() == v.getId()) {

            Intent edit_quiz = new Intent(Edit_quiz_home_activity.this, Edit_Quiz.class);

            Bundle edit_quiz_id = new Bundle();
            edit_quiz_id.putInt("quizId",getIntent().getExtras().getInt("chosenQuiz"));

            edit_quiz.putExtras(edit_quiz_id);

            startActivity(edit_quiz);

        } else if (stats.getId() == v.getId()) {

            Intent quiz = new Intent(Edit_quiz_home_activity.this, Statistics_Activity.class);

            Bundle data = new Bundle();
            data.putInt("quizId", UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("chosenQuiz")).getId());
            quiz.putExtras(data);

            startActivity(quiz);

        }
    }
}

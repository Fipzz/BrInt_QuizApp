package com.example.brint_quizapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brint_quizapp.dal.dao.QuizDAO;
import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.QuizDTO;
import com.example.brint_quizapp.dal.dto.UserDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class Edit_quiz_home_activity extends AppCompatActivity implements View.OnClickListener {

    Button edit_quiz, stats, delete;

    TextView quiz_navn;

    EditText uniqeCode;

    QuizDTO theQuiz;

    SharedPreferences sharedPref;

    String currentTheme, sharedPreference;

    CountDownTimer wait;

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

        delete =  (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);

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

        } else if(delete.getId() == v.getId()){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure you want to delete this quiz?");
            builder.setMessage("You are about to delete this quiz.\nAre you sure you want to continue?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                    deleteQuiz dq = new deleteQuiz();

                    dq.execute();

                    edit_quiz.setEnabled(false);
                    stats.setEnabled(false);
                    delete.setEnabled(false);

                    wait = new CountDownTimer(60000,60000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {

                            startActivity(new Intent(Edit_quiz_home_activity.this, Quiz_list_activity.class));

                        }
                    }.start();


                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

        }
    }

    private class deleteQuiz extends AsyncTask<String, Void, Void> {

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

                quizDAO.deleteQuiz(UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("chosenQuiz")).getId(),connection);

                connection.commit();

                UserDAO updateUser = new UserDAO();
                UserDTO updatedUser = new UserDTO();
                updatedUser = updateUser.getUserById(UserSingleton.getUserSingleton().getUser().getId(), connection);

                UserSingleton.getUserSingleton().setUser(updatedUser);

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

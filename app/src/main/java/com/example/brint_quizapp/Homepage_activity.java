package com.example.brint_quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.brint_quizapp.dal.dao.QuizDAO;
import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.QuizDTO;
import com.example.brint_quizapp.dal.dto.UserDTO;

import java.sql.Connection;

public class Homepage_activity extends AppCompatActivity implements View.OnClickListener {

    //TODO rediger quiz skal ændres til "Mine Quizzer" og skal føre til quiz oversigt

    Button quiz, profile, edit;
    EditText quiz_code;
    Toast toast;
    SharedPreferences sharedPref;
    String currentTheme, sharedPreference;
    QuizDAO quizDAO;
    DBconnector dBconnector;
    Connection connection;
    QuizDTO quizDTO;
    int quizID;
    CountDownTimer timer;
    TextView title, loading;
    boolean complete = false;

    String unikKode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme == "blue_theme"){

            setTheme(R.style.Theme_App_Blue);

        } else if (currentTheme == "purple_theme") {

            setTheme(R.style.Theme_App_Purple);
        }

        setContentView(R.layout.homepage_activity_layout);

        quiz = (Button) findViewById(R.id.start_quiz_knap);
        quiz.setOnClickListener(this);

        profile = (Button) findViewById(R.id.profil_knap);
        profile.setOnClickListener(this);

        edit = (Button) findViewById(R.id.rediger_knap);
        edit.setOnClickListener(this);

        quiz_code = (EditText) findViewById(R.id.unikkode);

        title = (TextView) findViewById(R.id.titel);


        loading = (TextView) findViewById(R.id.loading_view);
        loading.setVisibility(View.INVISIBLE);

        if(UserSingleton.getUserSingleton().getUser() == null){

            edit.setVisibility(View.INVISIBLE);

        }

    }

    private void startLoading(){
        quiz.setVisibility(View.INVISIBLE);
        profile.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        quiz_code.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
    }

    private void stopLoading(){
        quiz.setVisibility(View.VISIBLE);
        profile.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        quiz_code.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

        if(quiz.getId() == v.getId()){

            unikKode = quiz_code.getText().toString();

            if (unikKode.matches("")){

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Indsæt unik kode",
                        Toast.LENGTH_LONG);

                toast.show();

            } else {

                quizID = Integer.parseInt(quiz_code.getText().toString());
                startLoading();
                GetQuizDataClass getQuizDataClass = new GetQuizDataClass();
                getQuizDataClass.execute();
                timer = new CountDownTimer(20000,500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(quizDTO != null || complete){
                            timer.cancel();
                            timer.onFinish();
                        }
                    }

                    @Override
                    public void onFinish() {
                        if(quizDTO != null){

                            Intent quiz = new Intent(Homepage_activity.this, Quiz_logic_activity.class);

                            Bundle data = new Bundle();
                            data.putString("quizcode", quiz_code.getText().toString());
                            quiz.putExtras(data);

                            startActivity(quiz);

                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Could not find quiz",
                                    Toast.LENGTH_LONG);

                            toast.show();
                            stopLoading();
                            complete = false;
                        }
                    }

                }.start();

            }

        } else if(profile.getId() == v.getId()){

            startActivity(new Intent(Homepage_activity.this, Profile_activity.class));


        } else if(edit.getId() == v.getId()){

            startActivity(new Intent(Homepage_activity.this, Quiz_list_activity.class));


        }

    }

    private class GetQuizDataClass extends AsyncTask<String, Void, Void> {
        Connection connection;
        DBconnector connectionClass = new DBconnector();

        @Override
        protected Void doInBackground(String... strings) {

            try {
                try {

                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                connection = connectionClass.CONN();
                connection.setAutoCommit(false);

                quizDAO = new QuizDAO();
                quizDTO = quizDAO.getQuizByQuizId(quizID, connection);

                connection.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            complete = true;
            timer.cancel();
            timer.onFinish();


        }
    }

}

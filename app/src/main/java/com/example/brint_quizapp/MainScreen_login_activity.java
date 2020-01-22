package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.UserDTO;

import java.sql.Connection;

public class MainScreen_login_activity extends AppCompatActivity implements View.OnClickListener {

    Button login, anon, dbtest;
    EditText email, password;
    CountDownTimer timer;
    UserSingleton userSingleton;
    TextView opretBruger, loading, name;
    UserDTO userDTO;
    View r1, r2;

    String emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login_activity_layout);

        login = (Button) findViewById(R.id.log_in);
        login.setOnClickListener(this);

        anon = (Button) findViewById(R.id.anon);
        anon.setOnClickListener(this);

        email = findViewById(R.id.brugernavn);

        password = findViewById(R.id.password);

        userSingleton = new UserSingleton();

        opretBruger = (TextView) findViewById(R.id.opret_bruger);

        opretBruger.setOnClickListener(this);

        r1 = (View) findViewById(R.id.rectangle);
        r2 = (View) findViewById(R.id.rectangle_2);

        name = (TextView) findViewById(R.id.name);

        loading = (TextView) findViewById(R.id.loading);
        loading.setVisibility(View.INVISIBLE);

    }

    private void startLoading(){
        login.setVisibility(View.INVISIBLE);
        r1.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        opretBruger.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        anon.setVisibility(View.INVISIBLE);

        loading.setVisibility(View.VISIBLE);
    }

    private void stopLoading(){
        login.setVisibility(View.VISIBLE);
        r1.setVisibility(View.VISIBLE);
        r2.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        opretBruger.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        anon.setVisibility(View.VISIBLE);

        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed(){
        System.exit(0);
    }

    @Override
    public void onClick(View v) {

        if(login.getId() == v.getId()){

            startLoading();

            if(email.getText().toString().equals("") && password.getText().toString().equals("")){
                email.setText("Rasmus");
                password.setText("123");
            }

            emailString = email.getText().toString();



            GetUserClass getUserClass = new GetUserClass();

            getUserClass.execute();

            timer = new CountDownTimer(20000,500) {
                @Override
                public void onTick(long millisUntilFinished) {

                    if(userSingleton.getUser() != null){

                        if(userSingleton.getUser().getPassword().equals(password.getText().toString())){

                            startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));

                        }else{
                            stopLoading();
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Wrong password. Try again.",
                                    Toast.LENGTH_LONG);

                            toast.show();
                        }
                        timer.cancel();
                    }
                }

                @Override
                public void onFinish() {

                    if(userSingleton.getUser() != null){

                        if(userSingleton.getUser().getPassword().equals(password.getText().toString())){

                            startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));


                        }else{
                            stopLoading();
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Wrong password. Try again.",
                                    Toast.LENGTH_LONG);

                            toast.show();
                        }

                    }else{
                        stopLoading();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Could not find user.",
                                Toast.LENGTH_LONG);

                        toast.show();
                    }

                }
            }.start();



        } else if(anon.getId() == v.getId()){

            startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));

        }else if(opretBruger.getId() == v.getId()){
            startActivity(new Intent(MainScreen_login_activity.this, Create_user_activity.class));
        }

    }

    private class GetUserClass extends AsyncTask<String, Void, Void> {
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

                UserDAO userDAO = new UserDAO();
                UserDTO userDTO = userDAO.getUserByEmail(emailString, connection);

                userSingleton.setUser(userDTO);
                UserSingleton.getUserSingleton().setUser(userDTO);
                connection.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            timer.cancel();
            timer.onFinish();

        }
    }

}

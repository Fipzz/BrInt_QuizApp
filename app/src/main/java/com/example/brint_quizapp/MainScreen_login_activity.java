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
import java.sql.SQLException;

import static com.example.brint_quizapp.Db_test.connctionClass;

public class MainScreen_login_activity extends AppCompatActivity implements View.OnClickListener {

    Button login, anon, dbtest;
    EditText email, password;
    CountDownTimer timer;
    UserSingleton userSingleton;
    TextView opretBruger;
    UserDTO userDTO;

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

        dbtest = (Button) findViewById(R.id.dbtest);
        dbtest.setOnClickListener(this);

        email = findViewById(R.id.brugernavn);

        password = findViewById(R.id.password);

        userSingleton = new UserSingleton();

        opretBruger = (TextView) findViewById(R.id.opret_bruger);

        opretBruger.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(login.getId() == v.getId()){

            emailString = email.getText().toString();


            if(email.getText().toString().equals("")){
                userSingleton.setUser(null);
                startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));
            }else {

                GetUserClass getUserClass = new GetUserClass();

                getUserClass.execute();

                timer = new CountDownTimer(20000,500) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        if(userSingleton.getUser() != null){

                            if(userSingleton.getUser().getPassword().equals(password.getText().toString())){

                                UserSingleton.getUserSingleton().setUser(userDTO);

                                startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));

                            }else{
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

                                UserSingleton.getUserSingleton().setUser(userDTO);

                                startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));

                            }else{
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Wrong password. Try again.",
                                        Toast.LENGTH_LONG);

                                toast.show();
                            }

                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Could not find user.",
                                    Toast.LENGTH_LONG);

                            toast.show();
                        }

                    }
                }.start();

            }

        } else if(anon.getId() == v.getId()){

            startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));

        } else if(v.getId() == dbtest.getId()){
            startActivity(new Intent(MainScreen_login_activity.this, Db_test.class));
        }else if(opretBruger.getId() == v.getId()){
            startActivity(new Intent(MainScreen_login_activity.this, Create_user_activity.class));
        }

    }

    private class GetUserClass extends AsyncTask<String, Void, Void> {
        Connection connection;
        DBconnector connctionClass = new DBconnector();

        @Override
        protected Void doInBackground(String... strings) {

            try {
                try {

                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                connection = connctionClass.CONN();
                connection.setAutoCommit(false);

                UserDAO userDAO = new UserDAO();
                UserDTO userDTO = userDAO.getUserByEmail(emailString, connection);


                userSingleton.setUser(userDTO);
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

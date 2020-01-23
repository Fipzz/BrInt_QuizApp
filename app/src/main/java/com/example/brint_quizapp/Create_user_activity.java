package com.example.brint_quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.UserDTO;

import java.sql.Connection;

public class Create_user_activity extends AppCompatActivity implements View.OnClickListener {

    TextView name, email, password, password2, tv3, loading;
    Button create;
    CountDownTimer timer;
    View r1, r2, r3, r4;

    private void startLoading(){
        create.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        password2.setVisibility(View.INVISIBLE);
        r1.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.INVISIBLE);
        r3.setVisibility(View.INVISIBLE);
        r4.setVisibility(View.INVISIBLE);
        tv3.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);

    }

    private void stopLoading(){
        create.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        password2.setVisibility(View.VISIBLE);
        r1.setVisibility(View.VISIBLE);
        r2.setVisibility(View.VISIBLE);
        r3.setVisibility(View.VISIBLE);
        r4.setVisibility(View.VISIBLE);
        tv3.setVisibility(View.VISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Create_user_activity.this, MainScreen_login_activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.create_user);

        loading = (TextView) findViewById(R.id.loading2);
        loading.setVisibility(View.INVISIBLE);
        create = (Button) findViewById(R.id.user_done);
        name = (TextView) findViewById(R.id.user_name_text);
        email = (TextView) findViewById(R.id.user_email);
        password = (TextView) findViewById(R.id.user_password);
        password2 = (TextView) findViewById(R.id.user_repeat_password);

        create.setOnClickListener(this);

        r1 = (View) findViewById(R.id.rectangle2);
        r2 = (View) findViewById(R.id.rectangle3);
        r3 = (View) findViewById(R.id.rectangle4);
        r4 = (View) findViewById(R.id.rectangle5);

        tv3 = (TextView) findViewById(R.id.textView3);

    }

    @Override
    public void onClick(View v) {
        String nameString = name.getText().toString();
        String emailString = email.getText().toString();
        String passwordString =  password.getText().toString();
        String password2String =  password2.getText().toString();

        if(nameString.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter a name",
                    Toast.LENGTH_LONG);

            toast.show();
        }else if(emailString.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter an email",
                    Toast.LENGTH_LONG);

            toast.show();
        }else if(passwordString.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please enter a password",
                    Toast.LENGTH_LONG);

            toast.show();
        }else if(!passwordString.equals(password2String)){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Password mismatch",
                    Toast.LENGTH_LONG);

            toast.show();
        }else if(!nameString.equals("") && !emailString.equals("") && !passwordString.equals("") && passwordString.equals(password2String)){

            startLoading();
            UserDTO userDTO = new UserDTO();
            userDTO.setName(nameString);
            userDTO.setPassword(passwordString);
            userDTO.setEmail(emailString);

            final CreateUserClass createUserClass = new CreateUserClass();
            createUserClass.setUser(userDTO);
            createUserClass.execute();

            timer = new CountDownTimer(20000, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(createUserClass.isDone()){
                        timer.onFinish();
                        timer.cancel();
                    }
                }

                @Override
                public void onFinish() {
                    if(createUserClass.isDone() && createUserClass.isSuccess()) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "User created!",
                                Toast.LENGTH_LONG);

                        toast.show();

                        startActivity(new Intent(Create_user_activity.this, MainScreen_login_activity.class));
                    }else{
                        stopLoading();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "User could not be created.\nTry another Email.",
                                Toast.LENGTH_LONG);

                        toast.show();
                    }
                }
            }.start();

        }
    }

    private static class CreateUserClass extends AsyncTask<String, Void, Void> {
        Connection connection;

        UserDTO user = new UserDTO();

        Boolean success = false;
        Boolean done = false;

        public Boolean isDone() {
            return done;
        }

        public Boolean isSuccess(){
            return success;
        }
        public void setUser(UserDTO user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                connection = new DBconnector().CONN();
                connection.setAutoCommit(false);

                UserDAO userDAO = new UserDAO();

                success = userDAO.createUser(user, connection);

                connection.close();

                done = true;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


    }
}

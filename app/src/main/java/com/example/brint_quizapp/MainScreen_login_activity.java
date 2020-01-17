package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainScreen_login_activity extends AppCompatActivity implements View.OnClickListener {

    //TODO login and password should be(NO /n & limit input)

    Button login, anon, dbtest;
    EditText username, password;

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

        username = findViewById(R.id.brugernavn);

        password = findViewById(R.id.password);

    }

    @Override
    public void onClick(View v) {

        if(login.getId() == v.getId()){

            startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));

        } else if(anon.getId() == v.getId()){

            startActivity(new Intent(MainScreen_login_activity.this, Homepage_activity.class));

        } else if(v.getId() == dbtest.getId()){
            startActivity(new Intent(MainScreen_login_activity.this, Db_test.class));
        }

    }

}

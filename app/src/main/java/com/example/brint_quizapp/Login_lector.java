package com.example.brint_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login_lector extends AppCompatActivity implements View.OnClickListener {

    Button login,anon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity_layout);


        login = (Button) findViewById(R.id.log_in);
        login.setOnClickListener(this);

        anon = (Button) findViewById(R.id.anon);
        anon.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        if(R.id.log_in == v.getId()){

            startActivity(new Intent(Login_lector.this, Homepage_activity.class));

        } else if (R.id.anon == v.getId()){

        }



    }

}

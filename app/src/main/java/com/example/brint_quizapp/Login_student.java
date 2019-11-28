package com.example.brint_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login_student extends AppCompatActivity implements View.OnClickListener {

    Button login, anon, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);


        login = (Button) findViewById(R.id.Login);
        login.setOnClickListener(this);

        anon = (Button) findViewById(R.id.Anonymous);
        anon.setOnClickListener(this);

        back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(this);




    }



    @Override
    public void onClick(View v) {

        if(R.id.Login == v.getId()){

            startActivity(new Intent(Login_student.this, Login_screen.class));

        }



    }

}
